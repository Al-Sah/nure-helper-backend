package devs.nure.metainfoservice.services;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.*;

public interface DirectoryService {

    DirectoryDto addDirectory(CreateDirectory obj);
    DirectoryDto updateDirectory(UpdateDirectory obj);
    void removeDirectory(ChangeStatusDirectory deleteDirectory);
    DirectoryDto showDirectoryDescription(String dirUniqID);
    DirectoryNode showDirectory(String dirUniqID);
    TreeNode generateDirectoriesTree(String dirUniqID);
    void recoverDirectory(ChangeStatusDirectory recoverDirectory);
}
