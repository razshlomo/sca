package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaInspectors.internal.InvalidWordsInspector;
import com.sca.sca_application.ScaRules.ScaRulesResults.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Java invalid words in the code rule
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JavaInvalidWordsRule extends ScaJavaRuleBase {

    @Autowired
    private InvalidWordsInspector invalidWordsInspector;

    private Set<String> invalidWords = new HashSet<>(Arrays.asList("def", "var", "stat"));
    private Logger logger = LoggerFactory.getLogger(JavaInvalidWordsRule.class);

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspectWithComments) {

        String lineToInspect = stripCommentsFromLine(lineToInspectWithComments);

        logger.info("inspecting file {} line {}", scaFileInformation.getFilePath(), lineNumber);

        logger.debug("Before stripping comments:");
        logger.debug(lineToInspectWithComments);
        logger.debug("After stripping comments:");
        logger.debug(lineToInspect);

        return invalidWordsInspector.inspect(scaFileInformation, lineNumber, lineToInspect, invalidWords);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
