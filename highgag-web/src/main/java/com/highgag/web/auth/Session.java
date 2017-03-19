package com.highgag.web.auth;

import com.highgag.core.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {

    private Long id;

    private String account;

    private Role role;

}
