package com.highgag.web.controller;

import com.highgag.web.auth.Token;
import com.highgag.web.form.UserSignupForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void 회원가입_null_데이터_실패() {
        ResponseEntity entity = testRestTemplate
                .postForEntity("http://localhost:18888/auth/signup", null, null);
        Assert.assertEquals(entity.getStatusCodeValue(), 415);
    }

    @Test
    public void 회원가입_공백_데이터_실패() {
        ResponseEntity entity = testRestTemplate
                .postForEntity("http://localhost:18888/auth/signup", new UserSignupForm(), null);
        Assert.assertEquals(entity.getStatusCodeValue(), 400);
    }

    @Test
    public void 회원가입_성공() {
        UserSignupForm form = new UserSignupForm();
        form.setAccount("account");
        form.setEmail("email@address.com");
        form.setName("이주현");
        form.setPassword("12345678");

        ResponseEntity<Token> entity = testRestTemplate
                .postForEntity("http://localhost:18888/auth/signup", form, Token.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertNotNull(entity.getBody().getValue());
    }

    @Test
    public void 로그인_실패() {
        UserSignupForm form = new UserSignupForm();
        form.setAccount("account");
        form.setEmail("email@address.com");
        form.setName("이주현");
        form.setPassword("12345678");

        ResponseEntity<Token> entity = testRestTemplate
                .postForEntity("http://localhost:18888/auth/signup", form, Token.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertNotNull(entity.getBody().getValue());
    }

}
