package com.sca.sca_application;

import com.sca.sca_application.ScaReporters.internal.ScaThrowExceptionReporter;
import com.sca.sca_application.ScaRules.ScaIncident;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaFileInformationResult;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaReportersTests {

    @Autowired
    private ApplicationContext context;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private final ScaRuleInspectionResult failedResult = new ScaRuleInspectionResult() {
        @Override
        public ScaRuleResultState getResultStatus() {
            return ScaRuleResultState.FAILED;
        }

        @Override
        public String getErrorMessage() {return null;}
        @Override
        public List<ScaIncident> getScaIncidents() {return null;}
        @Override
        public void addScaIncident(ScaIncident... scaDefaultIncident) {}
        @Override
        public void setScaFileInformationResult(ScaFileInformationResult scaFileInformation) {}
        @Override
        public ScaFileInformationResult getScaFileInformation() {return null;}
    };

    private ScaRuleInspectionResult successResult = new ScaRuleInspectionResult() {

        @Override
        public ScaRuleResultState getResultStatus() {
            return ScaRuleResultState.PASS;
        }

        @Override
        public String getErrorMessage() {return null;}
        @Override
        public List<ScaIncident> getScaIncidents() {return null;}
        @Override
        public void addScaIncident(ScaIncident... scaDefaultIncident) {}
        @Override
        public void setScaFileInformationResult(ScaFileInformationResult scaFileInformation) {}
        @Override
        public ScaFileInformationResult getScaFileInformation() {return null;}
    };;

    @Test
    public void ScaThrowExceptionReporterTest(){
        ScaThrowExceptionReporter scaThrowExceptionReporter = context.getBean(ScaThrowExceptionReporter.class);
        scaThrowExceptionReporter.report(Collections.singletonList(successResult));

        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(ScaThrowExceptionReporter.throwExceptionMessage);

        scaThrowExceptionReporter.report(Collections.singletonList(failedResult));
    }

}
