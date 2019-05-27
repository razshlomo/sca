package com.sca.sca_application.ConfigurationLoaders;

import com.sca.sca_application.Configuration.ScaConfiguration;

public interface ConfigurationsLoader {
    ScaConfiguration getScaConfiguration();
    void loadConfiguration(String[] args);
}
