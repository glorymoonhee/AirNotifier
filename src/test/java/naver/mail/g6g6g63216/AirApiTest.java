package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import naver.mail.g6g6g63216.dao.PmDao;
import naver.mail.g6g6g63216.vo.PmData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AirApiTest {

	@Autowired
	ApplicationContext ctx;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		PmDao dao = ctx.getBean(PmDao.class);
		List<PmData> data = dao.queryByStation("ÀºÆò±¸");
		assertTrue ( data.size() > 0 );
		System.out.println(org.springframework.core.SpringVersion.getVersion() );
	}
	
	
}
