package naver.mail.g6g6g63216.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;

public class CachePmDao implements IPmDao {

	private Map<String,PmData> avrSidoData = new HashMap<String, PmData>();
	
	@Autowired(required=true)
	private IPmDao target ;
	
	private CacheClear cacheClear = new CacheClear();
	{
		cacheClear.start();
	}
	@Override
//	@Autowired(required=true)
	public void setJdbcTemplate(JdbcTemplate template) {
		
	}

	@Override
	public PmData getAvrData(String sido) {
		
		if(avrSidoData.containsKey(sido)){
			System.out.println("cach hit : " + sido);
			return avrSidoData.get(sido);
		}
		System.out.println("cach miss: " + sido);
		 PmData data = target.getAvrData(sido);
		 
		synchronized (avrSidoData) {
			 avrSidoData.put(sido, data);			
		}
		 return data;
	}

	@Override
	public List<PmData> queryBySido(String sido) {
		return target.queryBySido(sido);
	}

	@Override
	public List<PmData> queryByStation(String stationName) {
		return target.queryByStation(stationName);
	}

	@Override
	public List<PlaceVO> findAllPlaces() {
		return target.findAllPlaces();
	}
	
	class CacheClear extends Thread  {
		
		public CacheClear() {
			this.setName("T-CHACHE-CLEAR");
		}
		@Override
		public void run() {
			
			while ( true ) {
				try {
					Thread.sleep( 60 * 1000 * 30  );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " ĳ�� ������!");
				synchronized (avrSidoData) {
					avrSidoData.clear();
				}
			}
		}
	}

}
