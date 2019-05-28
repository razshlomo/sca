package com.sca.sca_application.Configuration;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ScaDefaultConfigurationElementBase implements ScaConfigurationElement {

    private List<String> parameters;
    private String id;

    public ScaDefaultConfigurationElementBase(String id, String ...params){
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("id cannot be empty");
        }
        this.id = id;
        if(params == null || params.length ==0){
            this.parameters = new ArrayList<>(0);
        }
        else{
            this.parameters = Arrays.asList(params);
        }
    }

    @Override
    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaDefaultConfigurationElementBase that = (ScaDefaultConfigurationElementBase) o;
        return Objects.equals(parameters, that.parameters) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters, id);
    }
}
