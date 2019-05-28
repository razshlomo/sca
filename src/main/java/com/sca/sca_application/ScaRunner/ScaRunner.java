package com.sca.sca_application.ScaRunner;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFilesLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesLoader;
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



    @Autowired(required = false)
    public ScaRunner(Map<String, ScaReporter> scaReporterMap, Map<String, ScaRulesLoader> scaRulesLoaderMap,Map<String, ScaFilesLoader> scaFilesLoaderMap) {
        this.scaReporterMap = scaReporterMap;
        this.scaRulesLoaderMap = scaRulesLoaderMap;
        this.scaFilesLoaderMap = scaFilesLoaderMap;
    }

    public void run(ScaConfiguration configuration){

        ArrayList<ScaRule> scaRules = getScaRulesFromLoaders(configuration);

        List<String> filesLoadersList = configuration.getFilesLoadersList();

        List<ScaRuleInspectionResult> results = getScaRuleInspectionResults(scaRules, filesLoadersList);

        submitResultsToReporters(configuration, results);
    }

    private ArrayList<ScaRule> getScaRulesFromLoaders(ScaConfiguration configuration) {
        List<String> rulesLoaderList = configuration.getRulesLoaderList();
        if(CollectionUtils.isEmpty(rulesLoaderList)){
            throw new RuntimeException("cannot find rules loaders");
        }

        ArrayList<ScaRule> scaRules = new ArrayList<>();
        for (String scaRulesLoaderId : rulesLoaderList) {
            ScaRulesLoader scaRulesLoader = scaRulesLoaderMap.get(scaRulesLoaderId);
            scaRules.addAll(scaRulesLoader.getRules());
        }
        return scaRules;
    }

    private void submitResultsToReporters(ScaConfiguration configuration, List<ScaRuleInspectionResult> results) {
        List<String> reportersList = configuration.getReportersList();

        for (String scaReporterId : reportersList) {
            ScaReporter scaReporter = scaReporterMap.get(scaReporterId);
            scaReporter.report(results);
        }
    }

    private List<ScaRuleInspectionResult> getScaRuleInspectionResults(ArrayList<ScaRule> scaRules, List<String> filesLoadersList) {
        List<ScaRuleInspectionResult> results = new ArrayList<>();

        for (String filesLoaderStr : filesLoadersList) {
            ScaFilesLoader filesLoader = scaFilesLoaderMap.get(filesLoaderStr);
            ScaFileInformation scaFileInformation =  filesLoader.getNextFileInformation();
            while(scaFileInformation != null){
                InputStream inputStream  = scaFileInformation.getFileInputSteam();
                Scanner scanner = new Scanner(inputStream);

                while(scanner.hasNext()){
                    String next = scanner.next();
                    for (ScaRule scaRule : scaRules) {
                        ScaRuleInspectionResult scaRuleInspectionResult =  scaRule.inspectLine(scaFileInformation, next);
                        results.add(scaRuleInspectionResult);
                    }
                }

                scaFileInformation = filesLoader.getNextFileInformation();
            }
        }
        return results;
    }
}
