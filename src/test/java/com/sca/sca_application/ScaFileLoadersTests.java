package com.sca.sca_application;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.internal.ScaLoadFilesFromFileSystem;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaFileLoadersTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void testScaLoadFilesFromFileSystem(){

        ScaLoadFilesFromFileSystem scaLoadFilesFromFileSystem = context.getBean(ScaLoadFilesFromFileSystem.class);

        List<String> filesPathsToLoad = Arrays.asList(
                "filesToTest/CsFileToTest.cs",
                "filesToTest/JavaFileToTest.java",
                "filesToTest/JavaFileToTest_2.java",
                "filesToTest/JsFileToTest.js",
                "filesToTest/JavaFileToTest_2.java1"
        );
        scaLoadFilesFromFileSystem.init(filesPathsToLoad);


        ScaFileInformation nextFileInformation = scaLoadFilesFromFileSystem.getNextFileInformation();
        while (nextFileInformation !=null){

            String filePath = nextFileInformation.getFilePath();
            Assert.assertTrue(filesPathsToLoad.contains(filePath));

            String extension = FilenameUtils.getExtension(filePath);
            Assert.assertEquals(extension,nextFileInformation.getFileExtension());

            Assert.assertNotNull(nextFileInformation.getFileInputSteam());

            nextFileInformation = scaLoadFilesFromFileSystem.getNextFileInformation();
        }

    }
}
