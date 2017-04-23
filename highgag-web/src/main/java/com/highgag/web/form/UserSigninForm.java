package com.highgag.web.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSigninForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String account;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
