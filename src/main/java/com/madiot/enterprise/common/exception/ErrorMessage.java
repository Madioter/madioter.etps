package com.madiot.enterprise.common.exception;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by yi.wang1 on 2014/12/17.
 */
public class ErrorMessage<T> {
    private List<String> errors = new ArrayList<String>();

    public void validate(T model) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(model);
        for (ConstraintViolation<T> item : constraintViolations) {
            errors.add(item.getMessage());
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String errorMessage) {
        errors.add(errorMessage);
    }

    public void addAllError(List<String> errorMessages) {
        this.errors.addAll(errorMessages);
    }

    public boolean haveError() {
        if (errors.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String item : errors) {
            if (!item.trim().endsWith(";")) {
                builder.append(item).append(";");
            }else{
            	builder.append(item);
            }
        }
        return builder.toString();
    }
    
    public String toHTMLString() {
        StringBuilder builder = new StringBuilder();
        for (String item : errors) {
            if (!item.trim().endsWith(";")) {
                builder.append(item).append("<br/>");
            }else{
            	builder.append(item);
            }
        }
        return builder.toString();
    }
}
