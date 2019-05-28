package com.sca.sca_application.ScaFileLoader;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

public interface ScaFilesLoader {
    ScaFileInformation getNextFileInformation();
}
