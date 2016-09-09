package com.jingyuyao.shortner;

import com.google.inject.AbstractModule;

/** Module used for {@link ShortnerApplication#run} */
class RunModule extends AbstractModule {
    private final ShortnerConfiguration configuration;

    RunModule(ShortnerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(ShortnerConfiguration.class).toInstance(configuration);
    }
}
