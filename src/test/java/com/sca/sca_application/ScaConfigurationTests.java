package com.sca.sca_application;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.Configuration.ScaFilesLoaderConfiguration;
import com.sca.sca_application.ConfigurationLoaders.internal.JsonConfigurationLoader;
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


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaConfigurationTests {

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
				"{\"filesLoadersList\":[{\"id\":\"scaLoadFilesFromFileSystem\",\"parameters\":[\"filesToTest/CsFileToTest.cs\",\"filesToTest/JavaFileToTest.java\",\"filesToTest/JavaFileToTest_2.java\",\"filesToTest/JsFileToTest.js\",\"filesToTest/JavaFileToTest_2.java1\"]}],\"rulesLoadersList\":[{\"id\":\"applicationContextRulesLoader\",\"parameters\":[\"one\"]}],\"reportersList\":[{\"id\":\"scaThrowExceptionReporter\",\"parameters\":[\"one\"]}]}";
		jsonConfigurationLoader.loadConfiguration(new String[]{JsonConfigurationLoader.expectedArg,confJsonStr});


		ScaConfiguration scaConfiguration = jsonConfigurationLoader.getScaConfiguration();
		String confJsonStrAfterLoading = Boon.toJson(scaConfiguration);


		JSONAssert.assertEquals(confJsonStr,confJsonStrAfterLoading,true);
	}
}
