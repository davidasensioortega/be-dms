package com.dasensio.dms.domain;

public class Document {

    private String id;
    private String name;
    private String owner;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static final class DocumentBuilder {

        private String id;
        private String name;
        private String owner;
        private byte[] data;

        private DocumentBuilder() {
        }

        public static DocumentBuilder aDocument() {
            return new DocumentBuilder();
        }

        public DocumentBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public DocumentBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DocumentBuilder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public DocumentBuilder withData(byte[] data) {
            this.data = data;
            return this;
        }

        public Document build() {
            Document document = new Document();
            document.setId(id);
            document.setName(name);
            document.setOwner(owner);
            document.setData(data);
            return document;
        }
    }
}
