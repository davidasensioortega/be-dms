package com.dasensio.dms.api.controller;

import com.dasensio.dms.api.dto.PostDto;
import com.dasensio.dms.api.util.PostDtoMapper;
import com.dasensio.dms.domain.Document;
import com.dasensio.dms.domain.Post;
import com.dasensio.dms.service.DocumentService;
import com.dasensio.dms.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final DocumentService documentService;

    private final PostService postService;

    private final PostDtoMapper mapper;

    public PostController(DocumentService documentService, PostService postService, PostDtoMapper mapper) {
        this.documentService = documentService;
        this.postService = postService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long savePost(@RequestParam("document_id") String documentId, @RequestParam("title") String title, @RequestParam("body") String body) {
        Document document = documentService.getDocument(documentId);
        Assert.notNull(document, "document doesn't exist for id " + documentId);
        Long postId = postService.savePost(title, body);
        document.setPostId(postId);
        documentService.saveDocument(document);
        return postId;
    }

    @GetMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable("post_id") Long postId) {
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "post doesn't exist for id " + postId);
        return mapper.toPostDto(post);
    }

}
