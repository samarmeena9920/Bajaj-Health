package com.bajajhealth.sqlsolver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // Step 1: Generate webhook
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        String requestBody = "{\"name\":\"John Doe\",\"regNo\":\"REG12347\",\"email\":\"john@example.com\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            System.err.println("Failed to generate webhook: " + response.getStatusCode());
            return;
        }
        JsonNode json = mapper.readTree(response.getBody());
        String webhookUrl = json.get("webhook").asText();
        String accessToken = json.get("accessToken").asText();
        System.out.println("Webhook URL: " + webhookUrl);
        System.out.println("Access Token: " + accessToken);

        // Step 2: Solve SQL problem (Question 1, since regNo ends with odd digit)
        String finalQuery = "SELECT * FROM ..."; // TODO: Replace with actual SQL solution

        // Step 3: Submit solution
        String submitUrl = webhookUrl;
        String submitBody = String.format("{\"finalQuery\":\"%s\"}", finalQuery.replace("\"", "\\\""));
        HttpHeaders submitHeaders = new HttpHeaders();
        submitHeaders.setContentType(MediaType.APPLICATION_JSON);
        submitHeaders.set("Authorization", accessToken);
        HttpEntity<String> submitEntity = new HttpEntity<>(submitBody, submitHeaders);
        ResponseEntity<String> submitResponse = restTemplate.postForEntity(submitUrl, submitEntity, String.class);
        System.out.println("Submission response: " + submitResponse.getStatusCode());
        System.out.println(submitResponse.getBody());
    }
}
