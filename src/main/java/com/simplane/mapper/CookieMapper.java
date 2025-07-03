package com.simplane.mapper;

import java.util.List;
import com.simplane.domain.CookieVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CookieMapper {

    //여러개의 명언을 목록에 담아 읽어온다
    public List<CookieVO> findAllCookies();

}

