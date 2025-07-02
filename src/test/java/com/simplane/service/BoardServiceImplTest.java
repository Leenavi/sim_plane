package com.simplane.service;

import com.simplane.domain.BoardVO;
import com.simplane.domain.Criteria;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceImplTest{

    @Autowired
    private BoardService service;

    @Test
    public void testGet() {
        System.out.println(service.get(2L));
    }

    @Test
    public void testGetAll() {
        service.getAll(new Criteria()).forEach(board -> log.info(board));
    }
}