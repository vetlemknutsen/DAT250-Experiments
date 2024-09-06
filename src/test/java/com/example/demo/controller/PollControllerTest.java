package com.example.demo.controller;

import com.example.demo.domains.Poll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PollControllerTest {

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplateBuilder().build();
    }

    private final String baseUrl = "http://localhost:8080/v1/poll";
    private final String userBaseUrl = "http://localhost:8080/v1/user";


    private void createUser(String username) {
        String url = userBaseUrl + "/create?username=" + username;

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User was created", response.getBody());
    }

    @Test
    public void testCreatePoll() {
        // Ensure user is created first
        String username = "user1";
        createUser(username);

        String url = baseUrl + "/create?username=" + username;

        Poll poll = new Poll();
        poll.setQuestion("What is your favorite color?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Poll> request = new HttpEntity<>(poll, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Poll was created", response.getBody());
    }

    @Test
    public void testGetAllPolls() {
        String url = baseUrl + "/getAll";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testChangePoll() {
        String url = baseUrl + "/change?pollId=1";

        Poll changedPoll = new Poll();
        changedPoll.setQuestion("Updated question?");
        changedPoll.setPublishedAt(Instant.now());
        changedPoll.setValidUntil(Instant.now().plusSeconds(7200));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Poll> request = new HttpEntity<>(changedPoll, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Poll changed", response.getBody());
    }

    @Test
    public void testDeletePoll() {
        String url = baseUrl + "/delete?pollId=1";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Poll deleted", response.getBody());
    }
}

