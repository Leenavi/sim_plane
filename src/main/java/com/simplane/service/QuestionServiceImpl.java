package com.simplane.service;

import com.simplane.domain.QuestionVO;
import com.simplane.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    @Override
    public QuestionVO getQuestionByIndex(Long testid, int step) {
        return questionMapper.readQuestionByIndex(testid, step);
    }

    @Override
    public boolean hasNextQuestion(Long testid, int step) {

        System.out.println("testid = " + testid + ", step = " + step);
        System.out.println("hasNext = " + questionMapper.existsQuestionAfter(testid, step));


        return questionMapper.existsQuestionAfter(testid, step);
    }
}
