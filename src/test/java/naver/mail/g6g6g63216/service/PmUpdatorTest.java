package naver.mail.g6g6g63216.service;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class PmUpdatorTest {

	@Autowired
	PmUpdator updator ;
	
	@Test
	public void test() {
		String oldXml = xml ("old.xml");
		String newXml = xml ( "new.xml");
		
		updator.startNotification(oldXml, newXml);
		
		System.out.println("do nothing");
	}

	private String xml(String fname) {
		InputStream in = PmUpdatorTest.class.getResourceAsStream(fname);
		Scanner sc = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		while ( sc.hasNextLine() ) {
			String line = sc.nextLine();
			sb.append(line );
		}
		sc.close();
		return sb.toString();
	}

}
