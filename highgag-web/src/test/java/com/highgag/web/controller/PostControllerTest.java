package com.highgag.web.controller;

import com.highgag.web.post.PostWriteForm;
import com.highgag.web.response.ErrorResponse;
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
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void POST_POST_BADREUQEST_TEST() {
        ResponseEntity<ErrorResponse> entity = restTemplate
                .postForEntity("http://localhost:8888/posts", new PostWriteForm(), ErrorResponse.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 400);
    }

    @Test
    public void POST_POST_BADREUQEST_TEST2() {
        PostWriteForm form = new PostWriteForm();
        form.setTitle("title");
        form.setContent("content");

        ResponseEntity<ErrorResponse> entity = restTemplate
                .postForEntity("http://localhost:8888/posts", form, ErrorResponse.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 200);
    }

}