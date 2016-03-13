package naver.mail.g6g6g63216.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import naver.mail.g6g6g63216.dao.IUserDao;
import naver.mail.g6g6g63216.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private IUserDao userDao ;
	
	public UserVO insertUser ( String email, String password) {
		UserVO user = userDao.insertUser(email, password);
		// FIXME ���� ������� ������ �����ϴµ� ���� �������� ������ �ö����� block �Ǿ ������ ������ �ؾ����� ������.
		//       �񵿱� ������� ���� �����ϴ� �ڵ带 ���ֿ� �־���� �մϴ�.
		sendEmail ( user.getEmail(), "<h3>hi</h3>" );
		
		return user;
	}
	/**
	 * ȸ�� ���� �˸��� ���� �߼��մϴ�.
	 * @param email
	 */
	public void sendEmail(String email, String htmlTemplate) {
		// TODO Auto-generated method stub
		 final String gmailAccount = "no.rep.for.javatuition@gmail.com";
	        final String password = "s123456789$";

	        String receiver = "happy_medium@naver.com";
	        String title = "�׽�Ʈ ���� �����մϴ�.";

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        javax.mail.Session session = Session.getDefaultInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(gmailAccount, password);
	            }
	        });

	        try {
	            MimeMessage msg = new MimeMessage(session);
	            msg.setSubject(title);
//	            msg.setText(body);

	            MimeBodyPart html = new MimeBodyPart();
	            html.setContent(htmlTemplate, "text/html;charset=UTF-8");

	            MimeMultipart bodyPart = new MimeMultipart();
	            bodyPart.addBodyPart(html);

	            msg.setContent(bodyPart);

	            msg.setFrom(new InternetAddress(gmailAccount));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("happy_medium@naver.com"));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	            Transport.send(msg);
//	            
//	            Transport transport= session.getTransport("smtps");
//	            transport.sendMessage(msg, msg.getAllRecipients());
//	            transport.close();

	        } catch ( MessagingException e) {
//	            throw e;
	            e.printStackTrace();
	        } 
	}
	public UserVO findUser(String email, String pass) {
		UserVO user = userDao.findUser(email,pass);
		
		return user;
	}
}
