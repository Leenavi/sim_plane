package com.simplane.controller;

import com.simplane.domain.MemberVO;
import com.simplane.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@Log4j
public class MemberController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 페이지 요청 처리
     * URL: GET /signup
     *
     * @return 회원가입 JSP 뷰 이름
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "/board/signup";  // signup.jsp 호출
    }

    /**
     * 회원가입 폼 제출 처리
     * URL: POST /signup
     * 파라미터: userid, password, name, birthdate, sex
     *
     * @param userid    아이디
     * @param password  비밀번호 (평문, 서비스에서 암호화 처리)
     * @param name      사용자 이름
     * @param birthdate 생년월일 ("yyyy-MM-dd" 형식)
     * @param sex       성별 (int)
     * @param model     뷰 전달용 모델
     * @return 성공시 로그인 페이지 리다이렉트, 실패시 다시 회원가입 폼
     */
    @PostMapping("/signup")
    public String processSignup(@RequestParam String userid,
                                @RequestParam String password,
                                @RequestParam String name,
                                @RequestParam String birthdate,
                                @RequestParam int sex,
                                Model model) {

        log.info("회원가입 시도: " + userid + ", " + name + ", " + birthdate + ", 성별=" + sex);

        // 1. 아이디 중복 체크
        if (userService.getUserById(userid) != null) {
            model.addAttribute("errorMsg", "이미 존재하는 아이디입니다.");
            return "board/signup";  // 중복 아이디면 가입 폼 다시 출력
        }

        Date sqlBirthdate;
        try {
            sqlBirthdate = Date.valueOf(birthdate); // yyyy-MM-dd 형식이어야 함
            LocalDate parsedDate = sqlBirthdate.toLocalDate();
            if (parsedDate.isAfter(LocalDate.now())) {
                model.addAttribute("errorMsg", "생년월일은 미래일 수 없습니다.");
                return "board/signup";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", "생년월일 형식이 올바르지 않습니다.");
            return "board/signup";
        }

        // 3. MemberVO 객체 생성 및 데이터 세팅
        MemberVO member = new MemberVO();
        member.setUserid(userid);
        member.setPassword(password);  // 암호화는 서비스에서 처리
        member.setName(name);
        member.setBirthdate(sqlBirthdate);
        member.setSex(sex);

        // 4. 회원가입 서비스 호출
        boolean success = userService.registerUser(member);

        if (success) {
            // 가입 성공 시 로그인 페이지로 리다이렉트 (joined=success 쿼리 파라미터 포함)
            return "redirect:/login";
        } else {
            // 가입 실패 시 에러 메시지와 함께 다시 가입 폼 출력
            model.addAttribute("errorMsg", "회원가입 실패");
            return "/board/signup";
        }
    }

    /**
     * 아이디 중복 검사 AJAX 요청 처리
     * URL: GET /checkUsername?username=...
     *
     * @param userid 검사할 아이디
     * @return "available" or "unavailable" 문자열 반환
     */
    @GetMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(@RequestParam String userid) {
        log.info("중복체크 요청: " + userid);  // ← 로그 찍어서 확인
        boolean exists = userService.getUserById(userid) != null;
        return exists ? "unavailable" : "available";
    }

}