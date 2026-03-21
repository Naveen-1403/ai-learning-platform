package com.mca.learning_platform.controller;

import com.mca.learning_platform.model.RoadmapData;
import com.mca.learning_platform.repository.RoadmapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db/roadmap")
public class RoadmapController {

    @Autowired
    private RoadmapRepository roadmapRepo;

    // 1. Roadmap-ஐ டேட்டாபேஸில் சேமிக்க
    @PostMapping("/save")
    public String saveRoadmap(@RequestBody RoadmapData data) {
        roadmapRepo.save(data);
        return "Roadmap Saved Successfully!";
    }

    // 2. சேமித்த Roadmap-ஐ திரும்ப எடுக்க
    @GetMapping("/get")
    public RoadmapData getRoadmap(@RequestParam String studentName) {
        return roadmapRepo.findTopByStudentNameOrderByIdDesc(studentName);
    }
}