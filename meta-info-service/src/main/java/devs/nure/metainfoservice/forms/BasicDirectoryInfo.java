package devs.nure.metainfoservice.forms;

import devs.nure.metainfoservice.models.CustomDirectory;
import lombok.Data;

@Data
public class BasicDirectoryInfo {
    String shortName;
    String uniqID;
    String parentID;

    public BasicDirectoryInfo(CustomDirectory directory){
        this.shortName = directory.getShortName();
        this.uniqID = directory.getUniqID();
        this.parentID = directory.getParentID();
    }
}
