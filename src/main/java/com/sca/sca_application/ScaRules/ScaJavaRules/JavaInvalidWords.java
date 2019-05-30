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


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JavaInvalidWords extends ScaJavaRuleBase {

    @Autowired
    private InvalidWordsInspector invalidWordsInspector;

    private Set<String> invalidWords = new HashSet<>(Arrays.asList("def","var", "stat" ));
    private Logger logger = LoggerFactory.getLogger(JavaInvalidWords.class);

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspectWithComments) {

        String lineToInspect = stripCommentsFromLine(lineToInspectWithComments);

        logger.info("inspecting file {} line {}",scaFileInformation.getFilePath(),lineNumber);
        logger.info("Before stripping comments:");
        logger.info(lineToInspectWithComments);
        logger.info("After stripping comments:");
        logger.info(lineToInspect);

        return invalidWordsInspector.inspect(scaFileInformation, lineNumber, lineToInspect,invalidWords);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
