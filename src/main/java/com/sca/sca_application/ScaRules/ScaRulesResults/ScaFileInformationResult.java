package com.sca.sca_application.ScaRules.ScaRulesResults;

import java.util.Objects;

public class ScaFileInformationResult {
    private String filePath;

    public ScaFileInformationResult(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaFileInformationResult that = (ScaFileInformationResult) o;
        return Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }
}