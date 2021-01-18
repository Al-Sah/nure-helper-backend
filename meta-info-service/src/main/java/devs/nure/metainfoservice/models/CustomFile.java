package devs.nure.metainfoservice.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class CustomFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String shortName;
    protected String uniqID;
    protected String parentID;
    protected String creationAuthor;
    protected String modificationAuthor;
    protected Date created;
    protected Date lastModification;
    protected State state;

    public CustomFile(
            String name, String uniqID, String parentID,
            String creationAuthor, String modificationAuthor,
            Date created, Date lastModification, State state){

        this.shortName = name;
        this.uniqID = uniqID;
        this.parentID = parentID;
        this.creationAuthor = creationAuthor;
        this.modificationAuthor = modificationAuthor;
        this.created = created;
        this.lastModification = lastModification;
        this.state = state;
    }

}
