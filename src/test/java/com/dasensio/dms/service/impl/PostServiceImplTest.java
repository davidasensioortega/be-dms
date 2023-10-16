package com.dasensio.dms.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dasensio.dms.domain.Post;
import com.dasensio.dms.service.JsonPlaceholderClient;
import com.dasensio.dms.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    private PostService service;

    private JsonPlaceholderClient client;

    @BeforeEach
    public void beforeEach() {
        client = mock(JsonPlaceholderClient.class);
        service = new PostServiceImpl(client);
    }

    @Test
    void savePost_whenNullTitle_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.savePost(null, null));
    }

    @Test
    void savePost_whenData_returnsId() {
        when(client.sendPost(any())).thenReturn(Post.PostBuilder.aPost().withId(1L).build());
        Long postId = service.savePost("title", "body");
        verify(client).sendPost(any());
        Assertions.assertEquals(1L, postId);
    }

    @Test
    void getPostById_whenNotExists_returnsNull() {
        when(client.getPostById(any())).thenReturn(null);
        Post post = service.getPostById(1L);
        verify(client).getPostById(any());
        Assertions.assertNull(post);
    }

    @Test
    void getPostById_whenExists_returnsPost() {
        when(client.getPostById(any())).thenReturn(Post.PostBuilder.aPost().build());
        Post post = service.getPostById(1L);
        verify(client).getPostById(any());
        Assertions.assertNotNull(post);
    }

}