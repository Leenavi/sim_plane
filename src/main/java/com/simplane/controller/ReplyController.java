package com.simplane.controller;

import com.simplane.domain.Criteria;
import com.simplane.domain.ReplyVO;
import com.simplane.domain.ReplyPageDTO;
import com.simplane.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping(value = "/new")
    public ResponseEntity<String> create(@RequestBody ReplyVO vo){ //@RequestBody -> json형식으로 받겠다
        log.info("ReplyVO : " + vo);

        int insertCount = replyService.register(vo); //댓글 저장 시도

        if(insertCount == 1){
            return new ResponseEntity<>("success", HttpStatus.OK); //200응답
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500에러
        }

    }

    // 댓글 목록을 페이지 별로 가져오기 -> 특정 게시글(bno)의 댓글들을 지정한 페이지(page)에 맞춰 JSON 형식으로 가져오는 GET API
    @GetMapping(value = "/pages/{testid}/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE) // 결과를 JSON 형식으로 응답
    public ResponseEntity<ReplyPageDTO> getList( //
                                                 @PathVariable("testid") Long testid,
                                                 @PathVariable("page") int page
    ){
        log.info("getLlist.....");

        Criteria cri = new Criteria(page, 10);

        return new ResponseEntity<>(replyService.getListPage(cri, testid), HttpStatus.OK);
    }

    // 댓글 단건(건별) 데이타 가져오기
    @GetMapping(value = "/{replyid}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReplyVO> get(@PathVariable("replyid") Long replyid){
        log.info("get....." + replyid);

        return new ResponseEntity<ReplyVO>(replyService.get(replyid), HttpStatus.OK);
    }

    // 댓글 데이터 수정
    //@PreAuthorize("principal.username == #vo.replyer")
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
            value = "/{replyid}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("replyid") Long replyid){

        vo.setReplyid(replyid);
        log.info("modify....." + replyid);

        return replyService.modify(vo) == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
                : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{replyid}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(/*@RequestBody ReplyVO vo, */@PathVariable("replyid")Long replyid){
        log.info("remove : " + replyid);

        //replyid로 댓글 삭제, 댓글삭제시 replyid만 필요함
        return replyService.remove(replyid) == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}