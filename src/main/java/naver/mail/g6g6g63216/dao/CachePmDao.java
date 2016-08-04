package naver.mail.g6g6g63216.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;

public class CachePmDao implements IPmDao {

	private static Logger logger = LoggerFactory.getLogger(CachePmDao.class);
	
	final private Map<String,PmData> avrSidoData = new HashMap<String, PmData>();
	
	private List<String> regions = new ArrayList<String>();
	
	@Autowired(required=true)
	private IPmDao target ;
	
	@PostConstruct
	public void loadSido () {
		System.out.println(" 여기서 하고 싶은 작업을 추가로 구현함.");
		
		Scanner sc = new Scanner( CachePmDao.class.getResourceAsStream("/region.txt"), "UTF-8" );
		
		while(sc.hasNextLine()){
			//System.out.println(sc.nextLine());
			regions.add(sc.nextLine().trim());
		}
	}
	@Override
//	@Autowired(required=true)
	public void setJdbcTemplate(JdbcTemplate template) {
		
	}

	@Override
	public PmData getAvrData(String sido) {
		/*
		 * thread issue 
		 */
		synchronized (avrSidoData) {
			if(avrSidoData.containsKey(sido)){
				System.out.println("cach hit : " + sido);
				return avrSidoData.get(sido);
			}			
		}
		
		System.out.println("cach miss!!: " + sido);
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
	
	@Scheduled( cron="0 30 * * * ?" )
	public void ping () {
		
		logger.info("UPDATING AVRAGE PMDATA ");
		for ( String sido : regions ) {
			logger.info("Reload PM data : " + sido);
//			avrSidoData.remove(sido);
			PmData data = target.getAvrData(sido); // getAvrData(sido);
			synchronized (avrSidoData) {
				avrSidoData.put(sido, data);
			}
			
		}
	}
}
