package naver.mail.g6g6g63216.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service 
public class MailService { 

    @Autowired 
    private JavaMailSender mailSender ; 
    /*
    public void sendMail ( Mail mail ) { 
    } 
    */

    public void sendPMChange ( String receiver, double oldPm100, double newPm100 ) {
    	;
    }
    public void sendMail ( String receiverEmail, String title, String content) { 
        MimeMessage msg = mailSender.createMimeMessage(); 

        try { 
            /* 
             * 1. title 
             */ 
            msg.setSubject(title); 
            /* 
             * 2. content 
             */ 
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8"); 
            helper.setSubject(title); 
            helper.setText(content, true); 
            helper.setTo(new InternetAddress(receiverEmail, "서군님", "utf-8")); 

            /* 
            MimeMultipart mmp = new MimeMultipart(); 

            MimeBodyPart body = new MimeBodyPart(); 
            body.setContent(content, "text/html;charset=UTF-8"); 

            mmp.addBodyPart(body); 
            msg.setContent(mmp); 
            msg.addRecipient(RecipientType.TO, new InternetAddress(receiverEmail)); 
             */ 

            mailSender.send(msg); 
            System.out.println("OK!!"); 
        } catch (MessagingException e) { 
            e.printStackTrace(); 
        } catch (UnsupportedEncodingException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 

    } 
}