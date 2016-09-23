package naver.mail.g6g6g63216.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import naver.mail.g6g6g63216.util.Util;
import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;
/**
 * ${requestScope.user.seq}
 *                user.getseq()
 * @author Administrator
 *
 */
public class PmDao implements IPmDao, ApiKeySpec {


	@Autowired(required=true)
	private JdbcTemplate template;
	
	@Value("${apikey}")
	private String apiKey;
	
	@Override
	public String getApiKey() {
		return apiKey;
	}	

	@Override
	public void setApiKey( String apiKey) {
		System.out.println("    API KEY : " + apiKey );
		this.apiKey = apiKey;
	}

	/* (non-Javadoc)
	 * @see naver.mail.g6g6g63216.dao.IPmDao#getAvrData(java.lang.String)
	 */
	@Override
	public PmData getAvrData( String sido ) {
		List<PmData> datas = queryBySido(sido);
		
		int sum10 =0;
		int count10 = 0;
	    int sum25 =0;
	    int count25 = 0;
	    
	    double grade10 =0;
	    double grade25 =0;
	    for(PmData d : datas){
	    	String pm10 = d.getPm100(); // "", "-"
	    	String pm25 = d.getPm025();
	    	
	    	 grade10 += d.getPm100Grade();
	    	 grade25 += d.getPm025Grade();
	    	 try {
				int v = Integer.parseInt(pm10);
			    count10++;
				sum10 += v;
			    
			} catch (NumberFormatException e) {
				// not a number
			}
	    	 
	    	 try {
					int v2 = Integer.parseInt(pm25);
				    count25++;
					sum25 += v2;
				    
				} catch (NumberFormatException e) {
					// not a number
				}
	   }
	    System.out.println("avr pm 10 grade : " + grade10 + ", " + grade25);
	    count10 = ( count10 == 0 ? 1 : count10);
	    count25 = ( count25 == 0 ? 1 : count25);
	    
	    int avgPM10 = sum10/count10;
	    int avgPM25 = sum25/count25;
	    int avrPM10Grade = (int) (grade10/count10); 
	    int avrPm25Graide = (int) (grade25/count25);
	    PmData avrData = new PmData(""+avgPM10, avrPM10Grade,""+avgPM25, avrPm25Graide, datas.get(0).getDataTime());
	    return avrData;
	}
	
	/* (non-Javadoc)
	 * @see naver.mail.g6g6g63216.dao.IPmDao#queryBySido(java.lang.String)
	 */
	@Override
	public List<PmData> queryBySido ( String sido) {
		
		String query = "select xml from sido where sido_name = ? ";
		String xml = template.query(query,new Object[]{sido},new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString("xml");
			}
		});
		
		
		Document xmlDoc = Jsoup.parse(xml, "", Parser.xmlParser());
	
		Elements elems = xmlDoc.select("body items item");
		
		ArrayList<PmData> data = new ArrayList<PmData>();
		int size = elems.size();
		// ResultSet 
		for ( int i = 0 ; i < size ; i++) {
			Element e = elems.get(i);
			String pm100 =e.select("pm10Value").text();
			int pm100Grade = Util.s2i(e.select("pm10Grade").text(), 0);
			String pm025 = e.select("pm25Value").text();
			int pm025Grade = Util.s2i(e.select("pm25Grade").text(), 0); // ""
			String datatime = e.select("dataTime").text();
			
			PmData pm = new PmData(pm100, pm100Grade, pm025, pm025Grade,datatime);
			//System.out.println(pm100);
			data.add(pm);
		}
		return data;
		
		
	}
	
	@Override
	public String getRawPmData( String sido ) {
		
		
		String sql = "select xml from sido where sido_name = ?";
		String oldXml = template.query(sql, new Object[]{sido}, new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString(1);
			}
			
		});
		return oldXml;
		
	}

	@Override
	public List<PmData> queryByStation(String stationName) {
		
		String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc"
				+ "/getMsrstnAcctoRltmMesureDnsty?"
				+ "ServiceKey=${key}"
				+ "&numOfRows=40"
				+ "&pageSize=10"
				+ "&pageNo=1"
				+ "&startPage=1"
				+ "&stationName=${station}"
				+ "&dataTerm=DAILY"
				+ "&ver=1.0"; // DAILY, MONTH, 3MONTH
		// 종로구 -> %EC%A2%85%EB%A1%9C%EA%B5%AC 
		try {
			uri = uri.replace("${station}", URLEncoder.encode(stationName, "utf-8"))
					 .replace("${key}", this.apiKey);
			System.out.println("[URI] " + uri);
			org.jsoup.Connection con = Jsoup.connect(uri);
			con.parser(Parser.xmlParser());
			Document xmlDoc = con.get();
			Elements elems = xmlDoc.select("body items item");
			
			ArrayList<PmData> data = new ArrayList<PmData>();
			int size = elems.size();
			int x = 0;
			 if(size >= 25) {
			     x = 1;
			 }else{
				 x = 0;
			 }
			
			// ResultSet 
			for ( int i = x ; i < size ; i++) {
				Element e = elems.get(i);
				String pm100 =e.select("pm10Value").text();
				int pm100Grade = Util.s2i(e.select("pm10Grade").text(), 0);
				String pm025 = e.select("pm25Value").text();
				int pm025Grade = Util.s2i(e.select("pm25Grade").text(), 0); // ""
				String datatime = e.select("dataTime").text();
				
				PmData pm = new PmData(pm100, pm100Grade, pm025, pm025Grade,datatime);
				//System.out.println(pm100);
				data.add(pm);
			}
			return data;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encoding error", e);
		} catch (IOException e) {
			throw new RuntimeException("io error", e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see naver.mail.g6g6g63216.dao.IPmDao#findAllPlaces()
	 */
	@Override
	public List<PlaceVO> findAllPlaces ( ) {
		String query = "select seq,locname,x(pos) as lat, y(pos) as lng, cdate from locations order by cdate desc";
         // 
		
		List<PlaceVO> places = template.query(query, new RowMapper<PlaceVO>(){

			@Override
			public PlaceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlaceVO place = new PlaceVO();
				place.setSeq(rs.getInt("seq"));
				place.setLocname(rs.getString("locname"));
				place.setLat(rs.getString("lat"));
				place.setLng(rs.getString("lng"));
				place.setCdate(rs.getString("cdate"));
				return place;
			}
		
		});
		
		return places;
	}
	
	@Override
	public Integer queryByStn(String stationName) {
		   
		String query = "select xml from sido where sido_name in (select sido from stations where station_name = ?)";
		String xml = template.query(query, new Object[]{stationName}, new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString("xml");
			}
		});
	
		
		Document xmlDoc = Jsoup.parse(xml, "", Parser.xmlParser());
		
		Elements elems = xmlDoc.select("body items item");
		int size = elems.size();
		int pm100 = 0;
	
		
		for ( int i = 0 ; i < size ; i++) {
			Element e = elems.get(i);
			if(e.select("stationName").text().equals(stationName)){
				try {
					pm100 =Integer.parseInt(e.select("pm10Value").text());	
				} catch (NumberFormatException e2) {
					pm100 = -1 ;
				}
				break;
			}
			 
		}
		
		return pm100;
	}
}
