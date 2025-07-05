package com.simplane.controller;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import com.simplane.domain.ImgPathVO;
import com.simplane.domain.PageDTO;
import com.simplane.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList; // 추가
import java.util.List;
import java.util.stream.Collectors; // 추가

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

        log.info("### 전체 게시글 수: " + total);
    }

    //단 건 읽어오기
    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam Long boardid, Criteria cri, Model model) {
        log.info("get......modify..........");

        model.addAttribute("board", service.get(boardid));
        model.addAttribute("cri", cri);
        model.addAttribute("images", service.getImageList(boardid)); // 현재 연결된 이미지 목록
    }

    // 데이터 수정
    @PostMapping("/modify")
    public String modify(
            BoardVO board,
            @ModelAttribute("cri") Criteria cri,
            @RequestParam(value = "imagePaths", required = false) List<String> imagePaths, // JSP에서 보낸 모든 이미지 경로
            RedirectAttributes rttr) {

        log.info("modify..... 게시글 수정 처리");
        log.info("boardid: " + board.getBoardid());
        log.info("title: " + board.getTitle());
        log.info("content: " + board.getContent());
        log.info("전달받은 imagePaths: " + imagePaths);

        // 서비스 계층으로 boardVO와 이미지 경로 리스트를 함께 전달
        if(service.modifyBoardAndImages(board, imagePaths)) {
            rttr.addFlashAttribute("result", "수정 되었습니다.");
        } else {
            rttr.addFlashAttribute("result", "수정 실패했습니다."); // 수정 실패 메시지 추가
        }

        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());

        log.info("modify..... 수정 처리 완료");

        return "redirect:/board/list";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO,
                           @RequestParam(value = "imagePaths", required = false) List<String> imagePaths,
                           RedirectAttributes rttr) {
        log.info("register.....");
        // 먼저 게시글 등록
        service.register(boardVO);
        log.info("register boardid: " + boardVO.getBoardid()); // 등록 후 boardid가 설정되었는지 확인

        if(imagePaths != null && !imagePaths.isEmpty()){
            // ImgPathVO 리스트 생성
            List<ImgPathVO> imgList = imagePaths.stream()
                    .map(path -> new ImgPathVO(null, boardVO.getBoardid(), path))
                    .collect(Collectors.toList());
            service.addImages(imgList); // 일괄 추가 메서드 호출
        }

        rttr.addFlashAttribute("result", boardVO.getBoardid());
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("boardid") Long boardid, @ModelAttribute("cri") Criteria cri ,
                         RedirectAttributes rttr) {
        log.info("remove..." + boardid);

        // 게시글 삭제 시 관련 이미지도 함께 삭제하도록 서비스 호출
        if (service.removeBoardAndImages(boardid)) {
            rttr.addFlashAttribute("result", "삭제를 성공했습니다.");
        } else {
            rttr.addFlashAttribute("result", "삭제 실패했습니다.");
        }
        // 삭제 후 목록 페이지로 리다이렉트 시 검색 조건 유지
        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());
        return "redirect:/board/list";
    }

    @GetMapping("/register")
    public void register(){
    }

}