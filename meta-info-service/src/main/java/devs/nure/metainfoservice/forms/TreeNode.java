package devs.nure.metainfoservice.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    private BasicDirectoryInfo data;
    private ArrayList<TreeNode> children;
}
