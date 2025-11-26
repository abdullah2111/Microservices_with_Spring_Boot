package com.example.quizapp.controller;


import com.example.quizapp.entity.Quiz;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.servcie.QuestionService;
import com.example.quizapp.servcie.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuestionService questionService;
    private final QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

}
