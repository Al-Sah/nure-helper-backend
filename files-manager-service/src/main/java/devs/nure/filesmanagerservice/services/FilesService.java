package devs.nure.filesmanagerservice.services;

import devs.nure.formslibrary.ChangeStatusFile;
import devs.nure.formslibrary.CreateFile;
import devs.nure.formslibrary.FileInfo;
import devs.nure.formslibrary.UpdateFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

    FileInfo saveFileMetaInfo(MultipartFile file, CreateFile createFile);
    FileInfo manageFile(MultipartFile file, CreateFile createFile);
    void storeFile(MultipartFile file, String uniqID);
    ResponseEntity<Resource> downloadFile(String location);
//    void deleteFile(ChangeStatusFile file);
//    void recoverFile(ChangeStatusFile file);
    FileInfo getFileInfo(String fileId);
    void completeRemoveFile(String fileId);
}
