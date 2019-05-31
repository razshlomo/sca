package com.sca.sca_application;

import com.sca.sca_application.Configuration.*;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import com.sca.sca_application.ConfigurationLoaders.internal.JsonConfigurationLoader;
import com.sca.sca_application.ScaRunner.ScaRunner;
import org.boon.Boon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class ScaApplication {

    private static ConfigurableApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private static Logger logger = LoggerFactory.getLogger(ScaApplication.class);

    public static void main(String[] args) {

        String[] alternateArgs = getAlternateArgs();

        applicationContext = SpringApplication.run(ScaApplication.class, alternateArgs);

        Map<String, ConfigurationsLoader> beansOfType = applicationContext.getBeansOfType(ConfigurationsLoader.class);

        logger.info("ConfigurationsLoader = {}", beansOfType);


        ScaConfiguration aggregatedConfiguration = getAggregatedConfiguration(alternateArgs, beansOfType);

        ScaRunner scaRunner = applicationContext.getBean(ScaRunner.class);
        scaRunner.run(aggregatedConfiguration);

    }

    private static String[] getAlternateArgs() {

        ScaConfiguration scaConfigurationTmp = new ScaConfiguration();

        scaConfigurationTmp.addRulesLoaders(new ScaRulesLoaderConfiguration("basicRulesLoader"));
        scaConfigurationTmp.addFileLoaders(new ScaFilesLoaderConfiguration("scaLoadFilesFromFileSystem", "filesToTest/CsFileToTest.cs", "filesToTest/JavaFileToTest.java", "filesToTest/JavaFileToTest_2.java", "filesToTest/JsFileToTest.js", "filesToTest/JavaFileToTest_2.java1"));
        scaConfigurationTmp.addReporters(new ScaReporterConfiguration("scaThrowExceptionReporter"));

        String confStr = Boon.toPrettyJson(scaConfigurationTmp);

        logger.info("Returning alternative args\n {}", confStr);
        return new String[]{JsonConfigurationLoader.expectedArg, confStr};
    }

    private static ScaConfiguration getAggregatedConfiguration(String[] args, Map<String, ConfigurationsLoader> beansOfType) {
        ScaConfiguration aggregatedConfiguration = new ScaConfiguration();

        for (ConfigurationsLoader configurationsLoader : beansOfType.values()) {
            configurationsLoader.loadConfiguration(args);
            ScaConfiguration configuration = configurationsLoader.getScaConfiguration();
            aggregatedConfiguration.addReporters(configuration.getReportersList().toArray(new ScaReporterConfiguration[0]));
            aggregatedConfiguration.addRulesLoaders(configuration.getRulesLoadersList().toArray(new ScaRulesLoaderConfiguration[0]));
            aggregatedConfiguration.addFileLoaders(configuration.getFilesLoadersList().toArray(new ScaFilesLoaderConfiguration[0]));
        }
        return aggregatedConfiguration;
    }
}
