package devs.nure.metainfoservice;

import devs.nure.metainfoservice.configuration.DeletedDirectoryNodeConfiguration;
import devs.nure.metainfoservice.configuration.RootDirectoryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableConfigurationProperties({
        DeletedDirectoryNodeConfiguration.class,
        RootDirectoryConfiguration.class
})
public class MetaInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetaInfoServiceApplication.class, args);
    }

}
