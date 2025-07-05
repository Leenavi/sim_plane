package com.simplane.service;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import com.simplane.domain.ImgPathVO;
import com.simplane.mapper.BoardMapper; // BoardMapper만 주입
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper; // ImgPathMapper는 필요 없음

    @Override
    @Transactional
    public void register(BoardVO board) {
        log.info("register......" + board);
        boardMapper.createSelectKey(board); // createSelectKey 사용
    }

    @Override
    public BoardVO get(Long boardid) {
        log.info("get......" + boardid);
        return boardMapper.read(boardid);
    }

    @Override
    @Transactional
    public boolean modifyBoardAndImages(BoardVO board, List<String> imagePaths) {
        log.info("modifyBoardAndImages......" + board);
        log.info("전달받은 imagePaths (최종 저장될): " + imagePaths); // 클라이언트에서 넘어온 최종 경로들

        // 1. 게시글 내용 수정
        boolean modifyResult = boardMapper.update(board) == 1;
        log.info("게시글 내용 수정 결과: " + modifyResult);

        if (modifyResult) {
            // 2. 해당 게시글의 기존 이미지 정보 삭제
            log.info(board.getBoardid() + "번 게시글의 모든 이미지 삭제 시작");
            boardMapper.deleteImg(board.getBoardid()); // 이 메서드가 실제 DB에 DELETE 쿼리를 날리는지 확인
            log.info(board.getBoardid() + "번 게시글의 기존 이미지 삭제 완료");

            // 3. 새로 전송된 이미지 경로들을 DB에 다시 삽입
            if (imagePaths != null && !imagePaths.isEmpty()) {
                log.info(imagePaths.size() + "개의 이미지 경로를 새로 삽입 시작");
                for (String path : imagePaths) {
                    ImgPathVO img = new ImgPathVO(null, board.getBoardid(), path);
                    boardMapper.createImg(img);
                    log.info("이미지 삽입: " + path);
                }
                log.info("새 이미지 삽입 완료");
            } else {
                log.info("전달받은 imagePaths가 없거나 비어있으므로 새 이미지를 삽입하지 않음.");
            }
        }
        return modifyResult;
    }

    @Override
    @Transactional
    public boolean removeBoardAndImages(Long boardid) {
        log.info("removeBoardAndImages......" + boardid);
        // 1. 게시글에 연결된 모든 이미지 정보 삭제 (BoardMapper의 deleteImg 사용)
        boardMapper.deleteImg(boardid);
        // 2. 게시글 삭제
        return boardMapper.delete(boardid) == 1;
    }

    @Override
    public List<BoardVO> getAll(Criteria cri) {
        log.info("getAll......" + cri);
        return boardMapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        log.info("getTotal......");
        return boardMapper.getTotalCount(cri);
    }

    @Override
    public void createImg(ImgPathVO img) {
        log.info("createImg......" + img);
        boardMapper.createImg(img);
    }

    @Override
    public void addImages(List<ImgPathVO> images) {
        log.info("addImages......" + images.size() + " images");
        if (images != null && !images.isEmpty()) {
            for (ImgPathVO img : images) {
                boardMapper.createImg(img); // BoardMapper의 createImg를 반복 호출
            }
        }
    }

    @Override
    public List<ImgPathVO> getImageList(Long boardid) {
        log.info("getImageList......" + boardid);
        return boardMapper.getImageList(boardid);
    }

    @Override
    public void deleteImagesByBoardId(Long boardid) {
        log.info("deleteImagesByBoardId......" + boardid);
        boardMapper.deleteImg(boardid);
    }
}