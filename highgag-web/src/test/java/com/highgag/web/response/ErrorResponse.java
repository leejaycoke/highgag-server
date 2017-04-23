package com.highgag.web.response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private List<SimpleFieldError> errors;
}