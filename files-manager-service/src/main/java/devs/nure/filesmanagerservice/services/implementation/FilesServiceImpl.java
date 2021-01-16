package devs.nure.filesmanagerservice.services.implementation;

import devs.nure.filesmanagerservice.feign.FeignFilesService;
import devs.nure.filesmanagerservice.services.FilesService;
import devs.nure.formslibrary.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesServiceImpl implements FilesService {

    private final FeignFilesService filesService;

    public FilesServiceImpl(FeignFilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public FileInfo saveFileMetaInfo(MultipartFile file) {
        FileInfo fileInfo = new FileInfo(file.getName());

        filesService.addFile(fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfo manageFile(MultipartFile file) {
        return saveFileMetaInfo(file);
    }
}
