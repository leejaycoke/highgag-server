package com.highgag.web.controller;

import com.highgag.web.form.PostWriteForm;
import com.highgag.web.response.ErrorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @Before
    public void init() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void 글등록_null데이터_실패() {
        ResponseEntity<ErrorResponse> entity = restTemplate
                .postForEntity("http://localhost:18888/posts", null, ErrorResponse.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 415);
    }

    @Test
    public void 글등록_공백데이터_실패() {
        ResponseEntity<ErrorResponse> entity = restTemplate
                .postForEntity("http://localhost:18888/posts", new PostWriteForm(), ErrorResponse.class);
        Assert.assertEquals(entity.getStatusCodeValue(), 400);
    }

    @Test
    public void 글등록_성공() {
        PostWriteForm form = new PostWriteForm();
        form.setTitle("title");
        form.setContent("content");

        ResponseEntity entity = restTemplate
                .postForEntity("http://localhost:18888/posts", form, null);

        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertNull(entity.getBody());
    }

}