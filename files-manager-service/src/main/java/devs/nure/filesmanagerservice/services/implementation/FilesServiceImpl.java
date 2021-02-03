package devs.nure.filesmanagerservice.services.implementation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import devs.nure.filesmanagerservice.exceptions.*;
import devs.nure.filesmanagerservice.feign.*;
import devs.nure.filesmanagerservice.forms.StoredFile;
import devs.nure.filesmanagerservice.models.Locator;
import devs.nure.filesmanagerservice.repisitories.LocatorRepository;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@EnableHystrix
@Service
public class FilesServiceImpl implements FilesService {

    private final FeignFilesMetaInfoService filesService;
    private final FeignStorageServer storageServer;
    private final LocatorRepository locatorRepository;

    public FilesServiceImpl(FeignFilesMetaInfoService filesService, FeignStorageServer storageServer, LocatorRepository locatorRepository) {
        this.filesService = filesService;
        this.storageServer = storageServer;
        this.locatorRepository = locatorRepository;
    }

    private void deleteMetaInformation(String fileID){
        try {
            filesService.removeFileMetaInfo(fileID);
        }catch (FeignException.FeignClientException.ServiceUnavailable exception){
            log.error(exception.getMessage());
            throw new ServiceDoNotRespondException("mata-information-service");
        }
    }


    private void saveMetaInformation(FileInfo fileInfo){
        try {
            filesService.sendFileInformation(fileInfo);
        }catch (FeignException.FeignClientException.BadRequest exception){
            log.error(exception.getMessage());
            throw new MetaInformationException(exception.contentUTF8().substring(12, exception.contentUTF8().length() - 2));
        }catch (FeignException.FeignClientException.ServiceUnavailable exception){
            log.error(exception.getMessage());
            throw new ServiceDoNotRespondException("mata-information-service");
        }
    }

    @Override
    public FileInfo generateFileMetaInformation(MultipartFile file, CreateFile createFile) {
        return new FileInfo(
                file.getOriginalFilename(),
                file.getContentType(),
                UUID.randomUUID().toString(),
                createFile.getParentID(),
                createFile.getAuthor(), createFile.getAuthor(),
                new Date(), new Date(), "CREATED");
    }

    @Override
    public FileInfo manageFile(MultipartFile file, CreateFile createFile) {
        if(file.isEmpty()){
            log.info("Downloaded invalid file ["+file.getOriginalFilename()+"]");
            throw new InvalidFileException(file.getOriginalFilename());
        }
        String fileChecksum = getChecksum(file); // TODO Checksum
        FileInfo fileInfo = generateFileMetaInformation(file, createFile);

        Optional<Locator> optionalLocator = locatorRepository.findFirstByChecksum(fileChecksum);
        boolean isPresent = optionalLocator.isPresent();
        Locator locator = isPresent ? new Locator(optionalLocator.get())
                : new Locator(fileChecksum, fileInfo.getContentType(), fileInfo.getUniqId());

        saveMetaInformation(fileInfo);

        if(!isPresent) {
            String uuid = "FILE-" + UUID.randomUUID().toString();
            StoredFile storedFile = sendFileToStorage(file, uuid, fileInfo.getUniqId());
            locator.setPath(storedFile.getPath());
            locator.setProvider(storedFile.getProvider());
            locator.setStoredFileUUID(uuid);
        }

        locator.setFileMetaInfoUUID(fileInfo.getUniqId());
        locatorRepository.save(locator);
        return fileInfo;
    }

    @Override
    public StoredFile sendFileToStorage(MultipartFile file, String uniqID, String mataID) {
        // TODO According to the size choose provider
        StoredFile storedFile = new StoredFile();
        storedFile.setProvider("mainStorage");

        try {
            storedFile.setPath(storageServer.storeFile(file, uniqID));
        } catch (FeignException exception) {
            log.error(exception.getMessage());
            deleteMetaInformation(mataID);
            if (exception instanceof FeignException.FeignClientException.BadRequest){
                throw new FilesStorageException(exception.contentUTF8().substring(12, exception.contentUTF8().length() - 2));
            } else if (exception instanceof FeignException.FeignClientException.ServiceUnavailable) {
                throw new ServiceDoNotRespondException("custom-files-storage");
            }
        }
        return storedFile;
    }

    @HystrixCommand(fallbackMethod = "downloadFileFallbackMethod")
    @Override
    public Resource downloadFile(String fileID) {
        Locator locator = locatorRepository.findByFileMetaInfoUUID(fileID).orElseThrow(() -> new FileNotFoundException(fileID));
        return storageServer.getFile(locator.getPath());
    }

    public Resource downloadFileFallbackMethod(String fileID) {

        /*return new AbstractResource() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(
                        ("this is fallback\n" + HttpStatus.REQUEST_TIMEOUT.toString())
                                .getBytes(StandardCharsets.UTF_8));
            }
        };
        */

        /*
        return new ByteArrayResource("fallback".getBytes(StandardCharsets.UTF_8), null);
        */

        return new InputStreamResource(new ByteArrayInputStream(
                ("this is fallback\n" + HttpStatus.REQUEST_TIMEOUT.toString())
                .getBytes(StandardCharsets.UTF_8)));

    }


    @Override
    public FileInfo getFileInfo(String fileID) {
        try {
            return filesService.getFileInfo(fileID); // or else fallback method (enable hystrix)
        } catch (FeignException exception){
            log.error(exception.getMessage());
        }
        Locator locator = locatorRepository.findByFileMetaInfoUUID(fileID).orElseThrow(() -> new FileNotFoundException(fileID));
        return new FileInfo("document", locator.getContentType(), fileID,"missed","missed","missed",new Date(), new Date(), "GENERATED");
    }

    @Override
    public void removeFile(String fileID) {
        Locator locator = locatorRepository.findByFileMetaInfoUUID(fileID).orElseThrow(() -> new FileNotFoundException(fileID));
        List<Locator> locators = locatorRepository.findAllByChecksum(locator.getChecksum());

        if (locators.size() == 1) {
            storageServer.deleteFile(locator.getPath()); // TODO different implementations according to the provider
        }
        locatorRepository.deleteById(locator.getId());
        deleteMetaInformation(fileID);
    }

    @Override
    public String getChecksum(MultipartFile file) {
        try {
            return DigestUtils.sha256Hex(file.getBytes());
        } catch (IOException e) {
            log.error("Cannot use sha256Hex (will be used \"size+content\"). Exception is: {}", e.getMessage());
        }
        return file.getSize() + file.getContentType();
    }
}
