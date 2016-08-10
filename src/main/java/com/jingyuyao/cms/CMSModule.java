package com.jingyuyao.cms;

import com.google.inject.AbstractModule;
import com.jingyuyao.cms.core.GreetingModule;

/**
 * Root module used by {@link CMSApplication}
 */
class CMSModule extends AbstractModule {
    private final CMSConfiguration configuration;

    CMSModule(CMSConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(CMSConfiguration.class).toInstance(configuration);

        install(new GreetingModule());
    }
}
