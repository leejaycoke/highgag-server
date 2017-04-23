package com.highgag.web.controller;

import com.highgag.core.domain.Role;
import com.highgag.web.auth.Authorization;
import com.highgag.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Authorization(roles = Role.ALL)
    @RequestMapping("/users/me")
    public void getMe() {

    }
}
