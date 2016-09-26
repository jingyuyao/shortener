package com.jingyuyao.shortener.db;

import com.google.inject.AbstractModule;

public class DAOModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LinkDAO.class);
    }
}
