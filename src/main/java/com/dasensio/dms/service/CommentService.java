package com.dasensio.dms.service;

public interface CommentService {

    Long saveComment(Long postId, String title, String body);

}
