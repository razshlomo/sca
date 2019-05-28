package com.sca.sca_application.ScaRules.ScaRulesResults.ScaIncidents;

import java.util.Objects;

public class ScaInvalidWordIncident extends ScaDefaultIncident {
    private String invalidWord;

    public String getInvalidWord() {
        return invalidWord;
    }

    public void setInvalidWord(String invalidWord) {
        this.invalidWord = invalidWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ScaInvalidWordIncident that = (ScaInvalidWordIncident) o;
        return Objects.equals(invalidWord, that.invalidWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), invalidWord);
    }
}
