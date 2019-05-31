package com.sca.sca_application;


import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderConstants;
import com.sca.sca_application.ScaInspectors.internal.InvalidWordsInspector;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaFileInformationTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void testInvalidWordInspector(){
        InvalidWordsInspector invalidWordsInspector = context.getBean(InvalidWordsInspector.class);
        String exampleFilePath = "file.java";


        ScaFileInformation scaFileInformation = new ScaFileInformation() {
            @Override
            public InputStream getFileInputSteam() {
                return null;
            }

            @Override
            public String getFilePath() {

                return exampleFilePath;
            }

            @Override
            public String getFileExtension() {
                return ScaFileLoaderConstants.JAVA;
            }
        };

        int lineNumber = 1;

        ScaRuleInspectionResult result = invalidWordsInspector.inspect(scaFileInformation, lineNumber, "this line should pass", Collections.singletonList("otherWord"));
        assertScaRuleInspectionResult(0,exampleFilePath, result, ScaRuleResultState.PASS);

        result = invalidWordsInspector.inspect(scaFileInformation, lineNumber,"this line shouldn't pass", Collections.singletonList("line"));
        assertScaRuleInspectionResult(1,exampleFilePath, result, ScaRuleResultState.FAILED);

        //Verify line number inside the incident. Relevant only on failure inspection.
        Assert.assertEquals(lineNumber, result.getScaIncidents().get(0).getLineNumber());

    }

    private void assertScaRuleInspectionResult(int numberOfExpectedIncidents,String exampleFilePath, ScaRuleInspectionResult result, ScaRuleResultState failed) {
        Assert.assertEquals(failed, result.getResultStatus());
        Assert.assertEquals(numberOfExpectedIncidents,result.getScaIncidents() == null ? 0 : result.getScaIncidents().size());
        Assert.assertEquals(exampleFilePath, result.getScaFileInformation().getFilePath());
        Assert.assertEquals(exampleFilePath, result.getScaFileInformation().getFilePath());
    }
}
