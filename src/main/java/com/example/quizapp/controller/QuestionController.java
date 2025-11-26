package com.example.quizapp.controller;

import com.example.quizapp.entity.Question;
import com.example.quizapp.servcie.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;



    @GetMapping("/all")
    public List<Question> findAll(){

        return questionService.findAllQuestions();
    }



    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Question>> addQuestions(@RequestBody List<Question> questions) {
        List<Question> savedQuestions = questionService.addQuestions(questions);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.findQuestionById(id);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Find questions by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = questionService.findQuestionsByCategory(category);
        return questions.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(questions);
    }


}
