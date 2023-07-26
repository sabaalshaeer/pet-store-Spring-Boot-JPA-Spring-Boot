package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication /*
						 * this annotation is used as the main starting point for a Spring Boot application.
						 * It enables component scanning, automatically finding and configuring
						 * components like controllers, services and DAOs within the
						 * application package and its sub-packages. Additionally, if the application
						 * includes Spring Web dependencies, this annotation starts an embedded web
						 * server (like Tomcat) to handle incoming HTTP requests, making it easy to
						 * create RESTful APIs or web applications.
						 */
public class PetStoreApplication {

	public static void main(String[] args) {
		// start spring boot
		SpringApplication.run(PetStoreApplication.class, args);

	}

}
