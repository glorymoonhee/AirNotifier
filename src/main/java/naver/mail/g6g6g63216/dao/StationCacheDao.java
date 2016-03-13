package naver.mail.g6g6g63216.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import naver.mail.g6g6g63216.vo.StationVO;

public class StationCacheDao implements IStationDao {


	private IStationDao dao ;
	
	private Map<String, List<String>> stationMap = new HashMap<String, List<String>>();
	
	public void setTarget ( IStationDao targetDao ) {
		dao = targetDao;
	}
	
	@Override
	public void setApiKey(String key) {
		dao.setApiKey(key);
	}

	@Override
	public List<String> findStationsBySido(String sidoName) {
		// @Transactional
		if ( stationMap.containsKey(sidoName) ) {
			// 주어진 sidoName에 대한 관측소 리스트가 있으면 그냥 꺼내서 반환함.
			System.out.println("cache hit: " + sidoName);
			List<String> list = stationMap.get(sidoName);
			return list;
			
		}
		List<String> stationNames = dao.findStationsBySido(sidoName);
		stationMap.put(sidoName, stationNames);
		return stationNames;
	}

	@Override
	public List<StationVO> findStationsBySido2(String sidoName) {
		
		
		return dao.findStationsBySido2(sidoName);
	}

}
