package com.dasensio.dms.domain;

public class Comment {

    private Long id;
    private Long postId;
    private String name;
    private String email;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static final class CommentBuilder {

        private Long id;
        private Long postId;
        private String name;
        private String email;
        private String body;

        private CommentBuilder() {
        }

        public static CommentBuilder aComment() {
            return new CommentBuilder();
        }

        public CommentBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CommentBuilder withPostId(Long postId) {
            this.postId = postId;
            return this;
        }

        public CommentBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CommentBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CommentBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public Comment build() {
            Comment comment = new Comment();
            comment.setId(id);
            comment.setPostId(postId);
            comment.setName(name);
            comment.setEmail(email);
            comment.setBody(body);
            return comment;
        }
    }

}
