package com.highgag.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleFieldError {

    private String name;

    private String message;
}