package com.highgag.web.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Token {

    private String value;

    private LocalDateTime expiresAt;

}
