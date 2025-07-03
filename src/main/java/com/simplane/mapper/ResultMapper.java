package com.simplane.mapper;

import com.simplane.domain.ResultVO;
import org.apache.ibatis.annotations.Param;

public interface ResultMapper {

    ResultVO readResultByScore(@Param("testid") int testid, @Param("totalScore") int totalscore);


    // 결과 삽입
    void insertResult(ResultVO result);

    void deleteResultsByTestId(int testid);
}
