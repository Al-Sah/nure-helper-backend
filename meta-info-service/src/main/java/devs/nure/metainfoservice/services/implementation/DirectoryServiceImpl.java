package devs.nure.metainfoservice.services.implementation;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.CreateDirectory;
import devs.nure.metainfoservice.forms.UpdateDirectory;
import devs.nure.metainfoservice.services.DirectoryService;
import org.springframework.stereotype.Service;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Override
    public DirectoryDto addDirectory(CreateDirectory directory) {
        return null;
    }

    @Override
    public DirectoryDto updateDirectory(UpdateDirectory directory) {
        return null;
    }

    @Override
    public void removeDirectory(String dirUniqID) {

    }

    @Override
    public Object showDirectories() {
        return null;
    }
}
