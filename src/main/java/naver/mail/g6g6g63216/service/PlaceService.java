package naver.mail.g6g6g63216.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import naver.mail.g6g6g63216.dao.IStationDao;
import naver.mail.g6g6g63216.vo.StationVO;

public class PlaceService {

	
	
	/*
	@Autowired
	@Qualifier(value="proxyStation")
	*/
	private IStationDao stationDao;
	
	public void setStationDao ( IStationDao dao ) {
		this.stationDao = dao;
	}

	public List<StationVO> getNearest( double clat, double clng, int cnt ) {
		/*
		 * 1. 일단은 모든 관측소를 다 가져옴.
		 */
		
		
		List<StationVO> stations = stationDao.findAllstations(); 
		/*
		 * VALUE        KEY
		 * station0 - 0.43454
		 * station1  - 0.4432
		 * staiton2 = 0.56744
		 *  
		 * 0.34533, 0.542, 1.3233, 0.5644, 0.233, ....
		 * 
		 * 0.34533,  0.233
		 * 
		 */
		double min = Double.MAX_VALUE;
		TreeMap<Double,StationVO> map = new TreeMap<Double, StationVO>();
		
		for(int i=0; i<stations.size(); i++){
			
			double lat = Double.parseDouble(stations.get(i).getLat());
			double lng = Double.parseDouble(stations.get(i).getLng());
			double dist = Math.sqrt(((clat-lat)*(clat-lat)) + ((clng-lng)*(clng-lng)));
			
			map.put(dist, stations.get(i));
			
		}
		
		Set<Double> keys = map.keySet();
		
		List<StationVO> nearest = new ArrayList<StationVO>();
		for ( Double d : keys ) {
			nearest.add(map.get(d) );
			if ( nearest.size() == cnt ) {
				break;
			}
		}
		
		return nearest;
	}
}
