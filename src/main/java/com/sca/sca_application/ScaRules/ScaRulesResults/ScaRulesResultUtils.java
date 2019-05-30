package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

/**
 * Utils for hanling with the result object
 */
public class ScaRulesResultUtils {

    /**
     * Created file information object to the reporter from inspection file information object
     * @param scaFileInformation inspection file information object
     * @return new file information result object
     */
    public static ScaFileInformationResult fromScaFileInformation(ScaFileInformation scaFileInformation){
        return new ScaFileInformationResult(scaFileInformation.getFilePath());
    }
}
