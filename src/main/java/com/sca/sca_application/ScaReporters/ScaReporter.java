package com.sca.sca_application.ScaReporters;

import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;

import java.util.List;

/**
 * After having the inspection results, interface to reports that should do something with them.
 */
public interface ScaReporter {
    /**
     * Perform the reporting (report to server / log to file / etc.)
     * @param results The results of the inspection.
     */
    void report(List<ScaRuleInspectionResult> results);
}
