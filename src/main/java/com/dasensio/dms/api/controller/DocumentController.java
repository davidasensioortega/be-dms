package com.dasensio.dms.api.controller;

import com.dasensio.dms.api.dto.DocumentDto;
import com.dasensio.dms.api.util.DocumentDtoMapper;
import com.dasensio.dms.domain.Document;
import com.dasensio.dms.service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    private final DocumentDtoMapper mapper;

    public DocumentController(DocumentService documentService, DocumentDtoMapper mapper) {
        this.documentService = documentService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveDocument(@RequestParam("file") MultipartFile file) {
        try {
            Assert.isTrue(!file.isEmpty(), "empty file received");
            DocumentDto documentDto = new DocumentDto();
            documentDto.setName(file.getOriginalFilename());
            documentDto.setData(file.getBytes());
            return documentService.saveDocument(mapper.toDocument(documentDto));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<DocumentDto> getDocuments() {
        Collection<Document> documents = documentService.getDocuments();
        documents.forEach(d -> d.setData(null));
        return mapper.toDocumentsDto(documents);
    }

    @GetMapping("/{document_id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<byte[]> getDocument(@PathVariable("document_id") String documentId, HttpServletResponse response) {
        Document document = documentService.getDocument(documentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment; filename=" + document.getName());

        return new HttpEntity<>(document.getData(), headers);
    }

    @DeleteMapping("/{document_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@PathVariable("document_id") String documentId) {
        documentService.deleteDocument(documentId);
    }

}
