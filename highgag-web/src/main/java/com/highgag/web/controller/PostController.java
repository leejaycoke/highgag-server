package com.highgag.web.controller;

import com.highgag.core.domain.Role;
import com.highgag.core.entity.Post;
import com.highgag.web.auth.Authorization;
import com.highgag.web.form.PostWriteForm;
import com.highgag.web.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> get() {
        return postService.getList();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    @Authorization(roles = Role.ADMIN)
    public void post(@Valid @RequestBody PostWriteForm form) {
        postService.write(form);
    }

}
