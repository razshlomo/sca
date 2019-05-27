package com.sca.sca_application.ScaRules;

import java.util.Collection;

public interface ScaRulesLoader {
    Collection<? extends ScaRule> getRules();
}
