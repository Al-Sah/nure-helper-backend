package devs.nure.metainfoservice.forms;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseDirectory {

    private String shortName;
    private String fullName;
    private String uniqName;
    private String description;
    private Date created;

}
