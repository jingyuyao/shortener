package com.jingyuyao.shortner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.jingyuyao.shortner.resources.GreetingResource;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ShortnerApplication extends Application<ShortnerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ShortnerApplication().run(args);
    }

    private Injector injector;

    @Override
    public String getName() {
        return "CMS";
    }

    @Override
    public void initialize(final Bootstrap<ShortnerConfiguration> bootstrap) {
        injector = Guice.createInjector(new InitializeModule());

        bootstrap.addBundle(i(new Key<MigrationsBundle<ShortnerConfiguration>>(){}));
        bootstrap.addBundle(i(new Key<HibernateBundle<ShortnerConfiguration>>(){}));
    }

    @Override
    public void run(final ShortnerConfiguration configuration,
                    final Environment environment) {
        injector = injector.createChildInjector(new RunModule(configuration));

        setUpJersey(environment.jersey());
    }

    /** Sets up the Jersey environment with resources */
    private void setUpJersey(JerseyEnvironment jerseyEnvironment) {
        jerseyEnvironment.register(i(GreetingResource.class));
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
