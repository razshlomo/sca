package com.sca.sca_application.ConfigurationLoaders.internal;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import org.boon.Boon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Load configuration object from JSON string.
 */
@Component
public class JsonConfigurationLoader implements ConfigurationsLoader {


    public static final String expectedArg = "-json";

    private ScaConfiguration scaConfiguration;
    private static Logger logger = LoggerFactory.getLogger(JsonConfigurationLoader.class);

    @Override
    public ScaConfiguration getScaConfiguration() {
        return scaConfiguration;
    }

    @Override
    public void loadConfiguration(String[] args) {

        if(args == null || args.length < 2){
            throw new RuntimeException("Must have at least 2 parameters");
        }

        if(!expectedArg.equals(args[0])){
            return;
        }

        String jsonStr = args[1];

        logger.info("loading configuration from json string:");
        logger.info(jsonStr);

        this.scaConfiguration = Boon.fromJson(jsonStr, ScaConfiguration.class);
    }
}
