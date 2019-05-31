package com.sca.sca_application;

import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRuleLoaders.internal.ApplicationContextRulesLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScaRulesLoaderTests {

    @Autowired
    private ApplicationContextRulesLoader basicRulesLoader;

    @Test
    public void VerifyAllRulesExistsTest(){
        List<String> rulesIds = Arrays.asList("cSharpLongLineRule", "jsInvalidWordsRule");

        Collection<? extends ScaRule> rules = basicRulesLoader.getRules(rulesIds);
        Assert.assertNotNull(rules);
        Assert.assertEquals(rulesIds.size(),rules.size());
    }
}
