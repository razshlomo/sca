package com.sca.sca_application.ScaRules.ScaRuleLoaders.internal;

import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesLoader;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BasicJavaRulesLoader implements ScaRulesLoader {

    @Override
    public Collection<? extends ScaRule> getRules() {
        return null;
    }

}
