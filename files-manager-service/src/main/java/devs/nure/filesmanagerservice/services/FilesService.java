package devs.nure.filesmanagerservice.services;

import devs.nure.formslibrary.CreateFile;
import devs.nure.formslibrary.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

    FileInfo saveFileMetaInfo(MultipartFile file, CreateFile createFile);
    FileInfo manageFile(MultipartFile file, CreateFile createFile);
}
