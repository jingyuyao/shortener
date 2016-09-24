package com.jingyuyao.shortner;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/** Module used for {@link ShortnerApplication#run} */
class RunModule extends AbstractModule {
    private final ShortnerConfiguration configuration;

    RunModule(ShortnerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(ShortnerConfiguration.class).toInstance(configuration);
    }

    @Provides
    Validator provideValidator(ValidatorFactory factory) {
        return factory.getValidator();
    }

    @Provides
    @Singleton
    ValidatorFactory provideValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
