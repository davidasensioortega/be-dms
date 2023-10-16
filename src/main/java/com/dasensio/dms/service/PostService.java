package com.dasensio.dms.service;

import com.dasensio.dms.domain.Post;

public interface PostService {

    Post getPostById(Long id);

    Long savePost(String title, String body);

}
