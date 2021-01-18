package devs.nure.formslibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    String name;
    String type;
//    float size;
    String uniqId;
    String parentID;
    String creationAuthor;
    String modificationAuthor;
    Date created;
    Date lastModification;
    String state;

}
