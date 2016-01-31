package naver.mail.g6g6g63216;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.IStationDao;
import naver.mail.g6g6g63216.dao.PmDao;
import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.StationVO;

@Controller
public class PlaceController {

	@Autowired
	private PmDao dao;
	
	@Autowired
	@Qualifier(value="proxyStation")
	private IStationDao stationDao;
	
	public void setDao ( PmDao pmDao) {
		//System.out.println("[pm dao in PlaceController] " + pmDao);
		this.dao = pmDao;
	}
	
	@RequestMapping( value ={"/places"}, method={RequestMethod.GET} )
	public String Allplace(Model model){
		
		List<PlaceVO> places = dao.findAllPlaces();
		model.addAttribute("places", places);
		return "places";
	}
	
	
	@RequestMapping(value={"/query/stations"}, method={RequestMethod.GET}, produces="application/json")
	public @ResponseBody Map<String, Object> queryBySido(HttpServletRequest request){
		
		String sido = request.getParameter("sido");

	
	    List<String> stations = stationDao.findStationsBySido(sido);
	  //  System.out.println("시도 조회: " + stations);
	    
	    Map<String, Object> response = new HashMap<String, Object>(); // dictionary, dict( 1: 1)
	    response.put("success", Boolean.TRUE);
	  
	    response.put("stations", stations);
	    
		return response;
	}
	
	@RequestMapping(value={"/station-map"}, method={RequestMethod.GET})
	public String pageStationMap (){
	      
		return "station-map";
	}
	
	@RequestMapping(value="/stations", method=RequestMethod.GET, produces="application/json" )
	public @ResponseBody List<StationVO> d(HttpServletRequest req) {
		  String sido = req.getParameter("sido");
		       List<StationVO> stations= stationDao.findStationsBySido2(sido);
		return stations;
	}
	
	
	 
}
