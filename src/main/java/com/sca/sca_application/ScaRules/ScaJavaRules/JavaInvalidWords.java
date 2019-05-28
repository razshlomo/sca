package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaDefaultRuleInspectionResult;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents.ScaInvalidWordIncident;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JavaInvalidWords implements ScaRule {

    private List<String> invalidWords = Arrays.asList("def","var", "stat" );

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, String lineToInspect) {

        ScaRuleInspectionResult results = new ScaDefaultRuleInspectionResult();

        Trie trie = Trie.builder().onlyWholeWords().addKeywords(invalidWords).build();
        Collection<Emit> emits = trie.parseText(lineToInspect);

        emits.forEach(emit -> {
            ScaInvalidWordIncident scaDefaultIncident = new ScaInvalidWordIncident();
            scaDefaultIncident.setInvalidWord(emit.getKeyword());
            scaDefaultIncident.setColumnNumber(emit.getStart());
        });

        /* Continue here */

        return results;
    }
}
