package com.sca.sca_application.ConfigurationLoaders.internal;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ConfigurationLoaders.ConfigurationsLoader;
import org.boon.Boon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonConfigurationLoader implements ConfigurationsLoader {


    private ScaConfiguration scaConfiguration;
    private static Logger logger = LoggerFactory.getLogger(JsonConfigurationLoader.class);

    @Override
    public ScaConfiguration getScaConfiguration() {
        return scaConfiguration;
    }

    @Override
    public void loadConfiguration(String[] args) {

        ScaConfiguration scaConfigurationTmp = new ScaConfiguration();

        scaConfigurationTmp.addRulesLoaders("FileSystemFilesLoader");

        String s = Boon.toJson(scaConfigurationTmp);
        logger.info("configuration:");
        logger.info(s);

        if(args == null || args.length < 2){
            throw new RuntimeException("Must have at least 2 parameters");
        }

        if(!"-json".equals(args[0])){
            return;
        }

        String jsonStr = args[1];
        this.scaConfiguration = Boon.fromJson(jsonStr, ScaConfiguration.class);
    }
}
