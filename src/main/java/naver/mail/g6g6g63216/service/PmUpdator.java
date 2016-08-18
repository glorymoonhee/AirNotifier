package naver.mail.g6g6g63216.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import naver.mail.g6g6g63216.dao.AirDataLoader;
import naver.mail.g6g6g63216.dao.CachePmDao;
import naver.mail.g6g6g63216.util.Util;

@Service
public class PmUpdator {

	private static Logger logger = LoggerFactory.getLogger(PmUpdator.class);
	
	@Autowired
	private AirDataLoader rawDataLoader ;
	
	@Autowired
	private CachePmDao dao;
	
	private MessageDigest sh ;
	
	
	private List<String> regions = new ArrayList<String>() ;
	
	@PostConstruct
	public void loadSido () throws NoSuchAlgorithmException {
		System.out.println(" 여기서 하고 싶은 작업을 추가로 구현함.");
		
		Scanner sc = new Scanner( CachePmDao.class.getResourceAsStream("/region.txt"), "UTF-8" );
		
		sh = MessageDigest.getInstance("SHA-256");
		/*
		String sido = null;
		while(sc.hasNextLine()){
			sido = sc.nextLine().trim();
			System.out.println("target : " + sido );
			regions.add(sido);
			String xml = rawDataLoader.loadAirData(sido);
			String sha256 = Util.encode(sh, xml);
			dao.setRawPmData(sido, xml, sha256);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
	}
	
	@Scheduled( cron="0 20 * * * ?" )
	public void updatePmData() {
		
		logger.info("Start update");
		for (String region:regions){
			logger.info("region: " + region);
			
			String newXml = rawDataLoader.loadAirData(region);
			String key = Util.encode(sh,newXml);
             
			if ( dao.pmDataChanged(region, key)){
				logger.info("XML UPDATED!");
				String oldXml = dao.getRawPmData(region);
				startNotification(oldXml, newXml);
				dao.setRawPmData(region, newXml, key);
			}
		}
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
		Elements oldItems = oldDoc.select("body items item"); // A, B, C 
		Document newDoc = Jsoup.parse(newXml, "", Parser.xmlParser()); 
		Elements newItems = newDoc.select("body items item"); // A, B, C
		
		/*
		 * 
		List<UserVO> users = new ArrayList<>();
		for ( A, B, C ) {
		  region = A ;
		  old_pm100 = oldImte.get(A).pm100; // 45
		  new_pm100 = newItem.get(A).pm100; // 97
		  
		   
		}
		
		 */
		
//		con.parser(Parser.xmlParser());

		/*
		 * 2. 루프 - 각각의 관측소마다. 이 관측소를 추가한 사용자들을 불러옵니다.
		 *       - user_station.pm100  값을 기준으로
		 *       - oldXml 의 값과 newXml 의 값이 pm100 의 양쪽에 존재하면 이 사람한테 메일을 보내줘야 함.
		 */
		
	}
}
