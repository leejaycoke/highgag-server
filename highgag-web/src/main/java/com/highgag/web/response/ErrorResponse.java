package com.highgag.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ErrorResponse {

    @NonNull
    private int statusCode;

    @NonNull
    private String message;

    private List<SimpleFieldError> fieldErrors;

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors
                .stream()
                .map(i -> new SimpleFieldError(i.getField(), i.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private class SimpleFieldError {

        private String name;

        private String message;
    }

}
