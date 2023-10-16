package com.dasensio.dms.service.impl;

import com.dasensio.dms.domain.Post;
import com.dasensio.dms.service.JsonPlaceholderClient;
import com.dasensio.dms.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PostServiceImpl implements PostService {

    private final JsonPlaceholderClient jsonPlaceholderClient;

    public PostServiceImpl(JsonPlaceholderClient jsonPlaceholderClient) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
    }

    @Override
    public Post getPostById(Long id) {
        return jsonPlaceholderClient.getPostById(id);
    }

    @Override
    public Long savePost(String title, String body) {
        Assert.notNull(title, "title must not be null");
        Post post = jsonPlaceholderClient.sendPost(Post.PostBuilder.aPost()
            .withTitle(title)
            .withBody(body)
            .build());
        return post.getId();
    }

}
