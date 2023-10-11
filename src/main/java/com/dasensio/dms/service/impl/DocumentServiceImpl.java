package com.dasensio.dms.service.impl;

import com.dasensio.dms.domain.Document;
import com.dasensio.dms.persistence.model.DocumentEntity;
import com.dasensio.dms.persistence.repository.DocumentRepository;
import com.dasensio.dms.service.DocumentService;
import com.dasensio.dms.service.util.DocumentMapper;
import com.dasensio.dms.service.util.PdfUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentMapper mapper;

    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper mapper) {
        this.documentRepository = documentRepository;
        this.mapper = mapper;
    }

    @Override
    public String saveDocument(Document document) {
        Assert.notNull(document.getName(), "Only documents with name allowed");
        Assert.isTrue(PdfUtils.isPdf(document.getData()), "Only pdf files allowed");
        document.setOwner(getUsername());
        DocumentEntity entity = documentRepository.save(mapper.toDocumentEntity(document));
        return entity.getId();
    }

    @Override
    public Collection<Document> getDocuments() {
        List<DocumentEntity> entities = documentRepository.findByOwner(getUsername());
        return mapper.toDocuments(entities);
    }

    @Override
    public Document getDocument(String documentId) {
        DocumentEntity entity = getDocumentAndVerify(documentId);
        return mapper.toDocument(entity);
    }

    private DocumentEntity getDocumentAndVerify(String documentId) {
        DocumentEntity entity = documentRepository.findById(documentId).orElse(null);
        Assert.notNull(entity, "document not found for id " + documentId);
        Assert.isTrue(entity.getOwner().equals(getUsername()), "user doesn't match " + getUsername());
        return entity;
    }

    @Override
    public void deleteDocument(String documentId) {
        DocumentEntity entity = getDocumentAndVerify(documentId);
        documentRepository.deleteById(entity.getId());
    }

    private String getUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getName).orElse("no auth.");
    }

}
