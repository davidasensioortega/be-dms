package com.dasensio.dms.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String owner;
    private Long postId;
    @Lob
    private byte[] data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static final class DocumentEntityBuilder {

        private String id;
        private String name;
        private String owner;
        private Long postId;
        private byte[] data;

        private DocumentEntityBuilder() {
        }

        public static DocumentEntityBuilder aDocumentEntity() {
            return new DocumentEntityBuilder();
        }

        public DocumentEntityBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public DocumentEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DocumentEntityBuilder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public DocumentEntityBuilder withPostId(Long postId) {
            this.postId = postId;
            return this;
        }

        public DocumentEntityBuilder withData(byte[] data) {
            this.data = data;
            return this;
        }

        public DocumentEntity build() {
            DocumentEntity documentEntity = new DocumentEntity();
            documentEntity.setId(id);
            documentEntity.setName(name);
            documentEntity.setOwner(owner);
            documentEntity.setPostId(postId);
            documentEntity.setData(data);
            return documentEntity;
        }
    }
}
