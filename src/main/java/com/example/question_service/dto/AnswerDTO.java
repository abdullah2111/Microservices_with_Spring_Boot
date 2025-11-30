package com.example.question_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    private Long questionId;
    private String answer;
}