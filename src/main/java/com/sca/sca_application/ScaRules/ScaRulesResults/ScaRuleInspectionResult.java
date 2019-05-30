package com.sca.sca_application.ScaRules.ScaRulesResults;


import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleResultState;

import java.util.List;

/**
 * Interface for results of inspection.
 */
public interface ScaRuleInspectionResult {
    String getErrorMessage();
    ScaRuleResultState getResultStatus();
    List<ScaIncident> getScaIncidents();
    void addScaIncident(ScaIncident... scaDefaultIncident);

    void setScaFileInformationResult(ScaFileInformationResult scaFileInformation);
    ScaFileInformationResult getScaFileInformation();
}
