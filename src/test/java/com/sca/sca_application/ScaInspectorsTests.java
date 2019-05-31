package com.sca.sca_application;


import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaInspectors.internal.InvalidWordsInspector;
import com.sca.sca_application.ScaInspectors.internal.LineLengthInspector;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaInspectorsTests {

    private final ScaFileInformation javaFileToTest = ScaTestUtils.javaFile;
    @Autowired
    private ApplicationContext context;


    @Test
    public void testLineLengthInspector(){
        LineLengthInspector lineLengthInspector = context.getBean(LineLengthInspector.class);

        ScaRuleInspectionResult result = lineLengthInspector.inspect(javaFileToTest, 1, "0123456789", 10);
        ScaTestUtils.assertScaRuleInspectionResult(javaFileToTest,0,result,ScaRuleResultState.PASS);

        result = lineLengthInspector.inspect(javaFileToTest,1,"01234567890",10);
        ScaTestUtils.assertScaRuleInspectionResult(javaFileToTest,1,result,ScaRuleResultState.FAILED);
    }

    @Test
    public void testInvalidWordInspector(){
        InvalidWordsInspector invalidWordsInspector = context.getBean(InvalidWordsInspector.class);


        int lineNumber = 1;

        ScaRuleInspectionResult result = invalidWordsInspector.inspect(javaFileToTest, lineNumber, "this line should pass", Collections.singletonList("otherWord"));
        ScaTestUtils.assertScaRuleInspectionResult(ScaTestUtils.javaFile,0, result, ScaRuleResultState.PASS);

        result = invalidWordsInspector.inspect(javaFileToTest, lineNumber,"this line shouldn't pass", Collections.singletonList("line"));
        ScaTestUtils.assertScaRuleInspectionResult(ScaTestUtils.javaFile, 1, result, ScaRuleResultState.FAILED);

        //Verify line number inside the incident. Relevant only on failure inspection.
        Assert.assertEquals(lineNumber, result.getScaIncidents().get(0).getLineNumber());

    }
}
