package com.dasensio.dms.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dasensio.dms.domain.Document;
import com.dasensio.dms.persistence.model.DocumentEntity;
import com.dasensio.dms.persistence.repository.DocumentRepository;
import com.dasensio.dms.service.DocumentService;
import com.dasensio.dms.service.util.DocumentMapperImpl;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    private DocumentService service;

    private DocumentRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = mock(DocumentRepository.class);
        service = new DocumentServiceImpl(repository, new DocumentMapperImpl());
    }

    @Test
    void saveDocument_whenNull_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> service.saveDocument(null));
    }

    @Test
    void saveDocument_whenEmpty_throwsException() {
        Document doc = new Document();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveDocument(doc));
    }

    @Test
    void saveDocument_whenNoPdf_throwsException() {
        Document doc = Document.DocumentBuilder.aDocument()
            .withName("test.doc")
            .withData("test doc data".getBytes())
            .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveDocument(doc));
    }

    @Test
    void saveDocument_whenPdf_returnsId() {
        Document doc = Document.DocumentBuilder.aDocument()
            .withName("test.pdf")
            .withData(getContentFromFile("test.pdf"))
            .build();
        String id = UUID.randomUUID().toString();
        when(repository.save(any())).thenReturn(DocumentEntity.DocumentEntityBuilder.aDocumentEntity().withId(id).build());
        String documentId = service.saveDocument(doc);
        verify(repository).save(any());
        Assertions.assertEquals(id, documentId);
    }

    @Test
    void getDocuments_returnsDataFromRepository() {
        when(repository.findByOwner(any())).thenReturn(Collections.emptyList());
        Collection<Document> documents = service.getDocuments();
        verify(repository).findByOwner(any());
        Assertions.assertTrue(documents.isEmpty());
    }

    @Test
    void getDocument_whenNotExists_returnsException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getDocument("id"));
        verify(repository).findById(any());
    }

    @Test
    void getDocument_whenNotOwner_returnsException() {
        when(repository.findById(any())).thenReturn(
            Optional.of(DocumentEntity.DocumentEntityBuilder.aDocumentEntity().withOwner("any").build()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getDocument("id"));
        verify(repository).findById(any());
    }

    @Test
    void getDocument_whenValidId_returnsDocument() {
        when(repository.findById(any())).thenReturn(
            Optional.of(DocumentEntity.DocumentEntityBuilder.aDocumentEntity().withOwner("no auth.").build()));
        Document document = service.getDocument("id");
        verify(repository).findById(any());
        Assertions.assertNotNull(document);
    }

    @Test
    void deleteDocument_whenValidId_deletesDocument() {
        when(repository.findById(any())).thenReturn(
            Optional.of(DocumentEntity.DocumentEntityBuilder.aDocumentEntity().withOwner("no auth.").build()));
        service.deleteDocument("id");
        verify(repository).findById(any());
        verify(repository).deleteById(any());
    }

    private byte[] getContentFromFile(String filename) {
        try {
            return Files.readAllBytes(new ClassPathResource(filename).getFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}