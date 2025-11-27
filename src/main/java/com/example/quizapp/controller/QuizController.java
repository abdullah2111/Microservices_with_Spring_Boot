package com.example.quizapp.controller;


import com.example.quizapp.dto.AnswerDTO;
import com.example.quizapp.dto.QuestionResponseDTO;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.servcie.QuestionService;
import com.example.quizapp.servcie.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuestionService questionService;
    private final QuizService quizService;


    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(
            @RequestParam String category,
            @RequestParam int numQ,
            @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }


    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionResponseDTO>> getQuizQuestions(@PathVariable Long quizId) {
        List<QuestionResponseDTO> questionDTOs = quizService.getQuizQuestions(quizId);
        return ResponseEntity.ok(questionDTOs);
    }


    @PostMapping("/{quizId}/submit")
    public ResponseEntity<Integer> submitAnswers(@PathVariable Long quizId, @RequestBody List<AnswerDTO> answers) {
        int totalMarks = quizService.evaluateQuiz(quizId, answers);

        return ResponseEntity.ok(totalMarks);
    }

}
