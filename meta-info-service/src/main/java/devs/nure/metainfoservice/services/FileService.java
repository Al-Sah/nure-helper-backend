package devs.nure.metainfoservice.services;

import devs.nure.formslibrary.ChangeStatusFile;
import devs.nure.formslibrary.FileInfo;
import devs.nure.formslibrary.UpdateFile;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.models.CustomFile;
import devs.nure.metainfoservice.models.State;

import java.util.List;

public interface FileService {
    void addFile(FileInfo file);
    FileDto updateFile(UpdateFile updateFile);
    FileDto showFileInfo(String fileUniqID);
    void deleteFilesInfo(String directoryID);
    void setFileState(ChangeStatusFile file, State state);
    void completeDeleteFile(String fileUniqID);
    List<CustomFile> getFilesByParentID(String uniqID);
}
