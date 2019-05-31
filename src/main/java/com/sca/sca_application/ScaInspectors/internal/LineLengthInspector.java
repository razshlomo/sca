package com.sca.sca_application.ScaInspectors.internal;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaInspectors.ScaInspector;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.*;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaDefaultIncident;
import org.springframework.stereotype.Component;


/**
 * ScaInspector. inspect line by a given maximum length.
 */
@Component
public class LineLengthInspector implements ScaInspector {
    @Override
    public ScaRuleInspectionResult inspect(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect, Object... params) {

        if(params == null || params.length <1 || !(params[0] instanceof Integer)){
            throw new RuntimeException("Expecting to get number of the maximum valid length");
        }
        int maximumValidLength = (int) params[0];

        ScaFileInformationResult scaFileInformationResult = ScaRulesResultUtils.fromScaFileInformation(scaFileInformation);


        if(lineToInspect != null && lineToInspect.trim().length() > maximumValidLength){
            ScaDefaultIncident scaDefaultIncident = new ScaDefaultIncident();
            scaDefaultIncident.setLineNumber(lineNumber);

            ScaRuleInspectionResult result = new ScaDefaultErrorRuleInspectionResult("Line is longer than " + maximumValidLength, ScaRuleResultState.FAILED,scaFileInformationResult);
            result.setScaFileInformationResult(scaFileInformationResult);
            result.addScaIncident(scaDefaultIncident);

            return result;
        }

        ScaSuccessRuleIncident scaSuccessRuleIncident = new ScaSuccessRuleIncident();
        scaSuccessRuleIncident.setLineNumber(lineNumber);
        scaSuccessRuleIncident.setScaFileInformationResult(scaFileInformationResult);
        return scaSuccessRuleIncident;

    }
}
