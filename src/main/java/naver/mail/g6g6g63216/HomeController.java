package naver.mail.g6g6g63216;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Connection.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.IPmDao;
import naver.mail.g6g6g63216.service.UserService;
import naver.mail.g6g6g63216.vo.PmData;
import naver.mail.g6g6g63216.vo.UserVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
	@Autowired
	@Qualifier("cachedPM")
	private IPmDao dao ;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		readPmData ( model );
		/*
		 * "/WEB-INF/views/" + home + ".jsp"
		 */
		return "home";
	}
     
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model){
		
	     return "join";
	}
	
	
	@RequestMapping(value = "/doJoin", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public @ResponseBody String doJoin(Model model, HttpServletRequest req){
	     String email = req.getParameter("email");
	     String pass = req.getParameter("password");
	     
	     UserVO user = userService.insertUser ( email, pass  );
	     System.out.println("����: " + user.toString());
	     
	     // json �������� ������ ������ �մϴ�. { "success" : true}
	     return "{\"success\": true}";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(Model model){
		
		return "login";
	}
	
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public @ResponseBody String dologin(Model model, HttpServletRequest req, HttpSession session){
	     String email = req.getParameter("email");
	     String pass = req.getParameter("password");
	     
	     UserVO user = userService.findUser(email,pass);
	     System.out.println("�α�: " + user.toString());
	     
	     /*
	      * 1. ������ ����� ����������.
	      */
	     session.setAttribute("user", user);
	     
	     return "{\"success\": true}";
	}
	
	private List<String> sidoNames = Arrays.asList("����", "���", "����", "����", "����", "�泲" , "���", "�泲", "���");
	private void readPmData(Model model) {
		// TODO Auto-generated method stub
		
		List<PmData> list = new ArrayList<PmData>();
		Map<PmData, String> sidoMap = new HashMap<PmData, String>();
		for(String sido: sidoNames){
			PmData data = dao.getAvrData(sido);
			list.add(data);
			sidoMap.put(data, sido);
		}
		
		model.addAttribute("sidoData", list);
		model.addAttribute("sidoMap", sidoMap);
		
//		PmData seoul = dao.getAvrData("����");
//		model.addAttribute("seoul",seoul);
//		
//		PmData gyeonggi = dao.getAvrData("���");
//		model.addAttribute("gyeonggi",gyeonggi);
//		
//		PmData gangwon = dao.getAvrData("����");
//		model.addAttribute("gangwon", gangwon);
	}
}
