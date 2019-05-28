package com.sca.sca_application.ScaFileInformation;

import java.io.InputStream;

public interface ScaFileInformation {
    InputStream getFileInputSteam();
    String getFilePath();
    String getFileExtension();
}
