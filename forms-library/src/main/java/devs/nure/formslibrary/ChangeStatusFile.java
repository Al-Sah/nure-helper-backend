package devs.nure.formslibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusFile {
//    @NotBlank(message = "fileId can't be empty")
    String fileId;
//    @NotBlank(message = "author cannot be empty")
    String author;
}
