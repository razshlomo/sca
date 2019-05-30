package com.sca.sca_application.ScaRules.ScaRulesResults;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScaDefaultErrorRuleInspectionResult implements ScaRuleInspectionResult {

    private String errorMessage;
    private ScaRuleResultState resultStatus;
    private List<ScaIncident> scaIncidents;
    private ScaFileInformationResult scaFileInformation;


    public ScaDefaultErrorRuleInspectionResult(String errorMessage, ScaRuleResultState resultStatus, ScaFileInformationResult scaFileInformation, ScaIncident... scaIncidents) {
        this.errorMessage = errorMessage;
        this.resultStatus = resultStatus;
        this.scaIncidents = new ArrayList<>();
        this.scaIncidents.addAll(Arrays.asList(scaIncidents));
        this.scaFileInformation = scaFileInformation;
    }

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
        return errorMessage;
    }

    @Override
    public ScaRuleResultState getResultStatus() {
        return resultStatus;
    }

    @Override
    public List<ScaIncident> getScaIncidents() {
        return scaIncidents;
    }

    @Override
    public void addScaIncident(ScaIncident... scaIncidentsArr) {
        if(scaIncidentsArr == null){
            return;
        }
        if (scaIncidents == null) {
            scaIncidents = new ArrayList<>();
        }
        scaIncidents.addAll(Arrays.asList(scaIncidentsArr));
    }

    public ScaFileInformationResult getScaFileInformation() {
        return scaFileInformation;
    }

    @Override
    public void setScaFileInformationResult(ScaFileInformationResult scaFileInformation) {
        this.scaFileInformation = scaFileInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaDefaultErrorRuleInspectionResult that = (ScaDefaultErrorRuleInspectionResult) o;
        return Objects.equals(errorMessage, that.errorMessage) &&
                resultStatus == that.resultStatus &&
                Objects.equals(scaIncidents, that.scaIncidents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage, resultStatus, scaIncidents);
    }
}
