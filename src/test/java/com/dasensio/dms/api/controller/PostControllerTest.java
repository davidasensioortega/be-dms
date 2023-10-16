package com.dasensio.dms.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.dasensio.dms.api.dto.PostDto;
import com.dasensio.dms.api.util.PostDtoMapperImpl;
import com.dasensio.dms.domain.Document;
import com.dasensio.dms.domain.Post;
import com.dasensio.dms.service.DocumentService;
import com.dasensio.dms.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    private DocumentService documentService;

    private PostService postService;

    private PostController controller;

    @BeforeEach
    public void beforeEach() {
        documentService = mock(DocumentService.class);
        postService = mock(PostService.class);
        controller = new PostController(documentService, postService, new PostDtoMapperImpl());
    }

    @Test
    void savePost_whenNotExists_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.savePost("1", "t", "b"));
        verify(documentService).getDocument(any());
        verifyNoInteractions(postService);
    }

    @Test
    void savePost_whenOk_returnsId() {
        when(documentService.getDocument(any())).thenReturn(Document.DocumentBuilder.aDocument().build());
        when(postService.savePost(any(), any())).thenReturn(1L);
        Long id = controller.savePost("1", "t", "b");
        verify(documentService).getDocument(any());
        verify(documentService).saveDocument(any());
        verify(postService).savePost(any(), any());
        Assertions.assertEquals(1L, id);
    }

    @Test
    void getPost_returnsPostFromService() {
        when(postService.getPostById(any())).thenReturn(Post.PostBuilder.aPost().withTitle("t").withBody("b").build());
        PostDto post = controller.getPost(1L);
        verify(postService).getPostById(1L);
        Assertions.assertNotNull(post);
        Assertions.assertEquals("t", post.getTitle());
        Assertions.assertEquals("b", post.getBody());
    }

}