package devs.nure.formslibrary;

import lombok.Data;

//import javax.validation.constraints.NotBlank;

@Data
public class UpdateFile {
//    @NotBlank(message = "name cannot be empty")
    String name;
//    @NotBlank(message = "uniqId cannot be empty")
    String uniqId;
//    @NotBlank(message = "modificationAuthor cannot be empty")
    String modificationAuthor;
}
