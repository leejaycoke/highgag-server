package com.highgag.web.controller;

import com.highgag.web.form.PostWriteForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 글등록_null데이터_실패() {
        ResponseEntity entity = restTemplate
                .postForEntity("http://localhost:18888/posts", null, null);
        Assert.assertEquals(entity.getStatusCodeValue(), 415);
    }

    @Test
    public void 글등록_공백데이터_실패() {
        ResponseEntity entity = restTemplate
                .postForEntity("http://localhost:18888/posts", new PostWriteForm(), null);
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