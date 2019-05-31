package com.sca.sca_application.ScaRules.ScaJsRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderConstants;
import com.sca.sca_application.ScaInspectors.internal.InvalidWordsInspector;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesDecorators.DuringCommentBlockDecorator;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * JS rule for invalid words in code files.
 * Get matches also in string values (inside " ")
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JsInvalidWordsRule implements ScaRule {

    private Logger logger = LoggerFactory.getLogger(JsInvalidWordsRule.class);
    private Set<String> invalidWords = new HashSet<>(Arrays.asList("static","ecstatic","fantastic"));
    @Autowired
    private DuringCommentBlockDecorator duringCommentBlockDecorator;
    @Autowired
    private InvalidWordsInspector invalidWordsInspector;


    @Override
    public boolean isFileRelevant(ScaFileInformation scaFileInformation) {
        String fileExtension = scaFileInformation.getFileExtension();
        return ScaFileLoaderConstants.JS.equals(fileExtension);
    }

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspectWithComments) {
        String lineToInspect = stripCommentsFromLine(lineToInspectWithComments);
        return invalidWordsInspector.inspect(scaFileInformation,lineNumber,lineToInspect,invalidWords);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setDuringCommentBlock(boolean duringCommentBlock) {
        duringCommentBlockDecorator.setDuringCommentBlock(duringCommentBlock);
    }

    @Override
    public boolean isDuringCommentBlock() {
        return duringCommentBlockDecorator.isDuringCommentBlock();
    }
}
