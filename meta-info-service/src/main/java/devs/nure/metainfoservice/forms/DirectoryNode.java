package devs.nure.metainfoservice.forms;

import devs.nure.metainfoservice.Dto.DirectoryDto;
import lombok.Data;

import java.util.List;

@Data
public class DirectoryNode {

    private DirectoryDto directoryData;
    private List<DirectoryDto> directories;
    //private List<FilesDto> files;
}
