package com.sca.sca_application.ScaReporters.internal;

import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.boon.Boon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScaThrowExceptionReporter implements ScaReporter {
    private Logger logger = LoggerFactory.getLogger(ScaThrowExceptionReporter.class);

    @Override
    public void report(List<ScaRuleInspectionResult> results) {

        List<ScaRuleInspectionResult> errors = results.stream()
                .filter(result -> ScaRuleResultState.FAILED.equals(result.getResultStatus()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(errors)){
            logger.info("No errors");
            return;
        }


        throw new RuntimeException("Errors found in the inspected files: " + System.lineSeparator() + Boon.toPrettyJson(errors));
    }
}
