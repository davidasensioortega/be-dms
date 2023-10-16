package com.dasensio.dms.service;

import com.dasensio.dms.domain.Comment;
import com.dasensio.dms.domain.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "jsonplaceholder",
    url = "https://jsonplaceholder.typicode.com/",
    fallback = JsonPlaceholderClientFallback.class)
public interface JsonPlaceholderClient {

    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);

    @PostMapping("/posts")
    Post sendPost(Post post);

    @PostMapping("/comments")
    Comment sendComment(Comment comment);

}