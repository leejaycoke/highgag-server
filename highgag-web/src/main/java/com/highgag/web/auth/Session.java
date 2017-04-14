package com.highgag.web.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.highgag.core.domain.Role;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Session {

    @NonNull
    private Long id;

    @NonNull
    private String account;

    @NonNull
    private Role role;

    private LocalDateTime expiresAt;

    @JsonIgnore
    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
