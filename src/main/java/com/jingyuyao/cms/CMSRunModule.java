package com.jingyuyao.cms;

import com.google.inject.AbstractModule;
import com.jingyuyao.cms.core.GreetingModule;

/** Module used for {@link CMSApplication#run} */
class CMSRunModule extends AbstractModule {
    private final CMSConfiguration configuration;

    CMSRunModule(CMSConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(CMSConfiguration.class).toInstance(configuration);

        install(new GreetingModule());
    }
}
