package naver.mail.g6g6g63216.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import naver.mail.g6g6g63216.vo.StationVO;

public class StationDao implements IStationDao {

	private String apiKey ; // = "LX%2Bvip8U1cIkZRIYLe%2Fj20f%2F7QAGPO8I3bIF6PRU9ILI05ynseP670tj5oAmkfnaUDKKbMPLRuQNRdosbKDN%2Fg%3D%3D";
	
	/* (non-Javadoc)
	 * @see naver.mail.g6g6g63216.dao.IStationDao#setApiKey(java.lang.String)
	 */
	@Override
	@Autowired(required=true)
	public void setApiKey( @Value("${apikey}") String key ) {
		System.out.println("    API Key in StationDao : " + key);
		this.apiKey = key;
	}
	/* (non-Javadoc)
	 * @see naver.mail.g6g6g63216.dao.IStationDao#findStationsBySido(java.lang.String)
	 */
	@Override
	public List<String> findStationsBySido( String sidoName) {
		
		
		System.out.println("cache miss: sending request for " + sidoName);
		
		String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getMsrstnList?ServiceKey=${key}&numOfRows=100&pageSize=10&pageNo=1&startPage=1&addr=${sido}";
		
		 uri = uri.replace("${key}", apiKey).replace("${sido}", encoding(sidoName) );
		 
		 
		// System.out.println("URI: " + uri);
		 Connection con = Jsoup.connect(uri);
		 con.timeout(10*1000); // 10초
		 con.parser(Parser.xmlParser());
		 try {
			Document xmlDoc = con.get();
			//System.out.println(xmlDoc.toString());
			Elements elems = xmlDoc.select("body items > item");
			
			int size = elems.size();
		//	System.out.println("size: " + size);
			
			
			List<String> stationNames = new ArrayList<String>();
			for(int i=0; i<size; i++){
				Element e = elems.get(i);
				String stationName = e.select("stationName").text();
               				
				stationNames.add(stationName);
			}
			
			return stationNames; 
			
		} catch (IOException e) {
			throw new RuntimeException("JSoup Exception", e);
			
		}
		 
	}
	/**
	 * (시도명, lat, lng, 주소) : VO를 만들어야 합니다. 
	 * @param sidoName
	 * @return
	 */
	@Override
	public List<StationVO> findStationsBySido2( String sidoName) {
		
		
		System.out.println("cache miss: sending request for " + sidoName);
		
		String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getMsrstnList?ServiceKey=${key}&numOfRows=100&pageSize=10&pageNo=1&startPage=1&addr=${sido}";
		
		 uri = uri.replace("${key}", apiKey).replace("${sido}", encoding(sidoName) );
		 
		 
		// System.out.println("URI: " + uri);
		 Connection con = Jsoup.connect(uri);
		 con.timeout(30*1000); // 10초
		 con.parser(Parser.xmlParser());
		 try {
			Document xmlDoc = con.get();
			//System.out.println(xmlDoc.toString());
			Elements elems = xmlDoc.select("body items > item");
			
			int size = elems.size();
		//	System.out.println("size: " + size);
			
			
			List<StationVO> stationNames = new ArrayList<StationVO>();
			for(int i=0; i<size; i++){
				Element e = elems.get(i);
				String stationName = e.select("stationName").text();
				String lat = e.select("dmX").text();
				String lng = e.select("dmY").text();
				String addr =e.select("addr").text();
				
				StationVO station = new StationVO(stationName, addr, lat, lng);
               	// statoin vo를 하나 만들어서 넣어줌.
				stationNames.add(station);
			}
			
			return stationNames; 
			
		} catch (IOException e) {
			throw new RuntimeException("JSoup Exception", e);
			
		}
		 
	}

	private String encoding(String s) {
		try {
			return URLEncoder.encode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
}
