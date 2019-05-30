package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;

import java.util.List;

public interface ScaRuleInspectionResult {
    String getErrorMessage();
    ScaRuleResultState getResultStatus();
    List<ScaIncident> getScaIncidents();
    void addScaIncident(ScaIncident... scaDefaultIncident);

    void setScaFileInformationResult(ScaFileInformationResult scaFileInformation);
    ScaFileInformationResult getScaFileInformation();
}
