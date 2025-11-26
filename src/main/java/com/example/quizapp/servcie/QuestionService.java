package com.example.quizapp.servcie;

import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
