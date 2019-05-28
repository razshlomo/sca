package com.sca.sca_application.ScaFileLoader;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

import java.util.List;

public interface ScaFilesLoader {
    void init(List<String> parameters);
    ScaFileInformation getNextFileInformation();
}
