package com.dasensio.dms.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dasensio.dms.domain.Post;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceIT {

    @Autowired
    private PostService service;


    @Test
    void getPost_withId_returnsPost() {
        Post post = service.getPostById(1L);
        assertNotNull(post);
    }

    @Test
    void savePost_returnsId() {
        Long id = service.savePost("title", "body");
        assertNotNull(id);
    }

}