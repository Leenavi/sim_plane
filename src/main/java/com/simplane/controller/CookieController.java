package com.simplane.controller;

import com.simplane.domain.CookieVO;
import com.simplane.service.CookieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie")
@Log4j
public class CookieController {

    private final CookieService cookieService;

    // 모달창에서 명언 띄울 때 실행
    @GetMapping(value = "/random")
    @ResponseBody
    public CookieVO getRandomCookie() {

        CookieVO cookie = cookieService.getRandomCookie();

        return cookie;
    }

    // home.jsp에서 모달 내용을 불러올 때
    @GetMapping(value = "/modalContent")
    public String getModelContent() {
        return "forward:/WEB-INF/views/cookie/cookie.jsp";
    }
}
