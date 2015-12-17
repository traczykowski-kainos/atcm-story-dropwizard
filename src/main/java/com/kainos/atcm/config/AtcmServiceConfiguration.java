package com.kainos.atcm.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AtcmServiceConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private final ApplicationConfig applicationConfig = new ApplicationConfig();

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }
}
