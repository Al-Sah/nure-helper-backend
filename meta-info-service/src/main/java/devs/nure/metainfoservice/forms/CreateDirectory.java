package devs.nure.metainfoservice.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDirectory {

    @NotBlank(message = "short name cannot be empty")
    private String shortName;
    @NotBlank(message = "full name cannot be empty")
    private String fullName;
    private String description;
    @NotBlank(message = "author cannot be empty")
    private String author;
    @NotBlank(message = "parentID cannot be empty")
    private String parentID;

}
