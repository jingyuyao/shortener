package com.jingyuyao.shortener;

import com.google.inject.AbstractModule;

/** Module used for {@link ShortenerApplication#run} */
class RunModule extends AbstractModule {
    private final ShortenerConfiguration configuration;

    RunModule(ShortenerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(ShortenerConfiguration.class).toInstance(configuration);
    }
}
