package devs.nure.formslibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    @NotBlank(message = "name can't be empty")
    String name;
    @NotBlank(message = "contentType can't be empty")
    String contentType;
    @NotBlank(message = "uniqId can't be empty")
    String uniqId;
    @NotBlank(message = "parentID can't be empty")
    String parentID;
    @NotBlank(message = "creationAuthor can't be empty")
    String creationAuthor;
    @NotBlank(message = "modificationAuthor can't be empty")
    String modificationAuthor;
    @NotBlank(message = "creation date can't be empty")
    Date created;
    @NotBlank(message = "lastModification can't be empty")
    Date lastModification;
    @NotBlank(message = "state can't be empty")
    String state;

}
