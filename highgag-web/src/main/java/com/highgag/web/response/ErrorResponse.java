package com.highgag.web.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class ErrorResponse {

    @NonNull
    private int statusCode;

    @NonNull
    private String message;

    private List<SimpleFieldError> fieldErrors;

}
