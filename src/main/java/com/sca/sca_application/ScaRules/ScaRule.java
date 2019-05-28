package com.sca.sca_application.ScaRules;

public interface ScaRule {
    ScaRuleInspectionResult inspectLine(String next);
}
