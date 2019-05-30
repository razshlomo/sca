package com.sca.sca_application.ScaFileLoader;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

import java.util.List;

/**
 * Loading files to inspect objects.
 */
public interface ScaFilesLoader {
    /**
     * Init method (to store stuff generally)
     * @param parameters parameters from the configuration object.
     */
    void init(List<String> parameters);

    /**
     * Method that returns the next file to inspect
     * @return next file that should be inspected.
     */
    ScaFileInformation getNextFileInformation();
}
