package com.sca.sca_application.Configuration;

import java.util.List;

/**
 * Describes an element in the configuration object.
 */
public interface ScaConfigurationElement {
    List<String> getParameters();

    /**
     * @return unique identifier of the object that the element describes
     */
    String getId();
}
