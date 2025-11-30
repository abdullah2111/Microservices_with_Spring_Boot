package com.example.question_service.controller;


import com.example.question_service.dto.AnswerDTO;
import com.example.question_service.dto.QuestionResponseDTO;
import com.example.question_service.entity.Question;
import com.example.question_service.servcie.QuestionService;
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



//    for microsservices


    @GetMapping("generate")
    public ResponseEntity<List<Long>> generateQuestions(@RequestParam String categoryName, @RequestParam Long numQuestions) {
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }



    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionResponseDTO>> getQuestions(@RequestBody List<Long> questionsIds) {
        return questionService.getQuestionsFromId(questionsIds);

    }



    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<AnswerDTO> responses){
        return  questionService.getScore(responses);
    }


}
