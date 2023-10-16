package com.dasensio.dms.api.controller;

import com.dasensio.dms.api.dto.PostDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostControllerIT {

    private static final String LOCALHOST = "http://localhost:";

    private static final String POSTS_ENDPOINT = "/posts";

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
    void savePost_whenOk_returnsId() {
        String documentId = saveDocument().getBody();
        ResponseEntity<Long> response = savePost(documentId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    private ResponseEntity<Long> savePost(String documentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("document_id", documentId);
        body.add("title", "test title");
        body.add("body", "test body");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(LOCALHOST + port + POSTS_ENDPOINT , requestEntity, Long.class);
    }

    @Test
    void savePost_whenNoDocument_throwsException() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> savePost("noId"));
    }

    private ResponseEntity<String> saveDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(LOCALHOST + port + "/documents", requestEntity, String.class);
    }

    private FileSystemResource getTestFile() {
        try {
            return new FileSystemResource(new ClassPathResource("test.pdf").getFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPost_whenExists_returnsData() {
        String url = LOCALHOST + port + POSTS_ENDPOINT + "/1";
        ResponseEntity<PostDto> responseEntity = restTemplate.getForEntity(url, PostDto.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    void getPost_whenNotExists_throwsException() {
        String url = LOCALHOST + port + POSTS_ENDPOINT + "/9999";
        Assertions.assertThrows(HttpServerErrorException.class, () -> restTemplate.getForEntity(url, PostDto.class));
    }

}