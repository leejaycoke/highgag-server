package com.highgag.web.controller;

import com.highgag.web.exception.HighgagException;
import com.highgag.web.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleException(HighgagException e, HttpServletResponse response) {
        response.setStatus(e.getStatusCode());
        ErrorResponse error = new ErrorResponse(e.getStatusCode(), e.getMessage());
        if (e.getFieldErrors() != null) {
            error.setFieldErrors(e.getFieldErrors());
        }
        return error;
    }

}
