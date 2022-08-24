package microService.cropManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc

@SpringBootApplication
@EnableSwagger2
//@EnableMongoRepositories
public class CropManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CropManagementApplication.class, args);
	}

}
