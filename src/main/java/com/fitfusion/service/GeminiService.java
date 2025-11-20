package com.fitfusion.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    public String getRecommendation(String apiKey, String prompt) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Please configure your Gemini API Key in Settings first.";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Construct JSON payload
            ObjectNode root = objectMapper.createObjectNode();
            ArrayNode contents = root.putArray("contents");
            ObjectNode content = contents.addObject();
            ArrayNode parts = content.putArray("parts");
            ObjectNode part = parts.addObject();
            part.put("text", prompt);

            HttpEntity<String> request = new HttpEntity<>(root.toString(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL + apiKey, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode responseJson = objectMapper.readTree(response.getBody());
                return responseJson.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            } else {
                return "Error calling Gemini API: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Failed to get recommendation: " + e.getMessage();
        }
    }
}
