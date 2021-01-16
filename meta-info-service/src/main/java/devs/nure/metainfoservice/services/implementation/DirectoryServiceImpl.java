package devs.nure.metainfoservice.services.implementation;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.forms.*;
import devs.nure.metainfoservice.models.CustomDirectory;
import devs.nure.metainfoservice.models.State;
import devs.nure.metainfoservice.exceptions.*;
import devs.nure.metainfoservice.repositories.DirectoryRepository;
import devs.nure.metainfoservice.services.DirectoryService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;
    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @PostConstruct
    private void rootInit(){
        CustomDirectory customDirectory = new CustomDirectory(
                "root", "root",
                "rootDirStudHelper", null, "",
                "ROOT","ROOT",
                new Date(), new Date(), State.CREATED );
        directoryRepository.save(customDirectory);
    }

    @Override
    public DirectoryDto addDirectory(CreateDirectory directory) {
        if (directoryRepository.existsByUniqID(directory.getParentID())) {
            CustomDirectory customDirectory = new CustomDirectory(
                    directory.getShortName(),
                    directory.getFullName(),
                    UUID.randomUUID().toString(),
                    directory.getParentID(),
                    directory.getDescription(),
                    directory.getAuthor(),
                    directory.getAuthor(),
                    new Date(), new Date(),
                    State.CREATED);
            directoryRepository.save(customDirectory);
            return new DirectoryDto(customDirectory);
        } else {
            throw new DirectoryNotFoundException("parent directory not found");
        }
    }

    @Override
    public DirectoryDto updateDirectory(UpdateDirectory directory) {

        CustomDirectory customDirectory = directoryRepository
                .findByUniqID(directory.getUniqId())
                .orElseThrow(() -> new DirectoryNotFoundException("directory not found "));
        customDirectory.setShortName(directory.getShortName());
        customDirectory.setFullName(directory.getFullName());
        customDirectory.setDescription(directory.getDescription());
        customDirectory.setModificationAuthor(directory.getModificationAuthor());
        customDirectory.setLastModification(new Date());
        customDirectory.setState(State.UPDATED);

        directoryRepository.save(customDirectory);
        return new DirectoryDto(customDirectory);
    }

    @Override
    public DirectoryDto showDirectoryDescription(String dirUniqId) {
        return new DirectoryDto(directoryRepository.findByUniqID(dirUniqId)
                .orElseThrow(() -> new DirectoryNotFoundException("directory not found ")));
    }

    @Override
    public DirectoryNode showDirectory(String dirUniqID) {
        DirectoryDto dirData = new DirectoryDto(directoryRepository.findByUniqID(dirUniqID)
                .orElseThrow(() -> new DirectoryNotFoundException("directory not found ")));
        List<CustomDirectory> madelDirectories = directoryRepository.findAllByParentID(dirData.getUniqID());
        List<DirectoryDto> directoryDtos = new LinkedList<>();
        for (var element: madelDirectories) {
            directoryDtos.add(new DirectoryDto(element));
        }
        return new DirectoryNode(dirData, directoryDtos);
    }

    @Override
    public TreeNode generateDirectoriesTree(String thisID){
        TreeNode newNode = new TreeNode();
        BasicDirectoryInfo data = new BasicDirectoryInfo(directoryRepository.findByUniqID(thisID)
                .orElseThrow(() -> new DirectoryNotFoundException("directory not found ")));
        newNode.setData(data);
        newNode.setChildren(new ArrayList<>());
        List<CustomDirectory> modelDirectories = directoryRepository.findAllByParentID(thisID);
        for (var element : modelDirectories) {
            if (element.getState()!=State.DELETED) {
                newNode.getChildren().add(generateDirectoriesTree(element.getUniqID()));
            }
        }

        return newNode;
    }

    @Override
    public void setStatusDirectory(ChangeStatusDirectory directory, State state) {

        CustomDirectory customDirectory =  directoryRepository.findByUniqID(directory.getDirectoryId())
                .orElseThrow(() -> new DirectoryNotFoundException("directory not found "));
        customDirectory.setState(state);
        customDirectory.setLastModification(new Date());
        customDirectory.setModificationAuthor(directory.getAuthor());
        directoryRepository.save(customDirectory);

        List<CustomDirectory> modelDirectories = directoryRepository.findAllByParentID(directory.getDirectoryId());
        for (var element : modelDirectories) {
            setStatusDirectory(new ChangeStatusDirectory(element.getUniqID(), element.getCreationAuthor()), state);
        }
    }
}
