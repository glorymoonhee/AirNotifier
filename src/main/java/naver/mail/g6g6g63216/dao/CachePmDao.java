package naver.mail.g6g6g63216.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;

public class CachePmDao implements IPmDao {
	
	@Autowired(required=true)
	private JdbcTemplate template ;

	private static Logger logger = LoggerFactory.getLogger(CachePmDao.class);
	
	final private Map<String,PmData> avrSidoData = new HashMap<String, PmData>();
	
	private List<String> regions = new ArrayList<String>();
	
	@Autowired(required=true)
	private IPmDao target ;
	
	private MessageDigest sh ; 

	/*
	@Override
	public void setJdbcTemplate(JdbcTemplate template) {
		this.template = template;
	}
	*/

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
	
	
	// FIXME PmUpdater 로 옮겼으니까 나중에 지우자!
	/*
	public void updatePMData () {
		for ( String sido : regions ) {
			String xml = target.getRawPmData ( sido );
			
			sh.update(xml.getBytes());
			byte [] digested = sh.digest();
			StringBuilder sb = new StringBuilder();
			for(int i = 0 ; i < digested.length ; i++){
				sb.append(Integer.toString((digested[i]&0xff) + 0x100, 16).substring(1));
			}
			
			String sha256 = sb.toString();
			
			if ( pmDataChanged ( sido, sha256 ) ) {
				String oldXml = null ;
				
				startNotification ( oldXml, xml );
			}
			
			
		}
	}
	*/
	/**
	 * 데이터가 갱신되었으면 true, 그렇지 않으면 false 를 반홚합니다.
	 * @param sido
	 * @param sha256
	 * @return
	 */
	public boolean pmDataChanged(String sido, String sha256) {
		String sql = "select count(*) from sido where sido_name=? and sha256=? ";
		Object [] params = new Object[]{sido,sha256};
		
		Integer cnt = template.query(sql, params, new  ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
		});
		return cnt == 0 ;
	}
	
	@Override
	public String getRawPmData(String sido) {
		return target.getRawPmData(sido);
	}
	
	public void setRawPmData(String sido, String xml, String key ){
		
		String sql = "update sido set xml = ? , sha256 = ? where sido_name = ?;";
		template.update(sql, new Object[]{xml,key,sido});
		
		
	}
	
}
