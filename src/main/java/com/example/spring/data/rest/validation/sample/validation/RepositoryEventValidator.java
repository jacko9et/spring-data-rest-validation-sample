package com.example.spring.data.rest.validation.sample.validation;

import com.example.spring.data.rest.validation.sample.validation.groups.Persist;
import com.example.spring.data.rest.validation.sample.validation.groups.Update;
import jakarta.validation.groups.Default;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.data.rest.core.event.BeforeSaveEvent;
import org.springframework.data.rest.core.event.RepositoryEvent;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

public interface RepositoryEventValidator extends SmartValidator {

    default void onEvent(RepositoryEvent event, Errors errors) {
        if (event instanceof BeforeSaveEvent) {
            validate(event.getSource(), errors, Default.class, Update.class);
        } else if (event instanceof BeforeCreateEvent) {
            validate(event.getSource(), errors, Default.class, Persist.class);
        }
    }

    default boolean failFast() {
        return true;
    }

    @Override
    default void validate(Object target, Errors errors, Object... validationHints) {
        validate(target, errors);
    }

}
