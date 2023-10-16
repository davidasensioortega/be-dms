package com.dasensio.dms.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dasensio.dms.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private CommentService service;

    private CommentController controller;

    @BeforeEach
    public void beforeEach() {
        service = mock(CommentService.class);
        controller = new CommentController(service);
    }

    @Test
    void saveComment_whenOk_returnsId() {
        when(service.saveComment(any(), any(), any())).thenReturn(1L);
        Long id = controller.saveComment(1L, "t", "b");
        verify(service).saveComment(any(), any(), any());
        Assertions.assertEquals(1L, id);
    }

}