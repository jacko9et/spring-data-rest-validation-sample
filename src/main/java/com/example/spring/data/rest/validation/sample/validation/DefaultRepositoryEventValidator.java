package com.example.spring.data.rest.validation.sample.validation;

import com.example.spring.data.rest.validation.sample.validation.groups.Remove;
import org.springframework.core.PriorityOrdered;
import org.springframework.data.rest.core.event.BeforeDeleteEvent;
import org.springframework.data.rest.core.event.RepositoryEvent;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public final class DefaultRepositoryEventValidator implements RepositoryEventValidator, PriorityOrdered {

    private final LocalValidatorFactoryBean defaultValidator;

    public DefaultRepositoryEventValidator(LocalValidatorFactoryBean defaultValidator) {
        this.defaultValidator = defaultValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void onEvent(RepositoryEvent event, Errors errors) {
        RepositoryEventValidator.super.onEvent(event, errors);
        if (event instanceof BeforeDeleteEvent) {
            validate(event.getSource(), errors, Remove.class);
        }
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        defaultValidator.validate(target, errors, validationHints);
    }

    @Override
    public void validate(Object target, Errors errors) {
        defaultValidator.validate(target, errors);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
