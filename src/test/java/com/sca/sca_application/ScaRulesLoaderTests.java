package com.sca.sca_application;

import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleLoaders.internal.ApplicationContextRulesLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaRulesLoaderTests {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ApplicationContextRulesLoader basicRulesLoader;

    @Test
    public void VerifyAllRulesExistsTest(){
        Collection<? extends ScaRule> rules = basicRulesLoader.getRules(null);

    }
}
