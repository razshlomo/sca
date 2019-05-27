package com.sca.sca_application.Configuration;

import com.sca.sca_application.ScaFileLoader.FilesLoader;
import com.sca.sca_application.ScaReporters.ScaReporter;
import com.sca.sca_application.ScaRules.ScaRulesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScaConfiguration {
    private List<ScaReporter> reportersList = new ArrayList<>();
    private List<ScaRulesLoader> rulesList = new ArrayList<>();

    public List<FilesLoader> getFilesLoadersList() {
        return filesLoadersList;
    }
    public void addFileLoaders(FilesLoader ...filesLoaders){
        filesLoadersList.addAll(filesLoaders == null ? new ArrayList<>(0) : Arrays.asList(filesLoaders));
    }

    private List<FilesLoader> filesLoadersList = new ArrayList<>();

    public List<ScaReporter> getReportersList() {
        return reportersList;
    }

    public void addReporters(ScaReporter ...reporters) {
        this.reportersList.addAll(reporters == null ? new ArrayList<>(0) : Arrays.asList(reporters));
    }

    public List<ScaRulesLoader> getRulesLoaderList() {
        return rulesList;
    }

    public void addRules(ScaRulesLoader ...rules) {
        this.rulesList.addAll(rules == null ? new ArrayList<>(0) : Arrays.asList(rules));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaConfiguration that = (ScaConfiguration) o;
        return Objects.equals(reportersList, that.reportersList) &&
                Objects.equals(rulesList, that.rulesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportersList, rulesList);
    }
}
