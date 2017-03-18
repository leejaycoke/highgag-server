package com.highgag.web.user;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class UserSignupForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 6, max = 20, message = "아이디는 6~20자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 영어(소문자), 숫자만 입력할 수 있습니다.")
    private String account;

    @NotBlank(message = "이름을 입력해주세요.")
    @Length(min = 6, max = 12, message = "이름은 6~12자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-z0-9가-힣]+$", message = "이름은 한글,영어,숫자만 입력할 수 있습니다.")
    private String name;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "이메일 주소가 옳바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 30, message = "비밀번호는 8~30자까지 입력할 수 있습니다.")
    private String password;

}
