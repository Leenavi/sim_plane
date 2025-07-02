package com.simplane.controller;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import com.simplane.domain.PageDTO;
import com.simplane.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j
public class BoardController {

    private final BoardService service;

    //목록 읽어오기
    @GetMapping("/list")
    public void list(Criteria cri, Model model) {
        log.info("list================");

        List<BoardVO> list = service.getAll(cri);
        model.addAttribute("list", list);

        int total = service.getTotal(cri);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }
    
    //단 건 읽어오기
    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam Long boardid, Criteria cri, Model model) {
        log.info("get......modify..........");

        model.addAttribute("board", service.get(boardid));
        model.addAttribute("cri", cri);
    }

    // 데이터 수정
    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("modify.....2");
        log.info("service is null? " + (service == null));

        log.info("boardid: " + board.getBoardid());
        log.info("title: " + board.getTitle());
        log.info("content: " + board.getContent());


        if(service.modify(board)) {
            rttr.addFlashAttribute("result", "수정 되었습니다.");
        }
        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());

        log.info("modify.....3");

        return "redirect:/board/list";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO, RedirectAttributes rttr) {
        log.info("register.....");
        service.register(boardVO);
        rttr.addFlashAttribute("result", boardVO.getBoardid());
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("boardid") Long boardid, @ModelAttribute("cri") Criteria cri ,
                         RedirectAttributes rttr) {
        log.info("remove..." + boardid);
//        log.info("remove...writer..." + writer);

        if (service.remove(boardid)) {
            rttr.addFlashAttribute("result", "삭제를 성공했습니다.");
        }
        return "redirect:/board/list";
    }

    @GetMapping("/register")
    public void register(){
    }

}
