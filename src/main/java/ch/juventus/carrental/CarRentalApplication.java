package ch.juventus.carrental;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

	@Bean
	public OpenAPI openApiConfig() {
		return new OpenAPI().info(createApiInfo());
	}

	private Info createApiInfo() {
		Info info = new Info();
		info.title("Car rental API")
				.description("Car rental API as backend of Car rental application")
				.version("v1.0.0");
		return info;
	}
}
