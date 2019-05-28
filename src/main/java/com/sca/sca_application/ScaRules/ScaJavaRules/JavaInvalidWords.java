package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleInspectionResult;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JavaInvalidWords implements ScaRule {

    private List<String> invalidWords = Arrays.asList("def","var", "stat" );

    @Override
    public ScaRuleInspectionResult inspectLine(String next) {

        ScaRuleInspectionResult results = new ArrayList<>();

        Trie trie = Trie.builder().onlyWholeWords().addKeywords(invalidWords).build();
        Collection<Emit> emits = trie.parseText(next);

        emits.forEach(emit -> {

        });
/*Continue here!!!!*/
    }
}
