package com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents;

import com.sca.sca_application.ScaRules.ScaIncident;

import java.util.Objects;

public class ScaDefaultIncident implements ScaIncident {
    private int lineNumber;
    private int columnNumber;

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public int getColumnNumber() {
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaDefaultIncident that = (ScaDefaultIncident) o;
        return lineNumber == that.lineNumber &&
                columnNumber == that.columnNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, columnNumber);
    }
}
