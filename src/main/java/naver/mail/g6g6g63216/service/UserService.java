package naver.mail.g6g6g63216.service;

import java.util.List;
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
import naver.mail.g6g6g63216.vo.PmData;
import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private IUserDao userDao ;
	
	public UserVO insertUser ( String email, String password) {
		UserVO user = userDao.insertUser(email, password);
		// FIXME 동기 방식으로 메일을 전송하는데 메일 서버에서 응답이 올때까지 block 되어서 웹서버 성능이 극악으로 떨어짐.
		//       비동기 방식으로 메일 전송하는 코드를 나주엥 넣어줘야 합니다.
//		sendEmail ( user.getEmail(), "<h3>수내동 11시</h3>" );
		
		return user;
	}
	
	public int UpdatetUser(String email, String pass) {
		
		int n = userDao.updateUser(email,pass);
		return n;
	}
	
	/**
	 *  수신자 : email
	 *  장소(station) : 
	 *  시간 ( time ):
	 *  pmdata ( )
	 * 
	 *    userService.sendEmail( "ddd@naver.com", StationVO station, String time, List<Pmdata> data ) ;
	 */
	public void sendPmNotification (String host, String receiver, StationVO station, PmData data) {
		String template = "<h3><a href=\"{host}/air/pm/station?sido={sido}&name={station}\">{station} - {time}시 </a></h3><div>PM 10 : {pm100}</div><div>PM 2.5 : {pm025}</div>" ;
		
		
		String sido = station.getAddr().substring(0, 2);
		String html = template.replace("{station}", station.getName())
		                  .replace("{time}", data.getDataTime())
		                  .replace("{pm100}",data.getPm100())
		                  .replace("{pm025}",data.getPm025())
		                  .replace("{host}", host)
		                  .replace("{sido}", sido);
		sendEmail(receiver, "미세먼지 정보", html);
	}
	/**
	 * 회원 가입 알리는 메일 발송합니다.
	 * @param receiver
	 */
	public void sendEmail(String receiver, String title, String htmlTemplate) {
		// TODO Auto-generated method stub
		
		 final String gmailAccount = "no.rep.for.javatuition@gmail.com";
	        final String password = "s123456789$";

//	        String receiver = "happy_medium@naver.com";
//	        String title = "테스트 메일 전송합니다.";

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
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
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


	public void delete_Userstation(String station_name) {
		
		   System.out.println("Userservice" + station_name);
		userDao.deleteStation(station_name);
		
	}


}
