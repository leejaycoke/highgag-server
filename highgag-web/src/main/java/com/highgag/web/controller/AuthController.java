package com.highgag.web.controller;

import com.highgag.web.auth.ScryptService;
import com.highgag.web.auth.Token;
import com.highgag.web.form.UserSigninForm;
import com.highgag.web.form.UserSignupForm;
import com.highgag.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScryptService scryptService;

    @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
    public Token post(@Valid @RequestBody UserSignupForm form) throws IOException {
        Token token = userService.signup(form);
        return token;
    }

    @RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
    public Token post(@Valid @RequestBody UserSigninForm form) throws IOException {
        Token token = userService.signin(form);
        return token;
    }

}
