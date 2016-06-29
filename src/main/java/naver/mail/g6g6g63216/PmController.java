package naver.mail.g6g6g63216;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import naver.mail.g6g6g63216.dao.IStationDao;
import naver.mail.g6g6g63216.vo.PmData;
import naver.mail.g6g6g63216.vo.StationVO;

@Controller
@RequestMapping(value = {"/pm"})
public class PmController {

	/*
	 * PmController c = new PmController();
	 * c.airService = ctx.getBean(AirService.class); // error!
	 * 
	 */
	@Autowired(required=true)
	private AirService airService ;
	
	
	@Autowired
	@Qualifier("proxyStation") 
	private IStationDao stationDao;
	
	@RequestMapping( value= {"/10"}, method={RequestMethod.GET} )
	public ModelAndView pm10 ( ) {
		ModelAndView  mnv = new ModelAndView();
		mnv.setViewName("show-pm10");
		mnv.addObject("title", "PM10");
		return mnv;
	}
	
	@RequestMapping (value = {"/25"}, method={RequestMethod.GET} )
	public ModelAndView pm25 () {
		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("show-pm25");
		mnv.addObject("title","PM25");
		
		return mnv;
		
	}
	
	@RequestMapping ( value = {"/station"})
	public String station ( HttpServletRequest req, Model model) throws UnsupportedEncodingException{
		
		List<String> sidos = new ArrayList<String>();
		sidos.add("[지역선택]");
		sidos.add("서울");
		sidos.add("경기");
		sidos.add("강원");
		sidos.add("경북");
		sidos.add("경남");
		sidos.add("부산");
		sidos.add("전북");
		String sido = null;
		
		if(req.getParameter("sido")==null){
			sido ="[지역선택]";
		}else{
			sido = req.getParameter("sido");
		}
		
		String stationName = req.getParameter("name");
		
	
		System.out.println("sido:" + sido);
		System.out.println("station: " + stationName);
		model.addAttribute("sidos", sidos);
		model.addAttribute("sido", sido);
		
		if ( stationName != null ) {
			List<StationVO> stations = stationDao.findStationsBySido2(sido);
			List<PmData> data = airService.getDataByStation(stationName);
			model.addAttribute("stations",stations);
			model.addAttribute("station",stationName);
			model.addAttribute("pmData", data);
			
		}
	//	System.out.println(data);
		
		
		
	//	model.addAttribute("selected", stationName);
		return "air-by-station"; // air-by-station.jsp
	}
//	@RequestMapping( value= {"/pm/10"}, method={RequestMethod.GET} )
//	public String pm10 ( Model model ) {
//		model.addAttribute("title", "PM10");
//		return "show-pm10";
//	}
}
