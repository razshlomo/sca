package com.sca.sca_application;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRulesLoader;
import com.sca.sca_application.ScaRunner.ScaRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class ScaApplication {

	private static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(ScaApplication.class, args);

		Map<String, ConfigurationsLoader> beansOfType = applicationContext.getBeansOfType(ConfigurationsLoader.class);

		ScaConfiguration aggregatedConfiguration = new ScaConfiguration();

		for (ConfigurationsLoader configurationsLoader: beansOfType.values()) {
			configurationsLoader.loadConfiguration(args);
			ScaConfiguration configuration = configurationsLoader.getConfiguration();
			aggregatedConfiguration.addReporters(configuration.getReportersList().toArray(new ScaReporter[0]));
			aggregatedConfiguration.addRules(configuration.getRulesLoaderList().toArray(new ScaRulesLoader[0]));
		}

		ScaRunner scaRunner = applicationContext.getBean(ScaRunner.class);
		scaRunner.run(aggregatedConfiguration);

	}
}
