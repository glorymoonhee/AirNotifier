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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.APICallException;
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
	
	@RequestMapping(value = "/gmap", method = RequestMethod.GET)
	public String gmap(Model model){
		
	     return "gmap";
	}
	
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Model model){
		
	     return "info";
	}
	
	
	
	
	@RequestMapping(value = "/doJoin", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public @ResponseBody String doJoin(Model model, HttpServletRequest req) {
	     String email = req.getParameter("email");
	     String pass = req.getParameter("password");
	     
	     UserVO user = userService.insertUser ( email, pass  );
	     System.out.println("가입: " + user.toString());
	     
	     // json 포맷으로 응답을 보내야 합니다. { "success" : true}
	     return "{\"success\": true}";
	}
	
	
	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public @ResponseBody String doUpdate(Model model, HttpServletRequest req) {
	     String email = req.getParameter("email");
	     String pass = req.getParameter("password");
	     
	     int n = userService.UpdatetUser(email, pass);
	     System.out.println("개수:" + n);
	     
	     return  n == 1 ? "{\"success\": true}" :  "{\"success\": false}";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(Model model){
		
		return "login";
	}
	
	
	@RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String Graph(Model model){
		
		return "graph";
	}
	
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
    public String board(Model model){
		
		return "/board/listpost";
	}
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public @ResponseBody String dologin(Model model, HttpServletRequest req, HttpSession session){
	     String email = req.getParameter("email");
	     String pass = req.getParameter("password");
	     
	     UserVO user = userService.findUser(email,pass);
	     System.out.println("로긴: " + user.toString());
	     
	     /*
	      * 1. 세선에 사용자 등록해줘야함.
	      */
	     session.setAttribute("user", user);
	     
	     return "{\"success\": true}";
	}
	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session){
		
		session.invalidate();
		
	     return "redirect:/";
	}
	
	
	
	
	
	
	private List<String> sidoNames = Arrays.asList("서울", "경기", "강원", "전남", "전북", "경남" , "경북", "충남", "충북");
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
		
//		PmData seoul = dao.getAvrData("서울");
//		model.addAttribute("seoul",seoul);
//		
//		PmData gyeonggi = dao.getAvrData("경기");
//		model.addAttribute("gyeonggi",gyeonggi);
//		
//		PmData gangwon = dao.getAvrData("강원");
//		model.addAttribute("gangwon", gangwon);
	}
	
	@ExceptionHandler(APICallException.class) 
	public String handlAPIException (APICallException e) {
		throw e;
	}
	 //////////////////////////////////////////////////////////////////////////////DeleteUserStation
	
	@RequestMapping(value="/DeleteUserStation", method=RequestMethod.POST, produces="application/json" )
	public @ResponseBody String deleteUser_station (Model model, HttpServletRequest req) {
	    String station_name = req.getParameter("station_name");
	    System.out.println("homecontroller" + station_name);
	    userService.delete_Userstation(station_name);
	    return "{\"success\": true, \"places\": [] }";
	}
}
