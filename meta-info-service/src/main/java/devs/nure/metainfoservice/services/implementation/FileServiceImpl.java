package devs.nure.metainfoservice.services.implementation;


import devs.nure.formslibrary.ChangeStatusFile;
import devs.nure.formslibrary.FileInfo;
import devs.nure.formslibrary.UpdateFile;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.exceptions.DirectoryNotFoundException;
import devs.nure.metainfoservice.exceptions.FileNotFoundException;
import devs.nure.metainfoservice.models.CustomFile;
import devs.nure.metainfoservice.models.State;
import devs.nure.metainfoservice.repositories.DirectoryRepository;
import devs.nure.metainfoservice.repositories.FileRepository;
import devs.nure.metainfoservice.services.FileService;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class FileServiceImpl implements FileService {
    
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    public FileServiceImpl(FileRepository fileRepository, DirectoryRepository directoryRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
    }
    
    @Override
    public void addFile(FileInfo file) {
        if (directoryRepository.existsByUniqID(file.getParentID())) {
            CustomFile customFile = new CustomFile(
                    file.getName(),
                    file.getUniqId(),
                    file.getParentID(),
                    file.getCreationAuthor(),
                    file.getModificationAuthor(),
                    file.getCreated(),
                    file.getLastModification(),
                    State.CREATED
            );
            fileRepository.save(customFile);
        } else {
            throw new DirectoryNotFoundException("parent directory not found");
        }
    }

    @Override
    public FileDto updateFile(UpdateFile updateFile) {

        CustomFile customFile = fileRepository.findByUniqID(updateFile.getUniqId())
                .orElseThrow(() -> new FileNotFoundException("file not found"));
        customFile.setShortName(updateFile.getName());
        customFile.setModificationAuthor(updateFile.getModificationAuthor());
        customFile.setLastModification(new Date());
        customFile.setState(State.UPDATED);

        fileRepository.save(customFile);
        return new FileDto(customFile);
    }

    @Override
    public FileDto showFileInfo(String fileUniqID) {
        return new FileDto(fileRepository.findByUniqID(fileUniqID)
                .orElseThrow(() -> new FileNotFoundException("file not found")));
    }

    @Override
    public void setStatusFile(ChangeStatusFile file, State state) {
        CustomFile customFile = fileRepository.findByUniqID(file.getFileId())
                .orElseThrow(() -> new FileNotFoundException("file not found"));
        customFile.setState(state);
        customFile.setLastModification(new Date());
        customFile.setModificationAuthor(file.getAuthor());
        fileRepository.save(customFile);
    }
}
