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
import org.springframework.jdbc.core.JdbcTemplate;
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
public class PmDao {

//	@Autowired
	private JdbcTemplate template;
	
	private String apiKey;
	
	public void setJdbcTemplate ( JdbcTemplate template) {
		System.out.println("ddddddddddddddddddddddddddddddddddddd??????????????????");
		System.out.println(this);
		this.template = template;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}



	public PmData findPm10ByRegion(String regionName){
		return new PmData("--", 0, "--", 0 ,"없음");
	}
	/**
	 * 측정소별 대기오염 정보를 조회합니다.
	 * @param stationName 측정소 이름
	 * @return
	 * @throws IOException
	 */
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
			org.jsoup.Connection con = Jsoup.connect(uri);
			con.parser(Parser.xmlParser());
			Document xmlDoc = con.get();
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
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encoding error", e);
		} catch (IOException e) {
			throw new RuntimeException("io error", e);
		}
		
	}
	
	public List<PlaceVO> findAllPlaces ( ) {
		String query = "select seq,locname,x(pos) as lat, y(pos) as lng, cdate from locations order by cdate desc";
//		JdbcTemplate template = new JdbcTemplate();// �뿬湲� �엳�쓣 �븘�슂媛� �뾾�쓬.
//		template.setDataSource(ds);
		
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
}
