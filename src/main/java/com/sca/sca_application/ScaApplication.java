package com.sca.sca_application;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import com.sca.sca_application.ConfigurationLoaders.internal.JsonConfigurationLoader;
import com.sca.sca_application.ScaRunner.ScaRunner;
import org.boon.Boon;
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

		String[] alternateArgs = getAlternateArgs();

		applicationContext = SpringApplication.run(ScaApplication.class, alternateArgs);

		Map<String, ConfigurationsLoader> beansOfType = applicationContext.getBeansOfType(ConfigurationsLoader.class);

		logger.info("beansOfType = {}",beansOfType);


		ScaConfiguration aggregatedConfiguration = getAggregatedConfiguration(alternateArgs, beansOfType);

		ScaRunner scaRunner = applicationContext.getBean(ScaRunner.class);
		scaRunner.run(aggregatedConfiguration);

	}

	private static String[] getAlternateArgs() {

		ScaConfiguration scaConfigurationTmp = new ScaConfiguration();

		scaConfigurationTmp.addRulesLoaders("basicJavaRulesLoader");
		scaConfigurationTmp.addFileLoaders("myLoader");
		scaConfigurationTmp.addReporters("blablabla");

		String confStr = Boon.toJson(scaConfigurationTmp);

		return new String[] {JsonConfigurationLoader.expectedArg,confStr};
	}

	private static ScaConfiguration getAggregatedConfiguration(String[] args, Map<String, ConfigurationsLoader> beansOfType) {
		ScaConfiguration aggregatedConfiguration = new ScaConfiguration();

		for (ConfigurationsLoader configurationsLoader: beansOfType.values()) {
			configurationsLoader.loadConfiguration(args);
			ScaConfiguration configuration = configurationsLoader.getScaConfiguration();
			aggregatedConfiguration.addReporters(configuration.getReportersList().toArray(new String[0]));
			aggregatedConfiguration.addRulesLoaders(configuration.getRulesLoaderList().toArray(new String[0]));
			aggregatedConfiguration.addFileLoaders(configuration.getFilesLoadersList().toArray(new String[0]));
		}
		return aggregatedConfiguration;
	}
}
