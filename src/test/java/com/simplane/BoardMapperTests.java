package com.simplane;

import com.simplane.domain.BoardVO;
import com.simplane.mapper.BoardMapper;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

    @Autowired
    private BoardMapper mapper;

    @Test
    public void testRead() {
        log.info("-----------------------------");
        log.info(mapper.read(1L));
    }

    @Test
    public void testReadAll() {
        List<BoardVO> list = mapper.readAll();

        for(BoardVO vo : list) {
            log.info(vo);
        }
    }

}
