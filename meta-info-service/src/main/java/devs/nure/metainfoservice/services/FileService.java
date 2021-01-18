package devs.nure.metainfoservice.services;

import devs.nure.formslibrary.ChangeStatusFile;
import devs.nure.formslibrary.FileInfo;
import devs.nure.formslibrary.UpdateFile;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.models.State;

public interface FileService {
    void addFile(FileInfo file);
    FileDto updateFile(UpdateFile updateFile);
    FileDto showFileInfo(String fileUniqID);
    void setStatusFile(ChangeStatusFile file, State state);
}
