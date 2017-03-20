package com.highgag.web.controller;

import com.highgag.web.exception.HighgagException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(HighgagException.class)
    public void handleException() {
        log.error(">>>>>>>>>>>>>>>>>>>>>>");
    }
}
