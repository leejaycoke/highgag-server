package com.highgag.web.controller;

import com.highgag.web.response.ErrorResponse;
import com.highgag.web.user.UserSignupForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by leejay on 2017. 2. 23..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void USER_POST_BADREUQEST_TEST() {
        ResponseEntity<ErrorResponse> entity = restTemplate
                .postForEntity("http://localhost:8888/users", new UserSignupForm(), ErrorResponse.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 400);
    }

}