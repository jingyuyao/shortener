package com.jingyuyao.shortener;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/** Module used for {@link ShortenerApplication#run} */
class RunModule extends AbstractModule {
    private final ShortenerConfiguration configuration;

    RunModule(ShortenerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(ShortenerConfiguration.class).toInstance(configuration);
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
