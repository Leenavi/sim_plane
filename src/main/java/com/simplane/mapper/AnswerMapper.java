package com.simplane.mapper;

import com.simplane.domain.AnswerVO;

import java.util.List;

public interface AnswerMapper {

    List<AnswerVO> readAnswerByQuestionId(Long questionId);

    AnswerVO readAnswerByAnswerId(Long answerId);
}
