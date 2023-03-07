package com.example.spring.data.rest.validation.sample.validation;

import org.springframework.context.ApplicationListener;
import org.springframework.core.OrderComparator;
import org.springframework.core.PriorityOrdered;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.core.event.RepositoryEvent;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.List;

public final class DefaultValidatingRepositoryEventListener implements ApplicationListener<RepositoryEvent>, PriorityOrdered {

    private final List<RepositoryEventValidator> validators;

    public DefaultValidatingRepositoryEventListener(
            Collection<RepositoryEventValidator> validators) {
        this.validators = validators.stream().sorted(OrderComparator.INSTANCE).toList();
    }

    @Override
    public void onApplicationEvent(RepositoryEvent event) {
        BindingResult errors = new ValidationErrors(event.getSource());
        validators.parallelStream()
                .filter(validator -> validator.supports(event.getSource().getClass()))
                .forEachOrdered(validator -> {
                    validator.onEvent(event, errors);
                    if (validator.failFast() && errors.hasErrors()) {
                        throw new RepositoryConstraintViolationException(errors);
                    }
                });
        if (errors.hasErrors()) {
            throw new RepositoryConstraintViolationException(errors);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
