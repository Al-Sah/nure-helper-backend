package devs.nure.metainfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MetaInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetaInfoServiceApplication.class, args);
    }

}