package devs.nure.filesmanagerservice.services;

import devs.nure.formslibrary.CreateFile;
import devs.nure.formslibrary.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

    FileInfo saveFileMetaInfo(MultipartFile file, CreateFile createFile);
    FileInfo manageFile(MultipartFile file, CreateFile createFile);
    void storeFile(MultipartFile file);
    ResponseEntity<Resource> downloadFile(String location);
}
