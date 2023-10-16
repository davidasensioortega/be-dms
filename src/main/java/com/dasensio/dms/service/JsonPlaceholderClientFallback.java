package com.dasensio.dms.service;

import com.dasensio.dms.domain.Comment;
import com.dasensio.dms.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class JsonPlaceholderClientFallback implements JsonPlaceholderClient {

    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    public Post sendPost(Post post) {
        return null;
    }

    @Override
    public Comment sendComment(Comment comment) {
        return null;
    }

}