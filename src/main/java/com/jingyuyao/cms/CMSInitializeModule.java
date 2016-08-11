package com.jingyuyao.cms;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

import javax.inject.Singleton;

/** Module used for {@link CMSApplication#initialize} */
class CMSInitializeModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    @Singleton
    MigrationsBundle<CMSConfiguration> provideMigrationsBundle() {
        return new MigrationsBundle<CMSConfiguration>() {
            @Override
            public String getMigrationsFileName() {
                return "migrations.yml";
            }

            @Override
            public DataSourceFactory getDataSourceFactory(CMSConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }
}
