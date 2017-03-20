package com.highgag.web.controller;

import com.highgag.web.exception.HighgagException;
import com.highgag.web.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    private final static int DEFAULT_STATUS_CODE = 500;

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResponse handle415Exception(HttpMediaTypeNotSupportedException e, HttpServletResponse response) {
        log.error(">>>>> unsupportedException e={}", e.toString());

        ErrorResponse error = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.name());
        response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e, HttpServletResponse response) {
        log.error(">>>>> handleException e={}", e.toString());

        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }


    @ExceptionHandler(HighgagException.class)
    public ErrorResponse handleHighgagException(HighgagException e, HttpServletResponse response) {
        log.error(">>>>> handleHighgagException e={}", e.toString());

        ErrorResponse error = new ErrorResponse(e.getStatusCode(), e.getMessage());
        if (e.getFieldErrors() != null && e.getFieldErrors().size() > 0) {
            error.setFieldErrors(e.getFieldErrors());
        }

        response.setStatus(e.getStatusCode());
        return error;
    }

}
