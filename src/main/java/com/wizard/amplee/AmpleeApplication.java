package com.wizard.amplee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wizard"})
public class AmpleeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmpleeApplication.class, args);
	}

}
