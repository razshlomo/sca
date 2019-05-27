package com.sca.sca_application;

import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ScaApplication {


	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(ScaApplication.class, args);

		applicationContext.getBeansOfType(ConfigurationsLoader.class);
	}
}
