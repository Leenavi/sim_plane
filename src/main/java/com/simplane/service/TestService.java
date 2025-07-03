package com.simplane.service;

import com.simplane.domain.TestVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TestService {

    List<TestVO> getAllTests();


    void saveTestFromRequest(HttpServletRequest request);

    void deleteTestById(int testid);
}
