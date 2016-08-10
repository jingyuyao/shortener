package com.jingyuyao.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jingyuyao.cms.resources.GreetingResource;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CMSApplication extends Application<CMSConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CMSApplication().run(args);
    }

    private Injector injector;

    @Override
    public String getName() {
        return "CMS";
    }

    @Override
    public void initialize(final Bootstrap<CMSConfiguration> bootstrap) {}

    @Override
    public void run(final CMSConfiguration configuration,
                    final Environment environment) {
        injector = Guice.createInjector(new CMSModule(configuration));

        setUpJersey(environment.jersey());
    }

    /** Sets up the Jersey environment with resources */
    private void setUpJersey(JerseyEnvironment jerseyEnvironment) {
        jerseyEnvironment.register(i(GreetingResource.class));
    }

    /** Helper method to use the {@link Injector} */
    private Object i(Class cls) {
        return injector.getInstance(cls);
    }
}
