package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderUtils;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.*;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JavaInvalidWords implements ScaRule {

    private List<String> invalidWords = Arrays.asList("def","var", "stat" );

    @Override
    public boolean isFileRelevant(ScaFileInformation scaFileInformation) {

        String fileExtension = scaFileInformation.getFileExtension();
        return ScaFileLoaderUtils.JAVA.equals(fileExtension);
    }

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect) {

        ScaFileInformationResult scaFileInformationResult = ScaRulesResultUtils.fromScaFileInformation(scaFileInformation);

        ScaRuleInspectionResult results = new ScaDefaultErrorRuleInspectionResult("Invalid words exist", ScaRuleResultState.FAILED,scaFileInformationResult);
        results.setScaFileInformation(scaFileInformationResult);

        Trie trie = Trie.builder().onlyWholeWords().addKeywords(invalidWords).build();
        Collection<Emit> emits = trie.parseText(lineToInspect);

        if(CollectionUtils.isEmpty(emits)){
            ScaSuccessRuleIncident scaSuccessRuleIncident = new ScaSuccessRuleIncident();
            scaSuccessRuleIncident.setLineNumber(lineNumber);
            scaSuccessRuleIncident.setScaFileInformation(scaFileInformationResult);
            return scaSuccessRuleIncident;
        }

        emits.forEach(emit -> {
            ScaInvalidWordIncident scaDefaultIncident = new ScaInvalidWordIncident();
            scaDefaultIncident.setInvalidWord(emit.getKeyword());
            scaDefaultIncident.setColumnNumber(emit.getStart());
            scaDefaultIncident.setLineNumber(lineNumber);
            results.addScaIncident(scaDefaultIncident);
        });

        return results;
    }
}
