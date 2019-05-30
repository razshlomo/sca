package com.sca.sca_application.ScaInspectors.internal;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaInspectors.ScaInspector;
import com.sca.sca_application.ScaRules.ScaRuleResultState;
import com.sca.sca_application.ScaRules.ScaRulesResults.*;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * ScaInspector. Inspect line for invalid words.
 */
@Component
public class InvalidWordsInspector implements ScaInspector {


    @Override
    public ScaRuleInspectionResult inspect(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect, Object... params) {

        if(params == null || params.length < 1){
            throw new RuntimeException("InvalidWordsInspector excpet to get a collection of invalid words");
        }

        //noinspection unchecked
        Collection<String> invalidWords = (Collection<String>) params[0];

        ScaFileInformationResult scaFileInformationResult = ScaRulesResultUtils.fromScaFileInformation(scaFileInformation);

        ScaRuleInspectionResult result = new ScaDefaultErrorRuleInspectionResult("Invalid words exist", ScaRuleResultState.FAILED,scaFileInformationResult);
        result.setScaFileInformationResult(scaFileInformationResult);

        Trie trie = Trie.builder().onlyWholeWords().addKeywords(invalidWords).build();
        Collection<Emit> emits = trie.parseText(lineToInspect);

        if(CollectionUtils.isEmpty(emits)){
            ScaSuccessRuleIncident scaSuccessRuleIncident = new ScaSuccessRuleIncident();
            scaSuccessRuleIncident.setLineNumber(lineNumber);
            scaSuccessRuleIncident.setScaFileInformationResult(scaFileInformationResult);
            return scaSuccessRuleIncident;
        }

        emits.forEach(emit -> {
            ScaInvalidWordIncident scaDefaultIncident = new ScaInvalidWordIncident();
            scaDefaultIncident.setInvalidWord(emit.getKeyword());
            scaDefaultIncident.setLineNumber(lineNumber);
            result.addScaIncident(scaDefaultIncident);
        });

        return result;
    }

}
