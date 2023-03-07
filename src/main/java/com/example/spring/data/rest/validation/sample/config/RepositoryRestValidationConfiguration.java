package com.example.spring.data.rest.validation.sample.config;

import com.example.spring.data.rest.validation.sample.validation.DefaultRepositoryEventValidator;
import com.example.spring.data.rest.validation.sample.validation.DefaultValidatingRepositoryEventListener;
import com.example.spring.data.rest.validation.sample.validation.RepositoryEventValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Configuration
public class RepositoryRestValidationConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.addBasenames("i18n/messages");
        return messageSource;
    }

    @Bean
    public DefaultValidatingRepositoryEventListener defaultValidatingRepositoryEventListener(
            Collection<RepositoryEventValidator> validators) {
        return new DefaultValidatingRepositoryEventListener(validators);
    }

    @Bean
    public DefaultRepositoryEventValidator defaultRepositoryEventValidator(
            LocalValidatorFactoryBean defaultValidator) {
        return new DefaultRepositoryEventValidator(defaultValidator);
    }

}

