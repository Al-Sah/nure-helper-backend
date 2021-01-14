package devs.nure.metainfoservice.Dto;

import devs.nure.metainfoservice.models.CustomDirectory;
import lombok.Data;

import java.util.Date;

@Data
public class DirectoryDto {

    private String shortName;
    private String fullName;
    private String uniqID;
    private String description;
    private String creationAuthor;
    private String modificationAuthor;
    private Date created;
    private Date lastModification;
    private String state; // state == type of modification

    public DirectoryDto(CustomDirectory directory){
        this.shortName = directory.getShortName();
        this.fullName = directory.getFullName();
        this.uniqID = directory.getUniqID();
        this.description = directory.getDescription();
        this.creationAuthor = directory.getCreationAuthor();
        this.modificationAuthor = directory.getModificationAuthor();
        this.created = directory.getCreated();
        this.lastModification = directory.getLastModification();
        this.state =directory.getState();
    }


}
