package com.sca.sca_application;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderConstants;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.junit.Assert;

import java.io.InputStream;

public class ScaTestUtils {

    public static final ScaFileInformation cSharpFile = new ScaFileInformation() {
        @Override
        public InputStream getFileInputSteam() {
            return null;
        }

        @Override
        public String getFilePath() {
            return "cSharpFile.cs";
        }

        @Override
        public String getFileExtension() {
            return ScaFileLoaderConstants.C_SHARP;
        }
    };


    public static final ScaFileInformation javaFile = new ScaFileInformation() {
        private String exampleFilePath = "file.java";
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


    public static void assertScaRuleInspectionResult(ScaFileInformation fileToTest,int numberOfExpectedIncidents, ScaRuleInspectionResult result, ScaRuleResultState expectedResultStatus) {
        Assert.assertEquals(expectedResultStatus, result.getResultStatus());
        Assert.assertEquals(numberOfExpectedIncidents,result.getScaIncidents() == null ? 0 : result.getScaIncidents().size());
        Assert.assertEquals(fileToTest.getFilePath(), result.getScaFileInformation().getFilePath());
    }
}
