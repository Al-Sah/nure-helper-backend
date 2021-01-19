package devs.nure.filesmanagerservice.services.implementation;

import devs.nure.filesmanagerservice.feign.FeignFilesMetaInfoService;
import devs.nure.filesmanagerservice.feign.FeignStorageServer;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FilesServiceImpl implements FilesService {

    private final FeignFilesMetaInfoService filesService;
    private final FeignStorageServer storageServer;

    public FilesServiceImpl(FeignFilesMetaInfoService filesService, FeignStorageServer storageServer) {
        this.filesService = filesService;
        this.storageServer = storageServer;
    }

    @Override
    public FileInfo saveFileMetaInfo(MultipartFile file, CreateFile createFile) {
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), file.getContentType(),
                UUID.randomUUID().toString(), createFile.getParentID(), createFile.getAuthor(),
                createFile.getAuthor(), new Date(), new Date(), "CREATED");
        try {
            filesService.addFile(fileInfo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return fileInfo;
    }

    @Override
    public FileInfo manageFile(MultipartFile file, CreateFile createFile) {
        FileInfo fileInfo = saveFileMetaInfo(file, createFile);
        storeFile(file);
        return fileInfo;
    }

    @Override
    public void storeFile(MultipartFile file) {
        storageServer.storeFile(file);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String location) {
        return storageServer.getFile(location);
    }
}
