package com.simplane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fortune")
public class FortuneController {

    //운세 카테고리 리스트
    @GetMapping("/list")
    public String showFortunelist() {
        return "fortune/fortunelist"; // → /WEB-INF/views/fortune/category.jsp
    }

    // 띠별 운세 입력 페이지
    @GetMapping("/year")
    public String showZodiacInputPage() {
        return "fortune/yearfortune"; // → /WEB-INF/views/fortune/zodiac.jsp
    }
}