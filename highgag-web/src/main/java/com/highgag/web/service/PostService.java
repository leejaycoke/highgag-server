package com.highgag.web.service;

import com.highgag.core.entity.Post;
import com.highgag.core.repository.PostRepository;
import com.highgag.web.form.PostWriteForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void write(PostWriteForm form) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        postRepository.save(post);
    }

    public List<Post> getList() {
        return postRepository.findAll();
    }
}
