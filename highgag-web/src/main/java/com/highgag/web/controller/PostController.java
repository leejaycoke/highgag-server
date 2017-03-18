package com.highgag.web.controller;

import com.highgag.core.entity.Post;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.post.PostService;
import com.highgag.web.post.PostWriteForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public void post(@Valid PostWriteForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new HighgagException(HttpStatus.BAD_REQUEST)
                    .setFieldErrors(bindingResult.getFieldErrors());
        }

        postService.write(form);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> get() {
        return postService.getList();
    }

}