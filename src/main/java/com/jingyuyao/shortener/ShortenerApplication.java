package com.jingyuyao.shortener;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.jingyuyao.shortener.resources.LinkResource;
import com.jingyuyao.shortener.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class ShortenerApplication extends Application<ShortenerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ShortenerApplication().run(args);
    }

    private Injector injector;

    @Override
    public String getName() {
        return "CMS";
    }

    @Override
    public void initialize(final Bootstrap<ShortenerConfiguration> bootstrap) {
        injector = Guice.createInjector(new InitializeModule());

        bootstrap.addBundle(i(AssetsBundle.class));
        bootstrap.addBundle(i(new Key<ViewBundle<ShortenerConfiguration>>(){}));
        bootstrap.addBundle(i(new Key<MigrationsBundle<ShortenerConfiguration>>(){}));
        bootstrap.addBundle(i(new Key<HibernateBundle<ShortenerConfiguration>>(){}));
    }

    @Override
    public void run(final ShortenerConfiguration configuration,
                    final Environment environment) {
        injector = injector.createChildInjector(new RunModule(configuration));

        setUpJersey(environment.jersey());
    }

    /** Sets up the Jersey environment with resources */
    private void setUpJersey(JerseyEnvironment jersey) {
        jersey.register(i(LinkResource.class));
        jersey.register(i(UserResource.class));
    }

    /** Helper method to get an instance out of the {@link Injector} */
    private <T> T i(Class<T> cls) {
        return injector.getInstance(cls);
    }

    /** Helper method to get an instance out of the {@link Injector} */
    private <T> T i(Key<T> key) {
        return injector.getInstance(key);
    }
}
