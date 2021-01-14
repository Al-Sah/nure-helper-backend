package devs.nure.metainfoservice.forms;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateDirectory {
    @NotBlank(message = "short name cannot be empty")
    private String shortName;
    @NotBlank(message = "full name cannot be empty")
    private String fullName;
    private String description;
    @NotBlank(message = "uniqId cannot be empty")
    private String uniqId;
    @NotBlank(message = "uniqId cannot be empty")
    private String modificationAuthor;


}
