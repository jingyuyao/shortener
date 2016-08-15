package com.jingyuyao.cms.db;

import com.google.inject.AbstractModule;

public class DAOModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GreetingDAO.class);
    }
}
