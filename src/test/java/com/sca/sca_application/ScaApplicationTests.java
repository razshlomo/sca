package com.sca.sca_application;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.Configuration.ScaFilesLoaderConfiguration;
import com.sca.sca_application.ConfigurationLoaders.internal.JsonConfigurationLoader;
import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.internal.ScaLoadFilesFromFileSystem;
import com.sca.sca_application.ScaInspectors.internal.InvalidWordsInspector;
import org.apache.commons.io.FilenameUtils;
import org.boon.Boon;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaApplicationTests {

	@Autowired
	ApplicationContext context;

	/**
	 * Test Configuration Element object keeps it's values
	 */
	@Test
	public void testConfigurationElement() {
		String[] params = {"param1", "param2"};
		String fileLoaderId = "fileLoaderId";
		ScaFilesLoaderConfiguration fileLoader = new ScaFilesLoaderConfiguration(fileLoaderId, params);

		Assert.assertEquals(fileLoaderId,fileLoader.getId());
		Assert.assertArrayEquals(params,fileLoader.getParameters().toArray());
	}

	@Test
	public void testJsonConfigurationLoader() throws JSONException {
		JsonConfigurationLoader jsonConfigurationLoader = context.getBean(JsonConfigurationLoader.class);
		String confJsonStr =
				"{\"filesLoadersList\":[{\"id\":\"scaLoadFilesFromFileSystem\",\"parameters\":[\"filesToTest/CsFileToTest.cs\",\"filesToTest/JavaFileToTest.java\",\"filesToTest/JavaFileToTest_2.java\",\"filesToTest/JsFileToTest.js\",\"filesToTest/JavaFileToTest_2.java1\"]}],\"rulesLoadersList\":[{\"id\":\"basicRulesLoader\",\"parameters\":[\"one\"]}],\"reportersList\":[{\"id\":\"scaThrowExceptionReporter\",\"parameters\":[\"one\"]}]}";
		jsonConfigurationLoader.loadConfiguration(new String[]{JsonConfigurationLoader.expectedArg,confJsonStr});


		ScaConfiguration scaConfiguration = jsonConfigurationLoader.getScaConfiguration();
		String confJsonStrAfterLoading = Boon.toJson(scaConfiguration);


		JSONAssert.assertEquals(confJsonStr,confJsonStrAfterLoading,true);
	}


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
