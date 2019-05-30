package com.sca.sca_application.ScaRules.ScaRuleLoaders.internal;

import com.sca.sca_application.ScaApplication;
import com.sca.sca_application.ScaRules.ScaCSharpRules.CSharpLongLineRule;
import com.sca.sca_application.ScaRules.ScaJavaRules.JavaInvalidWordsRule;
import com.sca.sca_application.ScaRules.ScaJsRules.JsInvalidWordsRule;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleLoaders.ScaRulesLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Basic rules loader. Currently returns list of the basic rules but can returns rules from dynamic loaded JARs or Groovy scripts.
 */
@Component
public class BasicRulesLoader implements ScaRulesLoader {

    @Override
    public Collection<? extends ScaRule> getRules() {
        return beansByClass(JavaInvalidWordsRule.class, JsInvalidWordsRule.class, CSharpLongLineRule.class);
    }

    @SafeVarargs
    private final <T> Collection<T> beansByClass(Class<? extends T>... clazz) {

        Collection<T> result = new ArrayList<>(clazz.length);
        ApplicationContext applicationContext = ScaApplication.getApplicationContext();

        for (Class<? extends T> aClass : clazz) {
            result.add(applicationContext.getBean(aClass));
        }

        return result;
    }

}
