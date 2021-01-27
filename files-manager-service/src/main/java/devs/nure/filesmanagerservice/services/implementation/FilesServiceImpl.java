package devs.nure.filesmanagerservice.services.implementation;

import devs.nure.filesmanagerservice.feign.*;
import devs.nure.filesmanagerservice.forms.StoredFile;
import devs.nure.filesmanagerservice.models.Locator;
import devs.nure.filesmanagerservice.repisitories.LocatorRepository;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        // TODO file validation
        String fileChecksum = getChecksum(file); // TODO Checksum
        FileInfo fileInfo = generateFileMetaInformation(file, createFile);

        Optional<Locator> optionalLocator = locatorRepository.findByChecksum(fileChecksum);
        boolean isPresent = optionalLocator.isPresent();
        Locator locator = isPresent ? new Locator(optionalLocator.get())
                : new Locator(fileChecksum, fileInfo.getContentType(), fileInfo.getUniqId());

        filesService.sendFileInformation(fileInfo); //TODO Handle exceptions (ControllerAdvice)

        if(!isPresent) {
            String uuid = "FILE-" + UUID.randomUUID().toString();
            StoredFile storedFile = sendFileToStorage(file, uuid); // TODO Handle exceptions (ControllerAdvice)
            locator.setPath(storedFile.getPath());
            locator.setProvider(storedFile.getProvider());
            locator.setStoredFileUUID(uuid);
        }

        locator.setFileMetaInfoUUID(fileInfo.getUniqId());
        locatorRepository.save(locator);
        return fileInfo;
    }

    @Override
    public StoredFile sendFileToStorage(MultipartFile file, String uniqID) {
        // TODO According to the size choose provider
        StoredFile storedFile = new StoredFile();
        storedFile.setProvider("mainStorage");
        String path = storageServer.storeFile(file, uniqID);
        storedFile.setPath(path);
        return storedFile;
    }

    @Override
    public Resource downloadFile(String fileID) {
        // TODO create new exception (and handle ...)
        Locator locator = locatorRepository.findByFileMetaInfoUUID(fileID).orElseThrow(() -> new RuntimeException("No file"));
        // TODO locator.getProvider()  !!
        return storageServer.getFile(locator.getPath());
    }


    @Override
    public FileInfo getFileInfo(String fileID) {
        return filesService.getFileInfo(fileID); // will be used in uploading files
    }

    @Override
    public void removeFile(String fileID) {
        // TODO create new exception (and handle ...)
        Locator locator = locatorRepository.findByFileMetaInfoUUID(fileID).orElseThrow(() -> new RuntimeException("No file"));
        List<Locator> locators = locatorRepository.findAllByChecksum(locator.getChecksum());

        if (locators.size() == 1) {
            storageServer.deleteFile(locator.getPath()); // TODO different implementations according to the provider
        }
        locatorRepository.deleteById(locator.getId());
        filesService.removeFileMetaInfo(fileID); // TODO Handle exceptions (ControllerAdvice)
    }

    @Override
    public String getChecksum(MultipartFile file) {
        return Long.toString(file.getSize()); // TODO calc Checksum
    }
}
