package com.mca.learning_platform.controller;

import com.mca.learning_platform.model.QuizScore;
import com.mca.learning_platform.repository.QuizScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db")
public class ScoreController {

    @Autowired
    private QuizScoreRepository scoreRepository;

    @PostMapping("/save-score")
    public String saveScore(@RequestBody QuizScore quizScore) {
        scoreRepository.save(quizScore);
        return "Score saved successfully to Database! 🎉";
    }
}