package com.simplane.service;

import com.simplane.domain.AnswerVO;

import java.util.List;

public interface AnswerService {
    AnswerVO getAnswerByAnswerId(Long answerid);

    List<AnswerVO> getAnswersByQuestionId(Long questionid);
}
