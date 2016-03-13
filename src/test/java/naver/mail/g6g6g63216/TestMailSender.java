package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import org.junit.Test;

import naver.mail.g6g6g63216.service.UserService;

public class TestMailSender {

	@Test
	public void test() {
		UserService us = new UserService();
		us.sendEmail("g6g6g63216@naver.com", "<h3>메일 본문 템플릿입니다.</h3>");
	}

}
