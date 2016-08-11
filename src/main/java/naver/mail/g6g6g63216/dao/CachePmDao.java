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
	
	@PostConstruct
	public void loadSido () throws NoSuchAlgorithmException {
		System.out.println(" 여기서 하고 싶은 작업을 추가로 구현함.");
		
		Scanner sc = new Scanner( CachePmDao.class.getResourceAsStream("/region.txt"), "UTF-8" );
		
		while(sc.hasNextLine()){
			//System.out.println(sc.nextLine());
			regions.add(sc.nextLine().trim());
		}
		
		sh = MessageDigest.getInstance("SHA-256");
		
	}

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
	
	@Scheduled( cron="0 20 * * * ?" )
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
	/**
	 * 
	 * @param oldXml 아까전에 읽어들였던 시도의 data
	 * @param newXml 지금 읽어들인 최신의 시도 data
	 */
	private void startNotification(String oldXml, String newXml) {
		/*
		 * 1. 양쪽 xml을 jsoup으로 파싱을 합니다.
		 */
		Document oldDoc = Jsoup.parse(oldXml, "", Parser.xmlParser());
		Elements oldItems = oldDoc.select("body items item");
		Document newDoc = Jsoup.parse(newXml, "", Parser.xmlParser());
		Elements newItems = newDoc.select("body items item");
		
		
//		con.parser(Parser.xmlParser());

		/*
		 * 2. 루프 - 각각의 관측소마다. 이 관측소를 추가한 사용자들을 불러옵니다.
		 *       - user_station.pm100  값을 기준으로
		 *       - oldXml 의 값과 newXml 의 값이 pm100 의 양쪽에 존재하면 이 사람한테 메일을 보내줘야 함.
		 */
		
	}
	@Override
	public String getRawPmData(String sido) {
		throw new RuntimeException("호출하지 마셈!");
//		
//		return null;
	}
	
}
