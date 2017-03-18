package com.highgag.web.service;

import com.highgag.core.domain.Role;
import com.highgag.core.entity.User;
import com.highgag.core.repository.UserRepository;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.user.UserSignupForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(UserSignupForm form) {
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
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        return user;
    }
}
