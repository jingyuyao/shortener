package com.jingyuyao.shortener;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Provides;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.db.DAOModule;
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

/** Module used for {@link ShortenerApplication#initialize} */
class InitializeModule extends AbstractModule {
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
    MigrationsBundle<ShortenerConfiguration> provideMigrationsBundle() {
        return new MigrationsBundle<ShortenerConfiguration>() {
            @Override
            public String getMigrationsFileName() {
                return "migrations.yml";
            }

            @Override
            public DataSourceFactory getDataSourceFactory(ShortenerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Provides
    @Singleton
    HibernateBundle<ShortenerConfiguration> provideHibernateBundle(
            @DatabaseEntities ImmutableList<Class<?>> hibernateClasses,
            SessionFactoryFactory sessionFactoryFactory) {
        return new HibernateBundle<ShortenerConfiguration>(hibernateClasses, sessionFactoryFactory) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(ShortenerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Provides
    SessionFactory provideSessionFactory(HibernateBundle<ShortenerConfiguration> hibernateBundle) {
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
        return ImmutableList.of(Link.class);
    }
}
