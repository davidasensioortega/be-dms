package com.dasensio.dms.api.controller;

import com.dasensio.dms.api.dto.DocumentDto;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DocumentControllerIT {

    private static final String LOCALHOST = "http://localhost:";

    private static final String DOCUMENTS_ENDPOINT = "/documents";

    @Value(value = "${local.server.port}")
    private int port;

    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    public void beforeEach() {
        restTemplate = restTemplateBuilder.basicAuthentication("test_user", "password").build();
    }

    @Test
    void saveDocument_whenPdf_returnsId() {
        ResponseEntity<String> response = saveDocument();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    private ResponseEntity<String> saveDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile("test.pdf"));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(LOCALHOST + port + DOCUMENTS_ENDPOINT, requestEntity, String.class);
    }

    @Test
    void saveDocument_whenNoFile_throwsException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", null);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(LOCALHOST + port + DOCUMENTS_ENDPOINT, requestEntity, String.class));

    }

    @Test
    void getDocuments_whenCall_returnsCollection() {
        Collection<DocumentDto> documents = restTemplate.getForObject(LOCALHOST + port + DOCUMENTS_ENDPOINT, Collection.class);
        Assertions.assertNotNull(documents);
    }

    @Test
    void getDocument_whenExists_returnsContent() {
        ResponseEntity<String> response = saveDocument();
        String id = response.getBody();
        String url = LOCALHOST + port + DOCUMENTS_ENDPOINT + "/" + id;
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    void getDocument_whenNotExists_throwsException() {
        String url = LOCALHOST + port + DOCUMENTS_ENDPOINT + "/noId";
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.getForEntity(url, byte[].class));
    }

    @Test
    void deleteDocument_whenExists_returnsOk() {
        ResponseEntity<String> response = saveDocument();
        String id = response.getBody();
        String url = LOCALHOST + port + DOCUMENTS_ENDPOINT + "/" + id;
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Object.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteDocument_whenNotExists_throwsException() {
        String url = LOCALHOST + port + DOCUMENTS_ENDPOINT + "/noId";
        Assertions.assertThrows(HttpClientErrorException.class, () -> restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Object.class));
    }

    private FileSystemResource getTestFile(String filename) {
        try {
            return new FileSystemResource(new ClassPathResource(filename).getFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}