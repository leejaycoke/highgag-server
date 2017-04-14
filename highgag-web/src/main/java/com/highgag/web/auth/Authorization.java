package com.highgag.web.auth;

import com.highgag.core.domain.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {

    Role[] roles() default Role.ALL;

    boolean allowGuest() default false;
}
