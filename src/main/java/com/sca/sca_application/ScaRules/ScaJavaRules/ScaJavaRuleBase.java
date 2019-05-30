package com.sca.sca_application.ScaRules.ScaJavaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFileLoaderUtils;
import com.sca.sca_application.ScaRules.ScaRule;
import com.sca.sca_application.ScaRules.ScaRulesDecorators.DuringCommentBlockDecorator;

public abstract class ScaJavaRuleBase  implements ScaRule {

    private DuringCommentBlockDecorator duringCommentBlockDecorator = new DuringCommentBlockDecorator();

    @Override
    public void setDuringCommentBlock(boolean duringCommentBlock) {
        duringCommentBlockDecorator.setDuringCommentBlock(duringCommentBlock);
    }

    @Override
    public boolean isDuringCommentBlock() {
        return duringCommentBlockDecorator.isDuringCommentBlock();
    }

    @Override
    public boolean isFileRelevant(ScaFileInformation scaFileInformation) {
        String fileExtension = scaFileInformation.getFileExtension();
        return ScaFileLoaderUtils.JAVA.equals(fileExtension);
    }

}
