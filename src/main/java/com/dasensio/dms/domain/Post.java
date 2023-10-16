package com.dasensio.dms.domain;

public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static final class PostBuilder {

        private Long id;
        private Long userId;
        private String title;
        private String body;

        private PostBuilder() {
        }

        public static PostBuilder aPost() {
            return new PostBuilder();
        }

        public PostBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PostBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public PostBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public Post build() {
            Post post = new Post();
            post.setId(id);
            post.setUserId(userId);
            post.setTitle(title);
            post.setBody(body);
            return post;
        }
    }

}
