package com.jingyuyao.cms;

import com.google.inject.AbstractModule;
import com.jingyuyao.cms.core.GreetingModule;

/**
 * Root module used by {@link CMSApplication}
 */
class CMSModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GreetingModule());
    }
}
