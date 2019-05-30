package com.sca.sca_application.ScaRules;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaRules.ScaRulesResults.ScaRuleInspectionResult;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

/**
 * describe SCA rule that inspect lines from file
 */
public interface ScaRule {

    default String CommentBlockStart(){return "/*";}
    default String CommentBlockEnd(){return "*/";}
    default String CommentLine(){ return "//";}

    /**
     * Determine if the inspected file is relevant
     * @param scaFileInformation file information object
     * @return true if the file should be inspected.
     */
    boolean isFileRelevant(ScaFileInformation scaFileInformation);

    /**
     * Inspect the given line and return results with it's incidents.
     * @param scaFileInformation inspected file information
     * @param lineNumber line number that is being inspected
     * @param lineToInspect content of the line to inspect
     * @return rule inspect result object with the state and the incident of the result.
     */
    ScaRuleInspectionResult inspectLine(ScaFileInformation scaFileInformation, int lineNumber, String lineToInspect);

    /**
     * @return Returns the class logger.
     */
    Logger getLogger();

    /**
     * Set the value if 'duringCommentBlock'
     * @param duringCommentBlock value to set
     */
    void setDuringCommentBlock(boolean duringCommentBlock);

    /**
     * Determine if the file is during comment block.
     * @return true if the file is currently in comment-block.
     */
    boolean isDuringCommentBlock();

    /**
     * Stripping the line from comments
     * @param lineToInspectWithComments line to remove the comments from
     * @return the line without the comments
     */
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
            int indexOfCommentBlockEnd = result.indexOf(CommentBlockEnd());
            if(indexOfCommentBlockEnd == -1){
                return false;
            }else{
                result.delete(0,indexOfCommentBlockEnd + CommentBlockEnd().length());
                setDuringCommentBlock(false);
            }
        }
        return true;
    }

    /**
     * Deletes the comment-line if exist in the string.
     * @param result StringBuilder of the line
     */
    default void deleteCommentLine(StringBuilder result) {
        int indexOfCommentInLine = result.indexOf(CommentLine());
        if(indexOfCommentInLine != -1){
            getLogger().info("Line comment found from index {}",indexOfCommentInLine);
            result.delete(indexOfCommentInLine,result.length());
        }
    }

    /**
     * Delete complete comment-block.
     * If block started without ending, set the rule status and deletes and rest of the line
     * @param result the String builder of the line without comment-block.
     */
    default void deleteCommentBlocks(StringBuilder result) {
        int indexOfCommentBlockStart = result.indexOf(CommentBlockStart());
        while (indexOfCommentBlockStart != -1){
            int indexOfCommentBlockEnd = result.indexOf(CommentBlockEnd(),indexOfCommentBlockStart);
            if(indexOfCommentBlockEnd == -1){
                setDuringCommentBlock(true);
                result.delete(indexOfCommentBlockStart,result.length());
            }else{
                getLogger().info("Comment block found from index {} to index {}",indexOfCommentBlockStart,indexOfCommentBlockEnd);
                result.replace(indexOfCommentBlockStart,indexOfCommentBlockEnd + CommentBlockEnd().length()," ");

            }

            indexOfCommentBlockStart = result.indexOf(CommentBlockStart());
        }
    }
}
