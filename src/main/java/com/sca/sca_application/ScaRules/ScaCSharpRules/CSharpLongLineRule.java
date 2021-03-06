package com.sca.sca_application.ScaRules.ScaCSharpRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderConstants;
import com.sca.sca_application.ScaInspectors.internal.LineLengthInspector;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesDecorators.DuringCommentBlockDecorator;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * C# files should not contain lines that are longer than 20 chars.
 */
@Component("cSharpLongLineRule")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CSharpLongLineRule implements ScaRule {

    private Logger logger = LoggerFactory.getLogger(CSharpLongLineRule.class);

    @Autowired
    private DuringCommentBlockDecorator duringCommentBlockDecorator;
    @Autowired
    private LineLengthInspector lineLengthInspector;

    @Override
    public boolean isFileRelevant(ScaFileInformation scaFileInformation) {
        String fileExtension = scaFileInformation.getFileExtension();
        return ScaFileLoaderConstants.C_SHARP.equals(fileExtension);
    }

    @Override
    public ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect) {
        return lineLengthInspector.inspect(scaFileInformation,lineNumber,lineToInspect,20);
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
