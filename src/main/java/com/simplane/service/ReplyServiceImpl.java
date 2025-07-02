package com.simplane.service;

import com.simplane.domain.Criteria;
import com.simplane.domain.ReplyVO;
import com.simplane.domain.ReplyPageDTO;
import com.simplane.mapper.ReplyMapper;
import com.simplane.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper mapper;
    private final TestMapper testMapper;


    @Override
    public int register(ReplyVO vo) {

        return mapper.create(vo);
    }

    @Override
    public ReplyVO get(Long replyid) {
        return mapper.read(replyid);
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long testid) {
        return new ReplyPageDTO(mapper.getCountByTestid(testid), mapper.getListWithPaging(cri, testid));
    }

    @Override
    public int modify(ReplyVO vo) {
        return mapper.update(vo);
    }

    @Override
    public int remove(Long replyid) {
        return mapper.delete(replyid);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long testid) {
        return mapper.getListWithPaging(cri, testid);
    }
}
