package com.jingyuyao.cms;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Provides;
import com.jingyuyao.cms.core.Greeting;
import com.jingyuyao.cms.db.DAOModule;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.migrations.MigrationsBundle;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/** Module used for {@link CMSApplication#initialize} */
class CMSInitializeModule extends AbstractModule {
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({FIELD, PARAMETER, METHOD})
    private @interface DatabaseEntities {}

    @Override
    protected void configure() {
        install(new DAOModule());
    }

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

    @Provides
    @Singleton
    HibernateBundle<CMSConfiguration> provideHibernateBundle(
            @DatabaseEntities ImmutableList<Class<?>> hibernateClasses,
            SessionFactoryFactory sessionFactoryFactory) {
        return new HibernateBundle<CMSConfiguration>(hibernateClasses, sessionFactoryFactory) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(CMSConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Provides
    SessionFactory provideSessionFactory(HibernateBundle<CMSConfiguration> hibernateBundle) {
        return hibernateBundle.getSessionFactory();
    }

    @Provides
    @Singleton
    SessionFactoryFactory provideSessionFactoryFactory() {
        return new SessionFactoryFactory();
    }

    @DatabaseEntities
    @Provides
    @Singleton
    ImmutableList<Class<?>> provideDatabaseEntities() {
        return ImmutableList.of(Greeting.class);
    }
}
