package kr.ac.skuniv.artsharing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ArtSharing2Application {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(ArtSharing2Application.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
		//SpringApplication.run(ArtSharing2Application.class, args);
	}


}
