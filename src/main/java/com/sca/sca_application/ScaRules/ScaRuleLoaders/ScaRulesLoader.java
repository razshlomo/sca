package com.sca.sca_application.ScaRules.ScaRuleLoaders;

import com.sca.sca_application.ScaRules.ScaRule;

import java.util.Collection;
import java.util.List;

/**
 * Interface for loading rules. Rules will be used for the inspection.
 */
public interface ScaRulesLoader {
    Collection<? extends ScaRule> getRules(List<String> params);
}
