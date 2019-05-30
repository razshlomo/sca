package com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents;

import com.sca.sca_application.ScaRules.ScaIncident;

import java.util.Objects;

public class ScaDefaultIncident implements ScaIncident {
    private int lineNumber;

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaDefaultIncident that = (ScaDefaultIncident) o;
        return lineNumber == that.lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber);
    }
}
