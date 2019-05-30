package com.sca.sca_application.ScaInspectors;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;

/**
 * interface for inspecting line. Used in order to be reused by rules.
 */
public interface ScaInspector {

    /**
     * Inspect line method
     * @param scaFileInformation File information of the inspected file.
     * @param lineNumber The inspected line number
     * @param lineToInspect line content to inspect
     * @param params optional params to be used from the rule.
     * @return inspection result object.
     */
    ScaRuleInspectionResult inspect(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect, Object... params);
}
