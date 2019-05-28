package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaDefaultErrorRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaSuccessRuleIncident;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class JavaInvalidWords implements ScaRule {

    private List<String> invalidWords = Arrays.asList("def","var", "stat" );

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, String lineToInspect) {

        ScaRuleInspectionResult results = new ScaDefaultErrorRuleInspectionResult("Invalid words are exists", ScaRuleResultState.FAILED,scaFileInformation);
        results.setScaFileInformation(scaFileInformation);

        Trie trie = Trie.builder().onlyWholeWords().addKeywords(invalidWords).build();
        Collection<Emit> emits = trie.parseText(lineToInspect);

        if(CollectionUtils.isEmpty(emits)){
            return new ScaSuccessRuleIncident();
        }

        emits.forEach(emit -> {
            ScaInvalidWordIncident scaDefaultIncident = new ScaInvalidWordIncident();
            scaDefaultIncident.setInvalidWord(emit.getKeyword());
            scaDefaultIncident.setColumnNumber(emit.getStart());
            results.addScaIncident(scaDefaultIncident);
        });

        return results;
    }
}
