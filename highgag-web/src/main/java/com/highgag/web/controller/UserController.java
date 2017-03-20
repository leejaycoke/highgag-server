package com.highgag.web.controller;

import com.highgag.web.auth.Token;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.service.UserService;
import com.highgag.web.user.UserSignupForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Token post(@Valid @RequestBody UserSignupForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setFieldErrors(bindingResult.getFieldErrors());
        }

        Token token = userService.signup(form);
        return token;
    }

}
