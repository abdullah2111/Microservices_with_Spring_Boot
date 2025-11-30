package com.example.question_service.servcie;


import com.example.question_service.dto.AnswerDTO;
import com.example.question_service.dto.QuestionResponseDTO;
import com.example.question_service.entity.Question;
import com.example.question_service.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }



    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> addQuestions(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }



    public Optional<Question> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Long numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);

        return ResponseEntity.ok(questions);

    }

    public ResponseEntity<List<QuestionResponseDTO>> getQuestionsFromId(List<Long> questionIds) {

        List<QuestionResponseDTO> responseList = new ArrayList<>();
        for (Long questionId : questionIds) {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);

            if (optionalQuestion.isEmpty()) {
                System.out.println("Warning: Question not found with ID: " + questionId);
                continue;
            }



            Question question = optionalQuestion.get();
            QuestionResponseDTO dto = new QuestionResponseDTO(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );
            responseList.add(dto);
        }

        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<Integer> getScore(List<AnswerDTO> responses) {
        Integer score = 0;

        for (AnswerDTO answer : responses) {
            Optional<Question> optionalQuestion = questionRepository.findById(answer.getQuestionId());
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                if (answer.getAnswer().equals(question.getRightAnswer())) {
                    score++;
                }
            }

            else {
                System.out.println("Question not found for ID: " + answer.getQuestionId());
            }
        }


        return ResponseEntity.ok(score);
    }



}
