package com.sca.sca_application.ScaRunner;

import com.sca.sca_application.Configuration.ScaConfiguration;
import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.FilesLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ScaRunner {

    public void run(ScaConfiguration configuration){

        ArrayList<ScaRule> scaRules = getScaRulesFromLoaders(configuration);

        List<FilesLoader> filesLoadersList = configuration.getFilesLoadersList();

        List<ScaRuleInspectionResult> results = getScaRuleInspectionResults(scaRules, filesLoadersList);

        submitResultsToReporters(configuration, results);
    }

    private ArrayList<ScaRule> getScaRulesFromLoaders(ScaConfiguration configuration) {
        List<ScaRulesLoader> rulesLoaderList = configuration.getRulesLoaderList();
        if(CollectionUtils.isEmpty(rulesLoaderList)){
            throw new RuntimeException("cannot find rules loaders");
        }

        ArrayList<ScaRule> scaRules = new ArrayList<>();
        for (ScaRulesLoader scaRulesLoader : rulesLoaderList) {
            scaRules.addAll(scaRulesLoader.getRules());
        }
        return scaRules;
    }

    private void submitResultsToReporters(ScaConfiguration configuration, List<ScaRuleInspectionResult> results) {
        List<ScaReporter> reportersList = configuration.getReportersList();

        for (ScaReporter scaReporter : reportersList) {
            scaReporter.report(results);
        }
    }

    private List<ScaRuleInspectionResult> getScaRuleInspectionResults(ArrayList<ScaRule> scaRules, List<FilesLoader> filesLoadersList) {
        List<ScaRuleInspectionResult> results = new ArrayList<>();

        for (FilesLoader filesLoader : filesLoadersList) {
            ScaFileInformation scaFileInformation =  filesLoader.getNextFileInformation();
            while(scaFileInformation != null){
                InputStream inputStream  = scaFileInformation.getFileInputSteam();
                Scanner scanner = new Scanner(inputStream);

                while(scanner.hasNext()){
                    String next = scanner.next();
                    for (ScaRule scaRule : scaRules) {
                        ScaRuleInspectionResult scaRuleInspectionResult =  scaRule.inpectLine(next);
                        results.add(scaRuleInspectionResult);
                    }
                }

                scaFileInformation = filesLoader.getNextFileInformation();
            }
        }
        return results;
    }
}
