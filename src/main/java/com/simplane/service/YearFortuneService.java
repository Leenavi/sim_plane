package com.simplane.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplane.domain.YearFortuneVO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service // 이 클래스가 Spring의 Service 컴포넌트임을 나타냄 (비즈니스 로직 처리 담당)
public class YearFortuneService {

    // 띠 이름(String)을 키로, 그에 해당하는 FortuneVO 객체를 값으로 가지는 Map (운세 저장소)
    private Map<String, YearFortuneVO> fortuneMap;

    // Spring이 이 클래스를 초기화할 때 자동으로 실행되는 메서드 (JSON 파일을 한 번만 로딩)
    @PostConstruct
    public void init() throws IOException {
        // JSON 파싱을 위한 Jackson 라이브러리의 ObjectMapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();

        // resources 폴더 내에 있는 yearFortune2025.json 파일을 읽기 위한 입력 스트림 생성
        InputStream is = new ClassPathResource("yearFortune2025.json").getInputStream();

        // JSON의 구조를 Map<String, FortuneVO>로 지정
        TypeReference<Map<String, YearFortuneVO>> typeRef = new TypeReference<>() {};

        // JSON 파일을 읽어와서 Map<String, FortuneVO> 형식으로 파싱한 뒤 fortuneMap에 저장
        fortuneMap = mapper.readValue(is, typeRef);
    }

    // 특정 띠 이름(예: "소")을 받아 해당 띠의 운세 정보(FortuneVO)를 반환하는 메서드
    public YearFortuneVO getFortune(String zodiacName) {
        return fortuneMap.get(zodiacName);
    }
}
