package com.sca.sca_application.ScaRunner;

import com.sca.sca_application.Configuration.*;
import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFilesLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesLoader;
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

@Component
public class ScaRunner {

    private final Map<String,ScaReporter> scaReporterMap;

    private final Map<String,ScaRulesLoader> scaRulesLoaderMap;
    private final Map<String, ScaFilesLoader> scaFilesLoaderMap;
    private Logger logger = LoggerFactory.getLogger(ScaRunner.class);


    @Autowired(required = false)
    public ScaRunner(Map<String, ScaReporter> scaReporterMap, Map<String, ScaRulesLoader> scaRulesLoaderMap,Map<String, ScaFilesLoader> scaFilesLoaderMap) {
        this.scaReporterMap = scaReporterMap;
        this.scaRulesLoaderMap = scaRulesLoaderMap;
        this.scaFilesLoaderMap = scaFilesLoaderMap;
    }

    public void run(ScaConfiguration configuration){

        ArrayList<ScaRule> scaRules = getScaRulesFromLoaders(configuration);

        List<ScaFilesLoaderConfiguration> filesLoadersList = configuration.getFilesLoadersList();

        List<ScaRuleInspectionResult> results = getScaRuleInspectionResults(scaRules, filesLoadersList);

        submitResultsToReporters(configuration, results);
    }

    private ArrayList<ScaRule> getScaRulesFromLoaders(ScaConfiguration configuration) {
        List<ScaRulesLoaderConfiguration> rulesLoaderList = configuration.getRulesLoaderList();
        if(CollectionUtils.isEmpty(rulesLoaderList)){
            throw new RuntimeException("cannot find rules loaders");
        }

        ArrayList<ScaRule> scaRules = new ArrayList<>();
        for (ScaRulesLoaderConfiguration scaRulesLoaderId : rulesLoaderList) {
            ScaRulesLoader scaRulesLoader = scaRulesLoaderMap.get(scaRulesLoaderId.getId());
            if(scaRulesLoader == null){
                throw new RuntimeException("Cannot find the rule loader " + scaRulesLoaderId);
            }
            scaRules.addAll(scaRulesLoader.getRules());
        }
        return scaRules;
    }

    private void submitResultsToReporters(ScaConfiguration configuration, List<ScaRuleInspectionResult> results) {

        logger.info("results = {}", Boon.toJson(results));

        List<ScaReporterConfiguration> reportersList = configuration.getReportersList();

        for (ScaReporterConfiguration scaReporterConfiguration : reportersList) {
            ScaReporter scaReporter = scaReporterMap.get(scaReporterConfiguration.getId());
            scaReporter.report(results);
        }
    }

    private List<ScaRuleInspectionResult> getScaRuleInspectionResults(ArrayList<ScaRule> scaRules, List<ScaFilesLoaderConfiguration> filesLoadersList) {
        List<ScaRuleInspectionResult> results = new ArrayList<>();

        for (ScaFilesLoaderConfiguration filesLoaderConfiguration : filesLoadersList) {
            ScaFilesLoader filesLoader = scaFilesLoaderMap.get(filesLoaderConfiguration.getId());
            if (filesLoader == null){
                throw new RuntimeException("Cannot find files loader " + filesLoaderConfiguration);
            }
            filesLoader.init(filesLoaderConfiguration.getParameters());
            ScaFileInformation scaFileInformation =  filesLoader.getNextFileInformation();
            while(scaFileInformation != null){
                InputStream inputStream  = scaFileInformation.getFileInputSteam();
                Scanner scanner = new Scanner(inputStream);
                int lineNumber = 0;
                while(scanner.hasNext()){
                    ++lineNumber;
                    String next = scanner.nextLine();
                    for (ScaRule scaRule : scaRules) {
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
