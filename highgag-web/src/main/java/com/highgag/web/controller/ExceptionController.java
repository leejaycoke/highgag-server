package com.highgag.web.controller;

import com.highgag.web.exception.HighgagException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(HighgagException.class)
    public void handleException(HighgagException e, HttpServletResponse response) throws IOException {
        response.sendError(e.getStatusCode());
    }
}
