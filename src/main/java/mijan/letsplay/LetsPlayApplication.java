package mijan.letsplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "mijan.letsplay")

public class LetsPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsPlayApplication.class, args);
	}

}
