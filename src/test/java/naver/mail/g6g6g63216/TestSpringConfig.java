package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import naver.mail.g6g6g63216.dao.PmDao;
import naver.mail.g6g6g63216.vo.PlaceVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestSpringConfig {
	
	@Autowired
	private ApplicationContext ctx ;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() {
		PmDao pmDao = ctx.getBean(PmDao.class);
		List<PlaceVO> fourPlaces = pmDao.findAllPlaces();
		assertEquals ( 4, fourPlaces.size() );
	}

}
