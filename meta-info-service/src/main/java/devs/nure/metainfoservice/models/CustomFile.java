package devs.nure.metainfoservice.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class CustomFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortName;
    private String uniqID;
    private String parentID;
    private String creationAuthor;
    private String modificationAuthor;
    private Date created;
    private Date lastModification;
    private State state;

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
