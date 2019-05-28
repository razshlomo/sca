package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRuleResultState;

import java.util.List;
import java.util.Objects;

public class ScaDefaultRuleInspectionResult implements ScaRuleInspectionResult {

    private String  errorMessage;
    private ScaRuleResultState resultStatus;
    private List<ScaIncident> scaIncidents;

    public void setScaIncidents(List<ScaIncident> scaIncidents) {
        this.scaIncidents = scaIncidents;
    }

    public void setResultStatus(ScaRuleResultState resultStatus) {
        this.resultStatus = resultStatus;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public ScaRuleResultState getResultStatus() {
        return null;
    }

    @Override
    public List<ScaIncident> getScaIncidents() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaDefaultRuleInspectionResult that = (ScaDefaultRuleInspectionResult) o;
        return Objects.equals(errorMessage, that.errorMessage) &&
                resultStatus == that.resultStatus &&
                Objects.equals(scaIncidents, that.scaIncidents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, resultStatus, scaIncidents);
    }
}
