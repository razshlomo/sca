package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

public interface ScaFileInformationResult {

    String getFilePath();

    static ScaFileInformationResult fromScaFileInformation(ScaFileInformation scaFileInformation){
        return scaFileInformation::getFilePath;
    }

}