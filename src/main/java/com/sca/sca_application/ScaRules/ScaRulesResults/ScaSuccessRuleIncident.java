package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;

import java.util.List;

public class ScaSuccessRuleIncident implements ScaRuleInspectionResult {
    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public ScaRuleResultState getResultStatus() {
        return ScaRuleResultState.PASS;
    }

    @Override
    public List<ScaIncident> getScaIncidents() {
        return null;
    }

    @Override
    public void addScaIncident(ScaInvalidWordIncident... scaDefaultIncident) {

    }

    @Override
    public void setScaFileInformation(ScaFileInformation scaFileInformation) {

    }

    @Override
    public ScaFileInformation getScaFileInformation() {
        return null;
    }
}
