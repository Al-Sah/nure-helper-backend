package devs.nure.metainfoservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "meta-info.deleted-dir-node")
public class DeletedDirectoryNodeConfiguration {

    Boolean show_nested_files;
    Boolean show_nested_directories;

}
