package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import naver.mail.g6g6g63216.dao.CachePmDao;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
public class PmDaoTest {

	@Autowired
	CachePmDao dao ;
	
	@Test
	public void test() {
		// CachePmDao dao = new CachePmDao();
		
		assertFalse ( dao.pmDataChanged("서울", "dldi3kd983isijjf23") );
	}

}
