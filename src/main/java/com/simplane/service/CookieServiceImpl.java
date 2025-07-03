package com.simplane.service;

import com.simplane.domain.CookieVO;
import com.simplane.mapper.CookieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Log4j
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieMapper cookieMapper;

    @Override
    public CookieVO getRandomCookie(){
        // 1. CookieMapper에게 모든 명언을 데이터베이스에서 가져와줘 전달
        List<CookieVO> allCookies = cookieMapper.findAllCookies();
        // 2. 데이터베이스에 명언이 없다면 실행
        if (allCookies.isEmpty()){
            return null; //아무것도 돌려줄께 없음
        }
        // 3. 명언이 있다면, 그 중에서 랜덤 선택
        Random random = new Random();
        int randomIndex = random.nextInt(allCookies.size());
        // 4. 랜덤으로 뽑은 숫자에 해당하는 명언을 목록에서 꺼내 돌려준다
        return allCookies.get(randomIndex);
    }
}
