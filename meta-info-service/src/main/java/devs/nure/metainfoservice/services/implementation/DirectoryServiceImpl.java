package devs.nure.metainfoservice.services.implementation;

import devs.nure.formslibrary.ChangeStatusFile;
import devs.nure.metainfoservice.Dto.*;
import devs.nure.metainfoservice.configuration.DeletedDirectoryNodeConfiguration;
import devs.nure.metainfoservice.configuration.RootDirectoryConfiguration;
import devs.nure.metainfoservice.forms.*;
import devs.nure.metainfoservice.models.*;
import devs.nure.metainfoservice.exceptions.*;
import devs.nure.metainfoservice.repositories.DirectoryRepository;
import devs.nure.metainfoservice.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final FileService fileService;
    private final boolean showDirectories;
    private final boolean showFiles;
    private final String rootDirectoryName;
    private final String rootDirectoryID;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository,
                                FileService fileService,
                                DeletedDirectoryNodeConfiguration node,
                                RootDirectoryConfiguration rootConfig) {
        this.directoryRepository = directoryRepository;
        this.fileService = fileService;
        this.showFiles = node.getShow_nested_files();
        this.showDirectories = node.getShow_nested_directories();
        this.rootDirectoryName = rootConfig.getName();
        this.rootDirectoryID = rootConfig.getId();
    }

    private CustomDirectory findByUUID(String uniqId){
        return directoryRepository.findByUniqID(uniqId)
                .orElseThrow(() -> new FileNotFoundException("Directory with ID ["+uniqId+"] not found"));
    }

    @PostConstruct
    private void rootInit(){
        if(!directoryRepository.existsByUniqID(rootDirectoryID)) {
            CustomDirectory customDirectory = new CustomDirectory(
                    rootDirectoryName, rootDirectoryName, rootDirectoryID, null, "",
                    rootDirectoryName, rootDirectoryName, new Date(), new Date(), State.CREATED);
            directoryRepository.save(customDirectory);
        }
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
            throw new DirectoryNotFoundException("Parent directory with ID ["+directory.getParentID()+"] not found");
        }
    }

    @Override
    public DirectoryDto updateDirectory(UpdateDirectory directory) {

        CustomDirectory customDirectory = findByUUID(directory.getUniqId());
        if (customDirectory.getState() == State.DELETED) {
            throw new DirectoryNotFoundException("Directory ["+directory.getUniqId()+"] has 'deleted' state");
        }
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
        return new DirectoryDto( findByUUID(dirUniqId) );
    }

    @Override
    public DirectoryNode showDirectory(String dirUniqID) {
        DirectoryDto dirData = new DirectoryDto( findByUUID(dirUniqID) );
        String dirState = dirData.getState();
        List<CustomDirectory> modelDirectories = directoryRepository.findAllByParentID(dirData.getUniqID());
        List<CustomFile> modelFiles = fileService.getFilesByParentID(dirData.getUniqID());
        List<DirectoryDto> directoryDtos = new LinkedList<>();
        List<FileDto> fileDtos = new LinkedList<>();

        if(!dirState.equals("DELETED") || showDirectories){
            for (var element: modelDirectories) {
                directoryDtos.add(new DirectoryDto(element));
            }
        }
        if(!dirState.equals("DELETED") || showFiles) {
            for (var element : modelFiles) {
                fileDtos.add(new FileDto(element));
            }
        }
        return new DirectoryNode(dirData, directoryDtos, fileDtos);
    }

    @Override
    public TreeNode generateDirectoriesTree(String thisID){
        TreeNode newNode = new TreeNode();
        BasicDirectoryInfo data = new BasicDirectoryInfo(findByUUID(thisID));
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
    public void setDirectoryState(ChangeStatusDirectory directory, State state) {
        CustomDirectory customDirectory = findByUUID(directory.getDirectoryId());
        customDirectory.setState(state);
        customDirectory.setLastModification(new Date());
        customDirectory.setModificationAuthor(directory.getAuthor());
        directoryRepository.save(customDirectory);

        List<CustomDirectory> modelDirectories = directoryRepository.findAllByParentID(directory.getDirectoryId());
        List<CustomFile> modelFiles = fileService.getFilesByParentID(directory.getDirectoryId());

        for (var element : modelDirectories) {
            setDirectoryState(new ChangeStatusDirectory(element.getUniqID(), element.getCreationAuthor()), state);
        }
        for (var element : modelFiles) {
            fileService.setFileState(new ChangeStatusFile(element.getUniqID(), element.getCreationAuthor()), state);
        }
    }

    @Override
    @Transactional
    public void removeDirectory(ChangeStatusDirectory deleteDirectory) {
        if(directoryRepository.findAllByParentID(deleteDirectory.getDirectoryId()).isEmpty() &&
                fileService.getFilesByParentID(deleteDirectory.getDirectoryId()).isEmpty()){
            directoryRepository.removeCustomDirectoryByUniqID(deleteDirectory.getDirectoryId());
        } else {
            setDirectoryState(deleteDirectory, State.DELETED);
        }
    }

    @Override
    @Transactional
    public void completeDeleteDirectory(String dirUniqID) {
        if (!directoryRepository.existsByUniqID(dirUniqID)) {
            throw new DirectoryNotFoundException("Directory with ID ["+dirUniqID+"] not found");
        }
        List<CustomDirectory> modelDirectories = directoryRepository.findAllByParentID(dirUniqID);
        // TODO deletion files from storage (send request to the files manager)
        fileService.deleteFilesInfo(dirUniqID);
        for (var element : modelDirectories) {
            completeDeleteDirectory(element.getUniqID());
        }
        directoryRepository.removeCustomDirectoryByUniqID(dirUniqID);
    }

}
