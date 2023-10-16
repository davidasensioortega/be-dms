package com.dasensio.dms.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dasensio.dms.domain.Comment;
import com.dasensio.dms.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class JsonPlaceholderClientIT {

    @Autowired
    JsonPlaceholderClient client;

    @Test
    void whenGetPostWithId_thenPostExist() {
        Post post = client.getPostById(1L);
        assertNotNull(post);
    }

    @Test
    void whenSendPost_thenReturnsId() {
        Post post = client.sendPost(Post.PostBuilder.aPost()
            .withTitle("title")
            .withBody("body")
            .build());
        assertNotNull(post);
        assertNotNull(post.getId());
    }

    @Test
    void whenSendComment_thenReturnsId() {
        Comment comment = client.sendComment(Comment.CommentBuilder.aComment()
            .withPostId(1L)
            .withName("name")
            .build());
        assertNotNull(comment);
        assertNotNull(comment.getId());
    }

}