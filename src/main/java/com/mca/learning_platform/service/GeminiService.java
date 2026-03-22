package com.mca.learning_platform.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    // application.properties-ல் உள்ள உங்கள் API Key-ஐ எடுக்கும்
    @Value("${gemini.api.key}")
    private String apiKey;

    // --- 1. Roadmap உருவாக்குவதற்கான மெத்தட் ---
    public String generateRoadmap(String topic) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "You are an expert tutor. Create a step-by-step learning roadmap for a beginner to learn: " + topic + ". Use clear headings, bullet points, and mention estimated time for each step. Keep it highly professional and concise.";
        
        String requestBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" + prompt + "\" } ] } ] }";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return (String) parts.get(0).get("text"); 
        } catch (Exception e) {
            System.out.println("🔥 Roadmap Error: " + e.getMessage());
            e.printStackTrace(); 
            return "Error: AI-யிடம் பேச முடியவில்லை. காரணம்: " + e.getMessage();
        }
    }

    // --- 2. Quiz உருவாக்குவதற்கான மெத்தட் ---
    public String generateQuiz(String topic) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "Create a 5-question multiple choice quiz on the topic: " + topic + ". Return ONLY a valid JSON array. Each object must have: 'question', 'options' (array of 4 strings), 'answer' (the correct option string), and 'explanation'. Do not use markdown like ```json.";
        
        String requestBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" + prompt + "\" } ] } ] }";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return (String) parts.get(0).get("text"); 
        } catch (Exception e) {
            System.out.println("Quiz Error: " + e.getMessage());
            return "[{\"question\": \"Error loading quiz\", \"options\":[\"A\",\"B\",\"C\",\"D\"], \"answer\":\"A\", \"explanation\":\"Try again\"}]";
        }
    }

    // --- 3. Mock Interview மெத்தட் ---
    public String getInterviewResponse(String jobRole, String userMessage) {
    	String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "You are a professional Technical Interviewer for the role: " + jobRole + ". User says: '" + userMessage + "'. If userMessage is 'START', introduce yourself and ask the first technical question. Otherwise, evaluate their answer briefly and ask the next question. Respond in plain text.";
        
        String requestBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" + prompt + "\" } ] } ] }";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text"); 
        } catch (Exception e) {
            System.out.println("Interview Error: " + e.getMessage());
            return "Interview Error: AI-யால் பேச முடியவில்லை.";
        }
    }

    // --- 4. AI Helpdesk Chatbot மெத்தட் (புதிதாக சேர்க்கப்பட்டது) 👇 ---
 // --- 4. AI Helpdesk Chatbot மெத்தட் ---
    public String getChatResponse(String userPrompt) {
        // லிங்க்கில் எந்தப் பிராக்கெட்டும் இல்லாமல் சுத்தமாக இருக்க வேண்டும் 👇
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Chatbot-க்கான ஸ்பெஷல் Prompt
        String prompt = "You are a friendly, helpful, and concise AI Tutor for software engineering students. Give short, direct answers (max 3-4 sentences). The student asks: " + userPrompt;
        
        // JSON Body-ல் escape characters பிரச்சனை வராமல் இருக்க
        String safePrompt = prompt.replace("\"", "\\\"");
        String requestBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" + safePrompt + "\" } ] } ] }";
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text"); 
        } catch (Exception e) {
            System.out.println("Chatbot Error: " + e.getMessage());
            return "Sorry, I am having trouble connecting to the network right now. Please try again later.";
        }
    }
}