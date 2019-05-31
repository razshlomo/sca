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

/**
 * ScaReporter. Throwing an exception in case of an invalid inspection result.
 * Used to fail builds usually.
 */
@Component
public class ScaThrowExceptionReporter implements ScaReporter {
    private Logger logger = LoggerFactory.getLogger(ScaThrowExceptionReporter.class);
    public static final String throwExceptionMessage = "Errors found in the results";

    @Override
    public void report(List<ScaRuleInspectionResult> results) {

        List<ScaRuleInspectionResult> errors = results.stream()
                .filter(result -> ScaRuleResultState.FAILED.equals(result.getResultStatus()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(errors)){
            logger.info("No errors");
            return;
        }


        logger.error("Errors found in the inspected files:" + System.lineSeparator() + Boon.toPrettyJson(errors));

        throw new RuntimeException(throwExceptionMessage);
    }
}
