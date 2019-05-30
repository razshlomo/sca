package com.sca.sca_application.ScaInspectors;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;

public interface ScaInspector {
    ScaRuleInspectionResult inspect(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect, Object... params);
}
