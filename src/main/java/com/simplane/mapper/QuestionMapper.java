package com.simplane.mapper;

import com.simplane.domain.QuestionVO;
import com.simplane.domain.TestVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {

    List<QuestionVO> readQuestionByTestId();

    QuestionVO readQuestionByIndex(@Param("testid") Long testid,@Param("q_index") int q_index);

    boolean existsQuestionAfter(@Param("testid") Long testid, @Param("q_index") int currentIndex);

}
