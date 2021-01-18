package devs.nure.metainfoservice.services;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.*;
import devs.nure.metainfoservice.models.State;

public interface DirectoryService {

    DirectoryDto addDirectory(CreateDirectory obj);
    DirectoryDto updateDirectory(UpdateDirectory obj);
    DirectoryDto showDirectoryDescription(String dirUniqID);
    DirectoryNode showDirectory(String dirUniqID);
    TreeNode generateDirectoriesTree(String dirUniqID);
    void setStatusDirectory(ChangeStatusDirectory directory, State state);
//    boolean isDirectoryExists(String dirUniqID);
}
