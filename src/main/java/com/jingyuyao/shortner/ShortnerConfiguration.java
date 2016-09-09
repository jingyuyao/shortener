package com.jingyuyao.shortner;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

class ShortnerConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    @Valid
    private DataSourceFactory database = new DataSourceFactory();

    DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
