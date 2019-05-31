package com.sca.sca_application.ScaRunner;

import com.sca.sca_application.Configuration.*;
import com.sca.sca_application.ScaApplication;
import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFilesLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleLoaders.ScaRulesLoader;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.boon.Boon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main runner of the SCA application.
 */
@Component
public class ScaRunner {

    /**
     * Holds all reporters in the application
     */
    private final Map<String,ScaReporter> scaReporterMap;
    /**
     * Holds all file loaders in the application
     */
    private final Map<String, ScaFilesLoader> scaFilesLoaderMap;
    private Logger logger = LoggerFactory.getLogger(ScaRunner.class);


    @Autowired
    public ScaRunner(Map<String, ScaReporter> scaReporterMap,Map<String, ScaFilesLoader> scaFilesLoaderMap) {
        this.scaReporterMap = scaReporterMap;
        this.scaFilesLoaderMap = scaFilesLoaderMap;
    }

    public void run(ScaConfiguration configuration){

        //Load all rules from rules loaders
        ArrayList<ScaRule> scaRules = getScaRulesFromLoaders(configuration);

        List<ScaFilesLoaderConfiguration> filesLoadersList = configuration.getFilesLoadersList();

        List<ScaRuleInspectionResult> results = getScaRuleInspectionResults(scaRules, filesLoadersList);

        submitResultsToReporters(configuration, results);
    }

    /**
     * Pull out from the configuration the Rules that should be run
     * @param configuration Application configuration object
     * @return List of ScaRules loaded according to the configuration.
     */
    private ArrayList<ScaRule> getScaRulesFromLoaders(ScaConfiguration configuration) {
        List<ScaRulesLoaderConfiguration> rulesLoaderList = configuration.getRulesLoadersList();
        if(CollectionUtils.isEmpty(rulesLoaderList)){
            throw new RuntimeException("cannot find rules loaders");
        }

        ArrayList<ScaRule> scaRules = new ArrayList<>();
        for (ScaRulesLoaderConfiguration scaRulesLoaderConf : rulesLoaderList) {
            ScaRulesLoader scaRulesLoader = ScaApplication.getApplicationContext().getBean(scaRulesLoaderConf.getId(),ScaRulesLoader.class);
            if(scaRulesLoader == null){
                throw new RuntimeException("Cannot find the rule loader " + scaRulesLoaderConf);
            }
            scaRules.addAll(scaRulesLoader.getRules(scaRulesLoaderConf.getParameters()));
        }
        return scaRules;
    }

    /**
     * Pull out reporters from the configuration object and send them the results.
     * @param configuration Configuration object of the application
     * @param results Inspection results from the rules.
     */
    private void submitResultsToReporters(ScaConfiguration configuration, List<ScaRuleInspectionResult> results) {

        logger.info("results = {}", Boon.toJson(results));

        List<ScaReporterConfiguration> reportersList = configuration.getReportersList();

        for (ScaReporterConfiguration scaReporterConfiguration : reportersList) {
            ScaReporter scaReporter = scaReporterMap.get(scaReporterConfiguration.getId());
            scaReporter.report(results);
        }
    }

    /**
     * Perform the inspection of the files with the given rules.
     * @param scaRules Rules of the inspections
     * @param filesLoadersList File loaders to get the files.
     * @return List of inspection result object - collected from the rules.
     */
    private List<ScaRuleInspectionResult> getScaRuleInspectionResults(List<ScaRule> scaRules, List<ScaFilesLoaderConfiguration> filesLoadersList) {
        List<ScaRuleInspectionResult> results = new ArrayList<>();

        for (ScaFilesLoaderConfiguration filesLoaderConfiguration : filesLoadersList) {
            ScaFilesLoader filesLoader = scaFilesLoaderMap.get(filesLoaderConfiguration.getId());
            if (filesLoader == null){
                throw new RuntimeException("Cannot find files loader " + filesLoaderConfiguration);
            }
            filesLoader.init(filesLoaderConfiguration.getParameters());
            ScaFileInformation scaFileInformation =  filesLoader.getNextFileInformation();
            while(scaFileInformation != null){
                ScaFileInformation finalScaFileInformation = scaFileInformation;
                List<ScaRule> relevantRules = scaRules.stream().filter(scaRule -> scaRule.isFileRelevant(finalScaFileInformation)).collect(Collectors.toList());
                InputStream inputStream  = scaFileInformation.getFileInputSteam();
                Scanner scanner = new Scanner(inputStream);
                int lineNumber = 0;
                while(scanner.hasNext()){
                    ++lineNumber;
                    String next = scanner.nextLine();
                    for (ScaRule scaRule : relevantRules) {
                        ScaRuleInspectionResult scaRuleInspectionResult =  scaRule.inspectLine(scaFileInformation,lineNumber, next);
                        results.add(scaRuleInspectionResult);
                    }
                }

                scaFileInformation = filesLoader.getNextFileInformation();
            }
        }
        return results;
    }
}
