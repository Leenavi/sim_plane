package com.simplane.service;

import com.simplane.domain.TestVO;
import com.simplane.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    @Override
    public List<TestVO> getAllTests() {
        return testMapper.readAllTests();
    }
}
