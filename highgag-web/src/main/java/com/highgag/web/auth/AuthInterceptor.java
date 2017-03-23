package com.highgag.web.auth;

import com.highgag.core.domain.Role;
import com.highgag.web.exception.HighgagException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService<Session> tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        Authorization authorization = method.getMethodAnnotation(Authorization.class);
        if (authorization == null) {
            return true;
        }

        List<Role> roles = Arrays.asList(authorization.roles());

        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw unauthorized();
        }

        Session session;
        try {
            session = tokenService.decrypt(accessToken, Session.class);
        } catch (Exception e) {
            throw unauthorized();
        }

        if (!roles.contains(session.getRole()) && !roles.contains(Role.ALL)) {
            throw denied();
        }

        request.setAttribute("session", session);
        return true;
    }

    private HighgagException unauthorized() {
        return new HighgagException(HttpStatus.UNAUTHORIZED, "로그인해주세요.");
    }

    private HighgagException denied() {
        return new HighgagException(HttpStatus.FORBIDDEN, "접근할 수 없습니다.");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
