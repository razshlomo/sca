package com.sca.sca_application.ScaRules;

import java.util.List;

public interface ScaRuleInspectionResult {
    String getErrorMessage();
    ScaRuleResultState getResultStatus();
    List<ScaIncident> getScaIncidents();
}
