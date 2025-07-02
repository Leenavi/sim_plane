package com.simplane.controller;

import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Log4j
@WebAppConfiguration
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardControllerTest extends TestCase {

    @Autowired  // 웹 관련 빈(생성된 객체) 관리
    private WebApplicationContext wac;

    //서버를 실행하지 않고도 http 요청과 응답을 시뮬레이션하기 위한 도구
    private MockMvc mockMvc;

    //서버를 실행하지 않고도 mvc 애플리케이션에서 통합 테스트 수행, 실제 서버를 실행하지 않고
    //컨트롤러 동작 테스트 가능
    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testget() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders
                        .get("/board/get")
                        .param("boardid", "1"))
                .andReturn()
                .getModelAndView()
                .getModelMap());
    }

}