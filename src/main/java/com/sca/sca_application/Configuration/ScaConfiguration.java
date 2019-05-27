package com.sca.sca_application.Configuration;

import com.sca.sca_application.ScaFileLoader.FilesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScaConfiguration {

    private List<String> reportersList = new ArrayList<>();
    private List<String> rulesLoadersList = new ArrayList<>();

    public List<FilesLoader> getFilesLoadersList() {
        return filesLoadersList;
    }
    public void addFileLoaders(FilesLoader ...filesLoaders){
        filesLoadersList.addAll(filesLoaders == null ? new ArrayList<>(0) : Arrays.asList(filesLoaders));
    }

    private List<FilesLoader> filesLoadersList = new ArrayList<>();

    public List<String> getReportersList() {
        return reportersList;
    }

    public void addReporters(String ...reporters) {
        this.reportersList.addAll(reporters == null ? new ArrayList<>(0) : Arrays.asList(reporters));
    }

    public List<String> getRulesLoaderList() {
        return rulesLoadersList;
    }

    public void addRulesLoaders(String ...rules) {
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
