package com.simplane.service;

import com.simplane.domain.QuestionVO;

public interface QuestionService {

    QuestionVO getQuestionByIndex(Long testid, int step);
    boolean hasNextQuestion(Long testid, int step);
}
