package com.highgag.web.service;

import com.highgag.core.domain.Role;
import com.highgag.core.entity.User;
import com.highgag.core.repository.UserRepository;
import com.highgag.web.auth.ScryptService;
import com.highgag.web.auth.Session;
import com.highgag.web.auth.Token;
import com.highgag.web.auth.TokenService;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.form.UserSigninForm;
import com.highgag.web.form.UserSignupForm;
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
    private TokenService tokenService;

    @Autowired
    private ScryptService scryptService;

    public User get(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new HighgagException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Token signin(UserSigninForm form) throws IOException {
        User user = userRepository.findByAccount(form.getAccount());
        if (user == null) {
            throw new HighgagException(HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호를 찾을 수 없습니다.");
        }

        if (!scryptService.check(form.getPassword(), user.getPassword())) {
            throw new HighgagException(HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호를 찾을 수 없습니다.");
        }

        Token token = tokenService.issue(new Session(user.getId(), user.getAccount(), user.getRole()));
        return token;
    }

    public Token signup(UserSignupForm form) throws IOException {
        if (userRepository.existsByAccount(form.getAccount())) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setError("account", "이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByEmail(form.getEmail())) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setError("email", "이미 사용중인 이메일입니다.");
        }

        User user = User.builder()
                .account(form.getAccount())
                .name(form.getName())
                .email(form.getEmail())
                .password(scryptService.encrypt(form.getPassword()))
                .role(Role.MEMBER)
                .build();

        userRepository.save(user);

        Session session = new Session(user.getId(), user.getAccount(), user.getRole());
        Token token = tokenService.issue(session);

        return token;
    }
}
