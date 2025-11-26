package com.example.quizapp.servcie;


import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, PageRequest.of(0, numQ));
        if (questions.size() < numQ) {
            return ResponseEntity.badRequest().build();  // Return 400 if not enough questions
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        Set<Question> questionSet = new HashSet<>(questions);
        quiz.setQuestions(questionSet);
        Quiz savedQuiz = quizRepository.save(quiz);
        return ResponseEntity.status(201).body(savedQuiz);
    }
}
