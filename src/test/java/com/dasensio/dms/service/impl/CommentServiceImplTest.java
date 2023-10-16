package com.dasensio.dms.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dasensio.dms.domain.Comment;
import com.dasensio.dms.service.CommentService;
import com.dasensio.dms.service.JsonPlaceholderClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private CommentService service;

    private JsonPlaceholderClient client;

    @BeforeEach
    public void beforeEach() {
        client = mock(JsonPlaceholderClient.class);
        service = new CommentServiceImpl(client);
    }

    @Test
    void saveComment_whenNullPostId_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveComment(null, "t", null));
    }

    @Test
    void saveComment_whenNullTitle_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveComment(1L, null, null));
    }

    @Test
    void saveComment_whenData_returnsId() {
        when(client.sendComment(any())).thenReturn(Comment.CommentBuilder.aComment().withId(1L).build());
        Long commentId = service.saveComment(1L, "title", "body");
        verify(client).sendComment(any());
        Assertions.assertEquals(1L, commentId);
    }

}