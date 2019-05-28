package com.sca.sca_application.ScaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;

public interface ScaRule {
    boolean isFileRelevant(ScaFileInformation scaFileInformation);
    ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect);
}
