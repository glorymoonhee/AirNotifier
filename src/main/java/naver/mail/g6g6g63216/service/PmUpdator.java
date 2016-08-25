package naver.mail.g6g6g63216.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import naver.mail.g6g6g63216.dao.AirDataLoader;
import naver.mail.g6g6g63216.dao.CachePmDao;
import naver.mail.g6g6g63216.util.Util;
import naver.mail.g6g6g63216.vo.UserVO;

@Service
public class PmUpdator {

	private static Logger logger = LoggerFactory.getLogger(PmUpdator.class);
	
	@Autowired
	private AirDataLoader rawDataLoader ;
	
	@Autowired
	private CachePmDao dao;
	
	@Autowired
	private MailService mailService;

	private MessageDigest sh ;
	
	
	private List<String> regions = new ArrayList<String>() ;
	
	@PostConstruct
	public void loadSido () throws NoSuchAlgorithmException {
		System.out.println(" 여기서 하고 싶은 작업을 추가로 구현함.");
		
		Scanner sc = new Scanner( CachePmDao.class.getResourceAsStream("/region.txt"), "UTF-8" );
		
		sh = MessageDigest.getInstance("SHA-256");
		
		String sido = null;
		while(sc.hasNextLine()){
			sido = sc.nextLine().trim();
			System.out.println("target : " + sido );
			regions.add(sido);
		}
		
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
	
	@Scheduled( cron="00 20 * * * ?" )
	public void updatePmData() {
		
		logger.info("Start update for " + regions);
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
	public void startNotification(String oldXml, String newXml) {
		/*
		 * 1. 양쪽 xml을 jsoup으로 파싱을 합니다.
		 */
		Document oldDoc = Jsoup.parse(oldXml, "", Parser.xmlParser());
		Elements oldItems = oldDoc.select("body items item"); // A, B, C 
		Document newDoc = Jsoup.parse(newXml, "", Parser.xmlParser()); 
		Elements newItems = newDoc.select("body items item"); // A, B, C
		
		
		List<UserVO> users = new ArrayList<UserVO>();
		Map<String,PmNotif> map = new HashMap<String, PmNotif>();
		
		for(int i=0; i< oldItems.size(); i++ ){
		   String stationName = oldItems.get(i).select("stationName").text(); 
		   int oldpm100 = s2i(oldItems.get(i).select("pm10Value").text());
		   int newpm100 = s2i(newItems.get(i).select("pm10Value").text());
		   
		   if ( oldpm100 == -1 || newpm100 == -1 ) {
			   continue;
		   }
		   
		   users = dao.getListUsers(stationName, oldpm100, newpm100);
		   map.put(stationName, new PmNotif(stationName, oldpm100, newpm100, users));
		}
			
			 System.out.println(users.size()+"user size 제발 나와라!!!!!!!!!!!!!!");
		
			 
		for(String sido : map.keySet()){
			PmNotif pmnotif = map.get(sido);
			for ( UserVO u : pmnotif.users ) {
				String email = dao.getUser_email(u.getSeq());
				System.out.println("email: " + email);
				mailService.sendMail(email, "[PM변경] " + sido , pmnotif.oldPM +"에서"+ pmnotif.newPM+"로 바뀌었습니다."); 
			}
				 
		}
		
	}
	
	private static class PmNotif {
		String sido;
		int oldPM;
		int newPM ;
		List<UserVO> users;
		public PmNotif(String sido, int oldPM, int newPM, List<UserVO> users) {
			super();
			this.sido = sido;
			this.oldPM = oldPM;
			this.newPM = newPM;
			this.users = users;
		}
		
	}
	
	private int s2i ( String s ) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1 ;
		}
	}
}
