package com.sca.sca_application;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import com.sca.sca_application.ScaRunner.ScaRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class ScaApplication {

	private static ConfigurableApplicationContext applicationContext;

	private static Logger logger = LoggerFactory.getLogger(ScaApplication.class);

	public static void main(String[] args) {

		applicationContext = SpringApplication.run(ScaApplication.class, args);

		Map<String, ConfigurationsLoader> beansOfType = applicationContext.getBeansOfType(ConfigurationsLoader.class);

		logger.info("beansOfType = {}",beansOfType);

		ScaConfiguration aggregatedConfiguration = getAggregatedConfiguration(args, beansOfType);

		ScaRunner scaRunner = applicationContext.getBean(ScaRunner.class);
		scaRunner.run(aggregatedConfiguration);

	}

	private static ScaConfiguration getAggregatedConfiguration(String[] args, Map<String, ConfigurationsLoader> beansOfType) {
		ScaConfiguration aggregatedConfiguration = new ScaConfiguration();

		for (ConfigurationsLoader configurationsLoader: beansOfType.values()) {
			configurationsLoader.loadConfiguration(args);
			ScaConfiguration configuration = configurationsLoader.getScaConfiguration();
			aggregatedConfiguration.addReporters(configuration.getReportersList().toArray(new String[0]));
			aggregatedConfiguration.addRulesLoaders(configuration.getRulesLoaderList().toArray(new String[0]));
		}
		return aggregatedConfiguration;
	}
}
