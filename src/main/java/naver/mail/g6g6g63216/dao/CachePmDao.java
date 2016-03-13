package naver.mail.g6g6g63216.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;
/**
 * 1시간마다 기샃엉 대기 데이터가 업데이트 되기 때문에 해쉬맵에 담아놓은 데이터를 1시간 간격으로 날려버려야지 새로운 데이터를 확인할 수 있습니다.
 * 그런데 지금 구현은 캐쉬를 영구적으로 들고 있어서 시간이 지나도 처음 등록된 데이터를 서비스하게 됩니다.
 * 
 * 따라서 매시간마다 캐쉬를 비워주는 구현이 필요합니다.
 * (Thread)
 * @author Administrator
 *
 */
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
					Thread.sleep( 60 * 1000 * 30  ); // 30 분마다
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " 캐쉬 날리자!");
				synchronized (avrSidoData) {
					avrSidoData.clear();
				}
			}
		}
	}

}
