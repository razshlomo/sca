package com.sca.sca_application.ScaRules.ScaRuleLoaders;

import com.sca.sca_application.ScaRules.ScaRule;

import java.util.Collection;

/**
 * Interface for loading rules. Rules will be used for the inspection.
 */
public interface ScaRulesLoader {
    Collection<? extends ScaRule> getRules();
}
