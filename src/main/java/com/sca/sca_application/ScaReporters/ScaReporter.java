package com.sca.sca_application.ScaReporters;

import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;

import java.util.List;

public interface ScaReporter {
    void report(List<ScaRuleInspectionResult> results);
}
