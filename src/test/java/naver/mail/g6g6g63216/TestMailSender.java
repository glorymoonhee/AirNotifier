package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import naver.mail.g6g6g63216.service.UserService;
import naver.mail.g6g6g63216.vo.PmData;
import naver.mail.g6g6g63216.vo.StationVO;

public class TestMailSender {

	@Test
	public void test() {
		UserService us = new UserService();
//		us.sendEmail("g6g6g63216@naver.com", "<h3>���� ���� ���ø��Դϴ�.</h3>");
		
		StationVO s = new StationVO("수내동", "경기도 수내동", "23.222", "123.111");
		
		List<PmData> data = new ArrayList<PmData>();
		data.add(new PmData("56", 2, "32", 1, "2016-07-19 11:00:00"));
		us.sendPmNotification("http://localhost:8080", "g6g6g63216@naver.com", s,  data.get(0));
	}

}
