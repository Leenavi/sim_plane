package com.simplane.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j
public class LoginController {

    //로그인 화면 보여줄 때
    @GetMapping(value = "/customlogin")
    public String customlogin() {
        return "/board/customlogin";
    }

    //로그인 성공 시 갈 메인 페이지
    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }
}