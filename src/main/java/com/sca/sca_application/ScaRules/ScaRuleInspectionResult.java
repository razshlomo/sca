package com.sca.sca_application.ScaRules;

public interface ScaRuleInspectionResult {
    String getErrorMessage();
    ScaRuleResultState resultStatus();
    int lineNumber();
    int columnNumber();
}
