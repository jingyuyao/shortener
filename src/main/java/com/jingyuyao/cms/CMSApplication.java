package com.jingyuyao.cms;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CMSApplication extends Application<CMSConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CMSApplication().run(args);
    }

    @Override
    public String getName() {
        return "CMS";
    }

    @Override
    public void initialize(final Bootstrap<CMSConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final CMSConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
