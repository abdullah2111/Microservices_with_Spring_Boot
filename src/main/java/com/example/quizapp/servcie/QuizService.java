package com.example.quizapp.servcie;


import com.example.quizapp.dto.AnswerDTO;
import com.example.quizapp.dto.QuestionResponseDTO;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, PageRequest.of(0, numQ));

        if (questions.size() < numQ) {
            return ResponseEntity.badRequest().body(null);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);

        Set<Question> questionSet = new HashSet<>(questions);
        quiz.setQuestions(questionSet);

        Quiz savedQuiz = quizRepository.save(quiz);

        return ResponseEntity.status(201).body(savedQuiz);
    }



    public List<QuestionResponseDTO> getQuizQuestions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<QuestionResponseDTO> questionDTOs = quiz.getQuestions().stream()
                .map(question -> new QuestionResponseDTO(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()
                ))
                .collect(Collectors.toList());

        return questionDTOs;
    }



    public int evaluateQuiz(Long quizId, List<AnswerDTO> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));




        int totalMarks = 0;
        for (AnswerDTO answerDTO : answers) {
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            if (question.getRightAnswer().equals(answerDTO.getAnswer())) {
                totalMarks += 1;
            }
        }


        return totalMarks;
    }






}
