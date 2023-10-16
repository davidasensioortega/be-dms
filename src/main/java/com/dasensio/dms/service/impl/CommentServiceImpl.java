package com.dasensio.dms.service.impl;

import com.dasensio.dms.domain.Comment;
import com.dasensio.dms.service.CommentService;
import com.dasensio.dms.service.JsonPlaceholderClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CommentServiceImpl implements CommentService {

    private final JsonPlaceholderClient jsonPlaceholderClient;

    public CommentServiceImpl(JsonPlaceholderClient jsonPlaceholderClient) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
    }

    @Override
    public Long saveComment(Long postId, String name, String body) {
        Assert.notNull(postId, "postId must not be null");
        Assert.notNull(name, "name must not be null");
        Comment comment = jsonPlaceholderClient.sendComment(Comment.CommentBuilder.aComment()
            .withPostId(postId)
            .withName(name)
            .withBody(body)
            .build());
        return comment.getId();
    }

}
