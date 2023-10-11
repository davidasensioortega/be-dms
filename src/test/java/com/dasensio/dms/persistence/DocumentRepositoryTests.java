package com.dasensio.dms.persistence;

import com.dasensio.dms.persistence.model.DocumentEntity;
import com.dasensio.dms.persistence.repository.DocumentRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class DocumentRepositoryTests {

    @Autowired
    private DocumentRepository repository;

    @Test
    void saveDocument() {
        DocumentEntity newDocument = getDocument();
        DocumentEntity savedDocument = repository.save(newDocument);
        Assertions.assertNotNull(savedDocument);
        Assertions.assertNotNull(savedDocument.getId());
    }

    @Test
    void findDocumentsByOwner() {
        DocumentEntity savedDocument = repository.save(getDocument());

        List<DocumentEntity> documents = repository.findByOwner("test owner");
        Assertions.assertEquals(1, documents.size());
        Assertions.assertEquals(savedDocument.getId(), documents.get(0).getId());
        Assertions.assertTrue(repository.findByOwner("other owner").isEmpty());
    }

    private DocumentEntity getDocument() {
        return DocumentEntity.DocumentEntityBuilder.aDocumentEntity()
            .withName("test name")
            .withOwner("test owner")
            .withData("test data".getBytes())
            .build();
    }

}
