package devs.nure.metainfoservice.Dto;

import devs.nure.metainfoservice.models.CustomFile;
import lombok.Data;

import java.util.Date;

@Data
public class FileDto {
    private String name;
    private String uniqID;
    private String parentID;
    private String creationAuthor;
    private String modificationAuthor;
    private Date created;
    private Date lastModification;
    private String state; // state == type of modification

    public FileDto(CustomFile file){
        this.name = file.getShortName();
        this.uniqID = file.getUniqID();
        this.parentID = file.getParentID();
        this.creationAuthor = file.getCreationAuthor();
        this.modificationAuthor = file.getModificationAuthor();
        this.created = file.getCreated();
        this.lastModification = file.getLastModification();
        this.state = file.getState().toString();
    }
}
