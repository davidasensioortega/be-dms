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
class CommentControllerIT {

    private static final String LOCALHOST = "http://localhost:";

    private static final String COMMENTS_ENDPOINT = "/comments";

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
    void saveComment_whenOk_returnsId() {
        ResponseEntity<Long> response = saveComment(1L, "test name", "test body");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    private ResponseEntity<Long> saveComment(Long postId, String name, String comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("post_id", postId);
        body.add("name", name);
        body.add("body", comment);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(LOCALHOST + port + COMMENTS_ENDPOINT , requestEntity, Long.class);
    }

    @Test
    void saveComment_whenNoPostId_throwsException() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> saveComment(null, "test name", "test body"));
    }

    @Test
    void saveComment_whenNoNAme_throwsException() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> saveComment(1L, null, "test body"));
    }

}