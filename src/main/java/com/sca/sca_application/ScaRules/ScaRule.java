package com.sca.sca_application.ScaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

public interface ScaRule {
    ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, String lineToInspect);
}
