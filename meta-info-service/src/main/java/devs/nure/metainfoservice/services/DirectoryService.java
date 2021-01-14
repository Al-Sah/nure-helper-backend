package devs.nure.metainfoservice.services;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.CreateDirectory;
import devs.nure.metainfoservice.forms.UpdateDirectory;

public interface DirectoryService {

    DirectoryDto addDirectory(CreateDirectory obj);
    DirectoryDto updateDirectory(UpdateDirectory obj);
    void removeDirectory(String dirID);
    DirectoryDto showDirectory(String dirUniqID);
    Object showDirectories();

}
