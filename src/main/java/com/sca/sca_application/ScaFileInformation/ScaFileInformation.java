package com.sca.sca_application.ScaFileInformation;

import java.io.InputStream;

/**
 * Contains information about file to inspect
 */
public interface ScaFileInformation {
    /**
     * @return get the input stream of the file content
     */
    InputStream getFileInputSteam();

    /**
     * @return return the path to the file
     */
    String getFilePath();

    /*
     * @return Returns the file extension.
     */
    String getFileExtension();
}
