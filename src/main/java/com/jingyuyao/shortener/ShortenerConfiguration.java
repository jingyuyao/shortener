package com.jingyuyao.shortener;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jingyuyao.shortener.factory.JedisFactory;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

class ShortenerConfiguration extends Configuration {
    @JsonProperty
    @NotNull
    @Valid
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    @NotNull
    @Valid
    private JedisFactory jedis = new JedisFactory();

    DataSourceFactory getDataSourceFactory() {
        return database;
    }

    JedisFactory getJedisFactory() {
        return jedis;
    }
}
