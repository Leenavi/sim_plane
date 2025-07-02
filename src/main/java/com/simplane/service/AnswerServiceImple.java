package com.simplane.service;

import com.simplane.domain.AnswerVO;
import com.simplane.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImple implements AnswerService {

    private final AnswerMapper answerMapper;

    @Override
    public AnswerVO getAnswerByAnswerId(Long answerid) {
        return answerMapper.readAnswerByAnswerId(answerid);
    }

    @Override
    public List<AnswerVO> getAnswersByQuestionId(Long questionid) {
        return answerMapper.readAnswerByQuestionId(questionid);
    }

}
