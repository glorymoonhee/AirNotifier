package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import naver.mail.g6g6g63216.service.MailService;
import naver.mail.g6g6g63216.service.UserService;
import naver.mail.g6g6g63216.vo.PmData;
import naver.mail.g6g6g63216.vo.StationVO;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
public class TestMailSender {

    @Autowired MailService service; 
    @Value("${mail.host}") String host ; 
    
	@Test
	public void test() {
		/*
		UserService us = new UserService();
		
		StationVO s = new StationVO("수내동", "경기도 수내동", "23.222", "123.111");
		
		List<PmData> data = new ArrayList<PmData>();
		data.add(new PmData("56", 2, "32", 1, "2016-07-19 11:00:00"));
		us.sendPmNotification("http://localhost:8080", "g6g6g63216@naver.com", s,  data.get(0));
		*/
	}
	
	@Test 
    public void test_spring_mailing() throws MessagingException { 
        System.out.println("HOST : " + host); 
//        File f = new File ( "src/main/template/welcome.html"); 
//        System.out.println(f.exists()); 
        service.sendMail("g6g6g63216@naver.com", "잘 갈까?", "<h3>다시보내자!</h3>"); 
    } 

}
