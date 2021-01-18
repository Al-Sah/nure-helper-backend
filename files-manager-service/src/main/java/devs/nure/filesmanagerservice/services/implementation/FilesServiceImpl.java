package devs.nure.filesmanagerservice.services.implementation;

import devs.nure.filesmanagerservice.feign.FeignFilesService;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FilesServiceImpl implements FilesService {

    private final FeignFilesService filesService;

    public FilesServiceImpl(FeignFilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public FileInfo saveFileMetaInfo(MultipartFile file, CreateFile createFile) {
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), file.getContentType(),
                UUID.randomUUID().toString(), createFile.getParentID(), createFile.getAuthor(),
                createFile.getAuthor(), new Date(), new Date(), "CREATED");

        try {
            filesService.addFile(fileInfo);
        } catch (Exception e) {
            e.getMessage();
        }
        return fileInfo;
    }

    @Override
    public FileInfo manageFile(MultipartFile file, CreateFile createFile) {
        return saveFileMetaInfo(file, createFile);
    }
}
