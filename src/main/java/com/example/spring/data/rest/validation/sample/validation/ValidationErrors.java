package com.example.spring.data.rest.validation.sample.validation;

import org.springframework.validation.BeanPropertyBindingResult;

public class ValidationErrors extends BeanPropertyBindingResult {

    public ValidationErrors(Object target) {
        super(target, target.getClass().getSimpleName());
    }

    @Override
    public void rejectValue(String field, String errorCode, String defaultMessage) {
        super.rejectValue(field, errorCode, new Object[]{getFieldValue(field)}, defaultMessage);
    }

    @Override
    public void rejectValue(String field, String errorCode) {
        rejectValue(field, errorCode, "invalid value");
    }

}
