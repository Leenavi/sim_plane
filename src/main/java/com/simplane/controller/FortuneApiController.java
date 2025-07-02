package com.simplane.controller;

import com.simplane.domain.YearFortuneVO;
import com.simplane.service.YearFortuneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fortune/api")
public class FortuneApiController {

    private final YearFortuneService yearFortuneService;

    public FortuneApiController(YearFortuneService yearFortuneService) {
        this.yearFortuneService = yearFortuneService;
    }

    // 띠별 운세 결과 (JSON 반환 API)
    @GetMapping("/year")
    public ResponseEntity<YearFortuneVO> getYearFortune(@RequestParam Integer birthYear) {


        System.out.println("birthYear: " + birthYear);

        if (birthYear == null) {
            return ResponseEntity.badRequest().build();
        }


        String[] years = {"원숭이", "닭", "개", "돼지", "쥐", "소", "호랑이", "토끼", "용", "뱀", "말", "양"};
        String year = years[birthYear % 12];
        YearFortuneVO fortune = yearFortuneService.getFortune(year);
        return ResponseEntity.ok(fortune);
    }
}
