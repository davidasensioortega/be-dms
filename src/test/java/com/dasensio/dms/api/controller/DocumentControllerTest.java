package com.dasensio.dms.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dasensio.dms.api.dto.DocumentDto;
import com.dasensio.dms.api.util.DocumentDtoMapperImpl;
import com.dasensio.dms.domain.Document;
import com.dasensio.dms.service.DocumentService;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    private DocumentService service;

    private DocumentController controller;

    @BeforeEach
    public void beforeEach() {
        service = mock(DocumentService.class);
        controller = new DocumentController(service, new DocumentDtoMapperImpl());
    }

    @Test
    void saveDocument_whenNull_throwsException() {
        Assertions.assertThrows(NullPointerException.class, () -> controller.saveDocument(null));
    }

    @Test
    void saveDocument_whenEmpty_throwsException() {
        MultipartFile file = new MockMultipartFile("sourceFile.tmp", "".getBytes());
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.saveDocument(file));
    }

    @Test
    void saveDocument_whenFile_returnsId() {
        MultipartFile file = new MockMultipartFile("sourceFile.tmp", "test".getBytes());
        when(service.saveDocument(any())).thenReturn("id");
        String id = controller.saveDocument(file);
        verify(service).saveDocument(any());
        Assertions.assertEquals("id", id);
    }

    @Test
    void getDocuments_returnsDataFromService() {
        when(service.getDocuments()).thenReturn(Collections.emptyList());
        Collection<DocumentDto> documents = controller.getDocuments();
        verify(service).getDocuments();
        Assertions.assertTrue(documents.isEmpty());
    }

    @Test
    void getDocument_returnsDocumentFromService() {
        byte[] data = "test data".getBytes();
        when(service.getDocument(any())).thenReturn(Document.DocumentBuilder.aDocument().withName("test.pdf").withData(data).build());
        HttpEntity<byte[]> entity = controller.getDocument("id", new MockHttpServletResponse());
        verify(service).getDocument("id");
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(MediaType.APPLICATION_PDF, entity.getHeaders().getContentType());
        Assertions.assertEquals(data, entity.getBody());
    }

    @Test
    void deleteDocument_callsService() {
        controller.deleteDocument("id");
        verify(service).deleteDocument("id");
    }

}