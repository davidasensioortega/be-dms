package com.dasensio.dms.api.controller;

import com.dasensio.dms.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService documentService) {
        this.commentService = documentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveComment(@RequestParam("post_id") Long postId, @RequestParam("name") String name, @RequestParam("body") String body) {
        return commentService.saveComment(postId, name, body);
    }

}
