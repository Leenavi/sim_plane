package com.simplane.service;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import com.simplane.domain.ImgPathVO;

import java.util.List;

public interface BoardService {

    public BoardVO get(Long boardid);

    public List<BoardVO> getAll(Criteria cri);

    public int getTotal(Criteria cri);

    // 게시글 수정
    public boolean modify(BoardVO board);

    public void register(BoardVO board); // 게시글 등록

    public boolean remove(Long boardid);

    // 게시글 ID로 이미지 리스트 가져오기
    List<ImgPathVO> getImageList(Long boardid);

    // 게시글 삭제 시 이미지도 같이 삭제
    void deleteImg(Long boardid);

    // 이미지 등록
    void createImg(ImgPathVO img);
}
