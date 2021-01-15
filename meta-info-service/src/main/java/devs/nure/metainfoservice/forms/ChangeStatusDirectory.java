package devs.nure.metainfoservice.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDirectory {
    @NotBlank(message = "directoryId cannot be empty")
    String directoryId;
    @NotBlank(message = "author cannot be empty")
    String author;
}
