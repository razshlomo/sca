package com.sca.sca_application.ScaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

public interface ScaRule {

    String CommentBlockStart = "/*";
    String CommentBlockEnd = "*/";
    String CommentLine = "//";

    boolean isFileRelevant(ScaFileInformation scaFileInformation);
    ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect);
    Logger getLogger();

    void setDuringCommentBlock(boolean duringBlock);
    boolean isDuringCommentBlock();

    default String stripCommentsFromLine(String lineToInspectWithComments) {
        if(StringUtils.isEmpty(lineToInspectWithComments)){
            return lineToInspectWithComments;
        }

        StringBuilder result =new StringBuilder(lineToInspectWithComments);


        if (!deleteEndOfCommentBlockIfNeeded(result)){
            //We are during comment block and it's not ended in this line. Returning empty line in order to skip it.
            return "";
        }

        deleteCommentLine(result);

        deleteCommentBlocks(result);

        return result.toString();
    }

    /**
     * If during comment block, looks for it's end and delete the not needed part.
     * @param result StringBuilder of the line to check.
     * @return false if comment block end wasn't found. else, true
     */
    default boolean deleteEndOfCommentBlockIfNeeded(StringBuilder result) {
        if(isDuringCommentBlock()){
            int indexOfCommentBlockEnd = result.indexOf(CommentBlockEnd);
            if(indexOfCommentBlockEnd == -1){
                return false;
            }else{
                result.delete(0,indexOfCommentBlockEnd + CommentBlockEnd.length());
                setDuringCommentBlock(false);
            }
        }
        return true;
    }

    default void deleteCommentLine(StringBuilder result) {
        int indexOfCommentInLine = result.indexOf(CommentLine);
        if(indexOfCommentInLine != -1){
            getLogger().info("Line comment found from index {}",indexOfCommentInLine);
            result.delete(indexOfCommentInLine,result.length());
        }
    }

    default void deleteCommentBlocks(StringBuilder result) {
        int indexOfCommentBlockStart = result.indexOf(CommentBlockStart);
        while (indexOfCommentBlockStart != -1){
            int indexOfCommentBlockEnd = result.indexOf(CommentBlockEnd,indexOfCommentBlockStart);
            if(indexOfCommentBlockEnd == -1){
                setDuringCommentBlock(true);
                result.delete(indexOfCommentBlockStart,result.length());
            }else{
                getLogger().info("Comment block found from index {} to index {}",indexOfCommentBlockStart,indexOfCommentBlockEnd);
                result.replace(indexOfCommentBlockStart,indexOfCommentBlockEnd + CommentBlockEnd.length()," ");

            }

            indexOfCommentBlockStart = result.indexOf(CommentBlockStart);
        }
    }
}
