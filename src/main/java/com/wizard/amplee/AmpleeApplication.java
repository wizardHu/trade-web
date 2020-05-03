package com.wizard.amplee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wizard"})
public class AmpleeApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AmpleeApplication.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
			// 如果未在命令行设置 --spring.profiles.active=dev 以及环境变量中没有
			// SPRING_PROFILES_ACTIVE
			app.setAdditionalProfiles("dev");// 默认使用开发环境
		}
		ApplicationContext applicationContext = app.run(args);
	}

}
