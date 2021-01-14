package devs.nure.metainfoservice.services.implementation;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.CreateDirectory;
import devs.nure.metainfoservice.forms.UpdateDirectory;
import devs.nure.metainfoservice.models.CustomDirectory;
import devs.nure.metainfoservice.repositories.DirectoryRepository;
import devs.nure.metainfoservice.services.DirectoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }


    @Override
    public DirectoryDto addDirectory(CreateDirectory directory) {

        CustomDirectory customDirectory = new CustomDirectory();

        //directory parameters
        customDirectory.setShortName(directory.getShortName());
        customDirectory.setFullName(directory.getFullName());
        customDirectory.setDescription(directory.getDescription());
        customDirectory.setCreationAuthor(directory.getAuthor());

        //other parameters
        customDirectory.setCreated(new Date());
        customDirectory.setUniqID(UUID.randomUUID().toString());
        customDirectory.setModificationAuthor(customDirectory.getCreationAuthor());
        customDirectory.setLastModification(customDirectory.getCreated());
        customDirectory.setState(null);

        directoryRepository.save(customDirectory);

        return new DirectoryDto(customDirectory);

    }

    @Override
    public DirectoryDto updateDirectory(UpdateDirectory directory) {

        CustomDirectory customDirectory = new CustomDirectory();

        customDirectory.setShortName(directory.getShortName());
        customDirectory.setFullName(directory.getFullName());
        customDirectory.setDescription(directory.getDescription());
        customDirectory.setUniqID(directory.getUniqId());
        customDirectory.setModificationAuthor(directory.getModificationAuthor());

        customDirectory.setLastModification(new Date());
        customDirectory.setState(null); // UPDATED

        directoryRepository.save(customDirectory);

        return new DirectoryDto(customDirectory);
    }

    @Override
    public void removeDirectory(String dirUniqID) {
        directoryRepository.deleteByUniqID(dirUniqID);
    }

    @Override
    public DirectoryDto showDirectory(String dirUniqId) {

        return new DirectoryDto(directoryRepository.findByUniqID(dirUniqId).orElseThrow(() -> new RuntimeException("")));
    }

    @Override
    public Object showDirectories() {
        return null;
    }
}
