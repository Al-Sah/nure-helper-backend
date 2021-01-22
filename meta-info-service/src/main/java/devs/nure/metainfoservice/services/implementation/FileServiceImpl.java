package devs.nure.metainfoservice.services.implementation;

import devs.nure.formslibrary.*;
import devs.nure.metainfoservice.Dto.FileDto;
import devs.nure.metainfoservice.exceptions.*;
import devs.nure.metainfoservice.models.CustomFile;
import devs.nure.metainfoservice.models.State;
import devs.nure.metainfoservice.repositories.*;
import devs.nure.metainfoservice.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    public FileServiceImpl(FileRepository fileRepository, DirectoryRepository directoryRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
    }

    private CustomFile findByUUID(String uniqId){
        return fileRepository.findByUniqID(uniqId).orElseThrow(() -> new FileNotFoundException("File not found"));
    }

    @Override
    public void deleteFilesInfo(String dirID){
       fileRepository.deleteAllByParentID(dirID);
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
                    file.getContentType(),
                    file.getCreated(),
                    file.getLastModification(),
                    State.CREATED);
            fileRepository.save(customFile);
        } else {
            throw new DirectoryNotFoundException("Parent directory not found");
        }
    }

    @Override
    public void updateFile(UpdateFile updateFile) {
        CustomFile customFile = findByUUID(updateFile.getUniqId());
        if (customFile.getState() != State.DELETED) {
            customFile.setShortName(updateFile.getName());
            customFile.setModificationAuthor(updateFile.getModificationAuthor());
            customFile.setLastModification(new Date());
            customFile.setState(State.UPDATED);

            fileRepository.save(customFile);
        } else {
            throw new RuntimeException("File's state is 'deleted'");
        }
    }

    @Override
    public FileDto showFileInfo(String fileUniqID) {
        if (findByUUID(fileUniqID).getState() == State.DELETED) {
            throw new FileNotFoundException("File with ID [" + fileUniqID + "] has state 'DELETED'");
        }
        return new FileDto(findByUUID(fileUniqID));
    }

    @Override
    public void setFileState(ChangeStatusFile file, State state) {
        CustomFile customFile = findByUUID(file.getFileId());
        customFile.setState(state);
        customFile.setLastModification(new Date());
        customFile.setModificationAuthor(file.getAuthor());
        fileRepository.save(customFile);
    }

    @Override
    @Transactional
    public void completeDeleteFile(String fileUniqID) {
        if (fileRepository.existsByUniqID(fileUniqID)) {
            fileRepository.removeCustomFileByUniqID(fileUniqID);
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    @Override
    public List<CustomFile> getFilesByParentID(String uniqID) {
        return fileRepository.findAllByParentID(uniqID);
    }

    @Override
    public FileInfo getFileInfo(String fileUniqID) {   // used by files manager
        FileDto fileDto = showFileInfo(fileUniqID);
        return new FileInfo(
                fileDto.getName(),
                fileDto.getContentType(),
                fileUniqID,
                fileDto.getParentID(),
                fileDto.getCreationAuthor(),
                fileDto.getModificationAuthor(),
                fileDto.getCreated(),
                fileDto.getLastModification(),
                fileDto.getState()
        );
    }
}
