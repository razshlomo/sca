package com.sca.sca_application.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScaConfiguration {

    private List<ScaReporterConfiguration> reportersList = new ArrayList<>();
    private List<ScaRulesLoaderConfiguration> rulesLoadersList = new ArrayList<>();
    private List<ScaFilesLoaderConfiguration> filesLoadersList = new ArrayList<>();

    public List<ScaFilesLoaderConfiguration> getFilesLoadersList() {
        return filesLoadersList;
    }
    public void addFileLoaders(ScaFilesLoaderConfiguration ...filesLoaders){
        filesLoadersList.addAll(filesLoaders == null ? new ArrayList<>(0) : Arrays.asList(filesLoaders));
    }

    public List<ScaReporterConfiguration> getReportersList() {
        return reportersList;
    }

    public void addReporters(ScaReporterConfiguration ...reporters) {
        this.reportersList.addAll(reporters == null ? new ArrayList<>(0) : Arrays.asList(reporters));
    }

    public List<ScaRulesLoaderConfiguration> getRulesLoaderList() {
        return rulesLoadersList;
    }

    public void addRulesLoaders(ScaRulesLoaderConfiguration ...rules) {
        this.rulesLoadersList.addAll(rules == null ? new ArrayList<>(0) : Arrays.asList(rules));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaConfiguration that = (ScaConfiguration) o;
        return Objects.equals(reportersList, that.reportersList) &&
                Objects.equals(rulesLoadersList, that.rulesLoadersList) &&
                Objects.equals(filesLoadersList, that.filesLoadersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportersList, rulesLoadersList, filesLoadersList);
    }
}
