package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;

import java.util.List;
import java.util.Objects;

public class ScaSuccessRuleIncident implements ScaRuleInspectionResult {

    private int lineNumber;
    private ScaFileInformationResult scaFileInformationResult;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

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
    public void addScaIncident(ScaInvalidWordIncident... scaDefaultIncident) {}

    @Override
    public void setScaFileInformation(ScaFileInformationResult scaFileInformationResult) {
        this.scaFileInformationResult = scaFileInformationResult;
    }

    @Override
    public ScaFileInformationResult getScaFileInformation() {
        return scaFileInformationResult;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaSuccessRuleIncident that = (ScaSuccessRuleIncident) o;
        return lineNumber == that.lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber);
    }
}
