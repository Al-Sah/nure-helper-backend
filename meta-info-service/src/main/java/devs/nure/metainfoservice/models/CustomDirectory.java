package devs.nure.metainfoservice.models;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CustomDirectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortName;
    private String fullName;
    private String uniqID;
    private String description;
    private String creationAuthor;
    private String modificationAuthor;
    private Date created;
    private Date lastModification;
    private String state; // TODO state as a enum

}
