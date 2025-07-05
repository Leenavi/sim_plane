package com.simplane.service;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import com.simplane.domain.ImgPathVO;

import java.util.List;

public interface BoardService {

    void register(BoardVO board);

    BoardVO get(Long boardid);

    // 게시글과 이미지 함께 수정하는 메서드
    boolean modifyBoardAndImages(BoardVO board, List<String> imagePaths);

    // 게시글과 이미지 함께 삭제하는 메서드
    boolean removeBoardAndImages(Long boardid);

    List<BoardVO> getAll(Criteria cri);

    int getTotal(Criteria cri);

    // BoardMapper에 이미 존재하는 메서드를 활용
    void createImg(ImgPathVO img);

    // 이미지 리스트 등록 (BoardMapper의 createImg를 반복 호출)
    void addImages(List<ImgPathVO> images);

    // 특정 게시글의 이미지 목록 가져오기 (BoardMapper의 getImageList 활용)
    List<ImgPathVO> getImageList(Long boardid);

    // 게시글 삭제 시 이미지들 모두 삭제 (BoardMapper의 deleteImg 활용)
    void deleteImagesByBoardId(Long boardid);
}