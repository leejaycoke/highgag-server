package com.highgag.web.exception;

import com.highgag.web.response.SimpleFieldError;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.*;

@Data
public class HighgagException extends RuntimeException {

    private int statusCode;

    private List<SimpleFieldError> errors = new ArrayList<>();

    private final static Map<Integer, String> STATUS_MESSAGES = new HashMap<>();

    public HighgagException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.name());
    }

    public HighgagException(HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = httpStatus.value();
    }

    public HighgagException setErrors(List<SimpleFieldError> errors) {
        this.errors = errors;
        return this;
    }

    public HighgagException setError(String field, String message) {
        errors = Collections.singletonList(new SimpleFieldError(field, message));
        return this;
    }

    public HighgagException addError(String field, String message) {
        errors.add(new SimpleFieldError(field, message));
        return this;
    }

}
