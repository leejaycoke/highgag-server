package com.highgag.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class HighgagException extends RuntimeException {

    private int statusCode;

    private List<FieldError> fieldErrors;

    private final static Map<Integer, String> STATUS_MESSAGES = new HashMap<>();

    public HighgagException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.name());
    }

    public HighgagException(HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = httpStatus.value();
    }

    public HighgagException setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }

}
