package com.simplane.service;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;

import java.util.List;

public interface BoardService {

    public BoardVO get(Long boardid);

    public List<BoardVO> getAll(Criteria cri);

    public int getTotal(Criteria cri);

    // 게시글 수정
    public boolean modify(BoardVO board);

    public void register(BoardVO board); // 게시글 등록

    public boolean remove(Long boardid);
}
