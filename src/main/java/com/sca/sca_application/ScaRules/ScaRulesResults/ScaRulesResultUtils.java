package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

public class ScaRulesResultUtils {
    public static ScaFileInformationResult fromScaFileInformation(ScaFileInformation scaFileInformation){
        return new ScaFileInformationResult(scaFileInformation.getFilePath());
    }
}
