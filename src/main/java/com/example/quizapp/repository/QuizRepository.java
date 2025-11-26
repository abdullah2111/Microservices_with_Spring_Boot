package com.example.quizapp.repository;

import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuizRepository  extends JpaRepository<Quiz, Long> {

}
