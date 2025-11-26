package com.example.quizapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultyLevel;
    private String category;

    @ManyToMany(mappedBy = "questions")  // This is the inverse side
    private Set<Quiz> quizzes = new HashSet<>();

}
