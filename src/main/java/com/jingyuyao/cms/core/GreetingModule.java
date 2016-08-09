package com.jingyuyao.cms.core;

import com.google.inject.AbstractModule;

public class GreetingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GreetingService.class);
    }
}
