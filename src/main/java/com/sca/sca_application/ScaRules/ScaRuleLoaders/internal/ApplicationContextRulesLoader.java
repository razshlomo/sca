package com.sca.sca_application.ScaRules.ScaRuleLoaders.internal;

import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleLoaders.ScaRulesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Basic rules loader. Currently returns list of the basic rules but can returns rules from dynamic loaded JARs or Groovy scripts.
 */
@Component
public class ApplicationContextRulesLoader implements ScaRulesLoader {

    @Autowired
    private ApplicationContext context;

    @Override
    public Collection<? extends ScaRule> getRules(Collection<String> params) {

        if (params == null || params.size() == 0) {
            return new ArrayList<>(0);
        }

        ArrayList<ScaRule> scaRules = new ArrayList<>(params.size());
        for (String param : params) {
            ScaRule bean = context.getBean(param, ScaRule.class);
            scaRules.add(bean);
        }
        return scaRules;
    }
}
