package com.example.spring.data.rest.validation.sample.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.support.RepositoryConstraintViolationExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = DefaultValidatingRepositoryEventListener.class)
public class RepositoryRestValidationExceptionHandler {

    private final MessageSourceAccessor messageSourceAccessor;

    public RepositoryRestValidationExceptionHandler(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @ExceptionHandler
    ResponseEntity<RepositoryConstraintViolationExceptionMessage> handleRepositoryConstraintViolationException(
            RepositoryConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(
                new RepositoryConstraintViolationExceptionMessage(exception, messageSourceAccessor));
    }

}
