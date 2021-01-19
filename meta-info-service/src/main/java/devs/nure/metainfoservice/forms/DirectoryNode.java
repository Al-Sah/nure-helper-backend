package devs.nure.metainfoservice.forms;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import devs.nure.metainfoservice.Dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryNode {

    private DirectoryDto directoryData;
    private List<DirectoryDto> directories;
    private List<FileDto> files;

}
