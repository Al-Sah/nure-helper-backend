package devs.nure.metainfoservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "meta-info.root-dir")
public class RootDirectoryConfiguration {
    String name;
    String id;
}
