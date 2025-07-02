package com.simplane.service;


import com.simplane.domain.Criteria;
import com.simplane.domain.ReplyVO;
import com.simplane.domain.ReplyPageDTO;

import java.util.List;

public interface ReplyService {

    public int register(ReplyVO vo);

    //댓글 단건 조회
    public ReplyVO get(Long replyid);

    //댓글 목록 조회 + 페이징
    public ReplyPageDTO getListPage(Criteria cri, Long testid);

    //댓글 수정
    public int modify(ReplyVO vo);

    public int remove(Long replyid);

    List<ReplyVO> getList(Criteria cri, Long testid);
}
