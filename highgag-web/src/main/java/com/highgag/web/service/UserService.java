package com.highgag.web.service;

import com.highgag.core.domain.Role;
import com.highgag.core.entity.User;
import com.highgag.core.repository.UserRepository;
import com.highgag.web.auth.Session;
import com.highgag.web.auth.Token;
import com.highgag.web.auth.TokenService;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.user.UserSignupForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private TokenService<Session> tokenService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setTokenService(TokenService<Session> tokenService) {
        this.tokenService = tokenService;
    }

    public Token signup(UserSignupForm form) throws IOException {
        if (userRepository.existsByAccount(form.getAccount())) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setFieldError("account", "이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByEmail(form.getEmail())) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setFieldError("email", "이미 사용중인 이메일 입니다.");
        }

        User user = new User();
        user.setAccount(form.getAccount());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        Session session = new Session(user.getId(), user.getAccount(), user.getRole());
        Token token = tokenService.issue(session);

        return token;
    }
}
