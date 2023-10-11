package com.dasensio.dms.service;

import com.dasensio.dms.domain.Document;
import java.util.Collection;

public interface DocumentService {

    String saveDocument(Document document);


    Collection<Document> getDocuments();


    Document getDocument(String documentId);

    void deleteDocument(String documentId);
}
