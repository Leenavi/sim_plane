package com.simplane.controller;

import com.simplane.domain.AnswerVO;
import com.simplane.domain.QuestionVO;
import com.simplane.domain.ResultVO;
import com.simplane.domain.TestVO;
import com.simplane.dto.AnswerSubmitDTO;
import com.simplane.dto.QuestionDTO;
import com.simplane.mapper.ResultMapper;
import com.simplane.service.AnswerService;
import com.simplane.service.QuestionService;
import com.simplane.service.TestService;
import com.simplane.service.TestServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
@Log4j
public class TestController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ResultMapper resultMapper;
    private final TestService testService;

    @GetMapping("/question")
    public ResponseEntity<QuestionDTO> getQuestion(
            @RequestParam Long testid,
            @RequestParam int step) {

        QuestionVO question = questionService.getQuestionByIndex(testid, step);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        List<AnswerVO> answers = answerService.getAnswersByQuestionId(question.getQuestionid());
        boolean hasNext = questionService.hasNextQuestion(testid, step);

        QuestionDTO dto = new QuestionDTO(question.getQuestion(), answers, hasNext, step);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/answer")
    public ResponseEntity<Void> submitAnswer(
            @RequestBody AnswerSubmitDTO dto,
            HttpSession session) {

        log.info("======================================================"+dto.getAnswerid());

        AnswerVO answer = answerService.getAnswerByAnswerId(dto.getAnswerid());
        if (answer == null) {
            return ResponseEntity.notFound().build();
        }

        // 세션에 점수 누적
        Integer currentScore = (Integer) session.getAttribute("score");
        if (currentScore == null) currentScore = 0;

        currentScore += answer.getScore();
        session.setAttribute("score", currentScore);

        log.info("======================================================"+dto.getAnswerid());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/result")
    public ResponseEntity<ResultVO> getResult(
            @RequestParam int testid,
            HttpSession session) {

        Integer score = (Integer) session.getAttribute("score");
        if (score == null) score = 0;

        ResultVO result = resultMapper.readResultByScore(testid, score);
        session.removeAttribute("score"); // 테스트 종료 후 세션 정리

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 반환
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("/list")
    public String showTestList(Model model) {
        List<TestVO> testList = testService.getAllTests();
        model.addAttribute("testList", testList);
        return "test/testlist";  // /WEB-INF/views/test/testlist.jsp
    }

    @GetMapping("/start")
    public String startTest(@RequestParam("testid") Long testid, Model model) {
        model.addAttribute("testid", testid);
        return "test/test";  // /WEB-INF/views/test/test.jsp
    }

}
