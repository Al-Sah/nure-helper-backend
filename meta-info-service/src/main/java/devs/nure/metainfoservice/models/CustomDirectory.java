package devs.nure.metainfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class CustomDirectory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortName;
    private String fullName;
    private String uniqID;
    private String parentID;
    private String description;
    private String creationAuthor;
    private String modificationAuthor;
    private Date created;
    private Date lastModification;
    private State state;

    public CustomDirectory(
            String shortName, String fullName,
            String uniqID, String parentID,
            String description,
            String creationAuthor, String modificationAuthor,
            Date created, Date lastModification, State state){

        this.shortName = shortName;
        this.fullName = fullName;
        this.uniqID = uniqID;
        this.parentID = parentID;
        this.description = description;
        this.creationAuthor = creationAuthor;
        this.modificationAuthor = modificationAuthor;
        this.created = created;
        this.lastModification = lastModification;
        this.state = state;
    }
}
