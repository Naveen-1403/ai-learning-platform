package com.mca.learning_platform.controller;

import com.mca.learning_platform.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final GeminiService geminiService;

    public AIController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    // 1. Roadmap-க்கான API
    @GetMapping("/roadmap")
    public String getRoadmap(@RequestParam String topic) {
        return geminiService.generateRoadmap(topic);
    }

    // 2. Quiz-க்கான API
    @GetMapping("/quiz")
    public String getQuiz(@RequestParam String topic) {
        return geminiService.generateQuiz(topic);
    }

    // 3. Mock Interview-க்கான API
    @GetMapping("/interview")
    public String startInterview(@RequestParam String role, @RequestParam String message) {
        return geminiService.getInterviewResponse(role, message);
    }

    // 4. AI Helpdesk Chatbot-க்கான புதிய API 👇
    @GetMapping("/chat")
    public String getChatResponse(@RequestParam String prompt) {
        return geminiService.getChatResponse(prompt);
    }
}