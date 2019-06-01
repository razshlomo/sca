package com.sca.sca_application.ScaRules.ScaRulesDecorators;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Decorator for handling rules that inspect files that are during Comment-Block.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DuringCommentBlockDecorator implements ScaRule {

    private boolean duringCommentBlock;

    @Override
    public boolean isFileRelevant(ScaFileInformation scaFileInformation) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Logger getLogger() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setDuringCommentBlock(boolean duringCommentBlock) {
         this.duringCommentBlock = duringCommentBlock;
    }

    @Override
    public boolean isDuringCommentBlock() {
        return duringCommentBlock;
    }
}
