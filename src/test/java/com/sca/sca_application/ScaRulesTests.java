package com.sca.sca_application;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaCSharpRules.CSharpLongLineRule;
import com.sca.sca_application.ScaRules.ScaJavaRules.JavaInvalidWordsRule;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaRulesTests {

    @Autowired
    private CSharpLongLineRule cSharpLongLineRule;
    @Autowired
    private JavaInvalidWordsRule javaInvalidWordsRule;

    @Test
    public void cSharpLongLineRuleTest(){
        ScaFileInformation cSharpFile = ScaTestUtils.cSharpFile;
        Assert.assertTrue(cSharpLongLineRule.isFileRelevant(cSharpFile));
        Assert.assertFalse(cSharpLongLineRule.isFileRelevant(ScaTestUtils.javaFile));

        ScaRuleInspectionResult result = cSharpLongLineRule.inspectLine(cSharpFile, 1, "01234567890123456789");
        ScaTestUtils.assertScaRuleInspectionResult(cSharpFile,0,result, ScaRuleResultState.PASS);

        result = cSharpLongLineRule.inspectLine(cSharpFile,1,"012345678901234567890");
        ScaTestUtils.assertScaRuleInspectionResult(cSharpFile,1,result,ScaRuleResultState.FAILED);
    }

    @Test
    public void javaInvalidWordsRuleTest(){
        ScaFileInformation javaFile = ScaTestUtils.javaFile;

        Assert.assertFalse(javaInvalidWordsRule.isFileRelevant(ScaTestUtils.cSharpFile));
        Assert.assertTrue(javaInvalidWordsRule.isFileRelevant(javaFile));

        ScaRuleInspectionResult result = javaInvalidWordsRule.inspectLine(javaFile, 1, "/*invalid word 'var' inside comment */");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,0,result,ScaRuleResultState.PASS);


        result = javaInvalidWordsRule.inspectLine(javaFile, 1, "invalid word 'var'");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,1,result,ScaRuleResultState.FAILED);

        result = javaInvalidWordsRule.inspectLine(javaFile, 1, "/*Starting comment block");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,0,result,ScaRuleResultState.PASS);


        result = javaInvalidWordsRule.inspectLine(javaFile, 1, "putting inside comment block invalid word 'var'");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,0,result,ScaRuleResultState.PASS);

        result = javaInvalidWordsRule.inspectLine(javaFile, 1, "in order to test comment blocks*/var again outside the comment block as invalid word");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,1,result,ScaRuleResultState.FAILED);

        result = javaInvalidWordsRule.inspectLine(javaFile, 1, "//testing comment line with invalid word var");
        ScaTestUtils.assertScaRuleInspectionResult(javaFile,0,result,ScaRuleResultState.PASS);
    }
}
