package com.sca.sca_application.ScaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;

import java.util.List;

public interface ScaRuleInspectionResult {
    String getErrorMessage();
    ScaRuleResultState getResultStatus();
    List<ScaIncident> getScaIncidents();
    void addScaIncident(ScaInvalidWordIncident... scaDefaultIncident);

    void setScaFileInformation(ScaFileInformation scaFileInformation);
    ScaFileInformation getScaFileInformation();
}
