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
    private String uniqName;
    private String description;
    private Date created;

}
