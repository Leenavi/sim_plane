package com.simplane.mapper;

import com.simplane.domain.Criteria;
import com.simplane.domain.ReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {

    public int create(ReplyVO vo); //댓글 등록

    public ReplyVO read(Long replyid); //댓글 조회

    public int update(ReplyVO reply); //댓글 수정

    //댓글 페이징 처리
    public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("testid") Long testid);

    public int delete(Long replyid); // 댓글 삭제

    public int getCountByTestid(Long testid);

}
