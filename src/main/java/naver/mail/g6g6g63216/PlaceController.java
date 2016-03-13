package naver.mail.g6g6g63216;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import naver.mail.g6g6g63216.dao.IPmDao;
import naver.mail.g6g6g63216.dao.IStationDao;
import naver.mail.g6g6g63216.dao.IUserDao;
import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

@Controller
public class PlaceController {

	@Autowired
	@Qualifier(value="cachedPM")
	private IPmDao dao;
	
	@Autowired
	@Qualifier(value="proxyStation")
	private IStationDao stationDao;
	
	@Autowired
	private IUserDao userDao ;
	
	public void setDao ( IPmDao pmDao) {
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

	
	    List<StationVO> stations = stationDao.findStationsBySido2(sido);
	  //  System.out.println("�õ� ��ȸ: " + stations);
	    
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
	
	
	@RequestMapping(value="/station/register", method=RequestMethod.POST, produces="application/json" )
	public @ResponseBody Map<String, Object> register_station_name(HttpServletRequest req, HttpSession session ) {
             
		String stationName = req.getParameter("station");
		UserVO loginUser = (UserVO) session.getAttribute("user");
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(loginUser ==null){
		  resMap.put("success", Boolean.FALSE);
		  resMap.put("cause", "NO_LOGIN");
		  return resMap;
		}
		
		
		/*
		 * �߰��� �� �Ǿ�����
		 * 
		 * { success : true }
		 * 
		 *  ����������
		 *  1. �α��� ���� ����.
		 *  {success : false , cause : NO_LOGIN}
		 *  
		 *  2. �̹� �߰�����.
		 *  {success : false, cause : ALREADY_REGITERED}
		 *  
		 */
		StationVO station = this.stationDao.findStationsByName(stationName);
		userDao.insertStation(loginUser.getSeq(), station );
		resMap.put("success", Boolean.TRUE);
		
		return resMap;
	}
	
	
	 
}
