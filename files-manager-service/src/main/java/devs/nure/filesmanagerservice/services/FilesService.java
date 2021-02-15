package devs.nure.filesmanagerservice.services;

import devs.nure.filesmanagerservice.forms.StoredFile;
import devs.nure.formslibrary.CreateFile;
import devs.nure.formslibrary.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

    FileInfo generateFileMetaInformation(MultipartFile file, CreateFile createFile);
    FileInfo manageFile(MultipartFile file, CreateFile createFile);
    StoredFile sendFileToStorage(MultipartFile file, String uniqID, String mataID);
    Resource getFileResDwnld(String fileID);
    Resource getFileRes(String fileID);
    FileInfo getFileInfo(String fileId);
    void removeFile(String fileId);
    String getChecksum(MultipartFile file);
}
