package com.highgag.web.exception;

import com.highgag.web.response.SimpleFieldError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class HighgagException extends RuntimeException {

    private int statusCode;

    private List<SimpleFieldError> fieldErrors = new ArrayList<>();

    private final static Map<Integer, String> STATUS_MESSAGES = new HashMap<>();

    public HighgagException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.name());
    }

    public HighgagException(HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = httpStatus.value();
    }

    public HighgagException setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors.clear();
        this.fieldErrors.addAll(fieldErrors.stream()
                .map(i -> new SimpleFieldError(i.getField(), i.getDefaultMessage()))
                .collect(Collectors.toList()));
        return this;
    }

    public HighgagException setFieldError(String field, String message) {
        this.fieldErrors.clear();
        this.fieldErrors.add(new SimpleFieldError(field, message));
        return this;
    }

}
