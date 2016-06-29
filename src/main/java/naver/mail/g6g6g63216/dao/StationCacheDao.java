package naver.mail.g6g6g63216.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import naver.mail.g6g6g63216.vo.StationVO;

public class StationCacheDao implements IStationDao {


	private IStationDao dao ;
	
	private JdbcTemplate template ;
	
	public void setJdbcTemplate ( JdbcTemplate template) {
		this.template = template;
	}
	
	private Map<String, List<String>> stationMap = new HashMap<String, List<String>>();
	/*
	 * JdbcTempate 
	 */
	
	public void setTarget ( IStationDao targetDao ) {
		dao = targetDao;
	}
	
	@Override
	public void setApiKey(String key) {
		dao.setApiKey(key);
	}

	@Override
	public List<String> findStationsBySido(String sidoName) {
		
		
		if ( stationMap.containsKey(sidoName) ) {
			// 주어진 sidoName에 대한 관측소 리스트가 있으면 그냥 꺼내서 반환함.
			System.out.println("cache hit: " + sidoName);
			List<String> list = stationMap.get(sidoName);
			return list;
			
		}
		List<String> stationNames = dao.findStationsBySido(sidoName);
		stationMap.put(sidoName, stationNames);
		return stationNames;
	}

	@Override
	public List<StationVO> findStationsBySido2(String sidoName) {
		// @Transactional
		String query = "SELECT seq, station_name, station_addr, lat, lng, sido  FROM stations where sido = ?";

		/*
		 * 1. sql 쿼리 
		 * 2. sql 쿼리의 물음표에 설정될 값들의 Object 배열 
		 * 3. result set
		 */
		List<StationVO> stations = template.query(query, new Object[] { sidoName }, new RowMapper<StationVO>() {
			@Override
			public StationVO mapRow(ResultSet rs, int rownum) throws SQLException {
				Integer seq = rs.getInt("seq");
				String name = rs.getString("station_name");
				String addr = rs.getString("station_addr");
				String lat = rs.getDouble("lat") + "";
				String lng = rs.getDouble("lng")+ "";
				StationVO vo = new StationVO(seq, name, addr, lat, lng);
				return vo;
			}
		});
		
		if ( stations.size() == 0 ) {
			System.out.println("No Station Info : remote request!");
			List<StationVO> newStations = dao.findStationsBySido2(sidoName);
			insertTODB ( sidoName, newStations);
			return newStations ;
		} else {
			System.out.println("Cached Station Data");
			return stations ;
		}

	}

	private void insertTODB(final String sidoName, final List<StationVO> newStations) {
		String query = "insert into stations (station_name,station_addr,sido,lat,lng) values(?,?,?,?,?)";
		
		template.batchUpdate(query, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement stmt, int idx) throws SQLException {
				StationVO vo = newStations.get(idx);
				stmt.setString(1, vo.getName());
				stmt.setString(2, vo.getAddr());
				stmt.setString(3, sidoName);
				
				
				/*
				 *  위경도 정보가 없을 수도 있다! 이상한 놈들이다!
				 *  일단 0 으로 넣기로 합니다.
				 *  
				 */
				double d = 0;
				try {
					d = Double.parseDouble(vo.getLat() );
				} catch ( NumberFormatException e) {
					d = 0;
				}
				stmt.setDouble(4,  d);
				
				try {
					d = Double.parseDouble(vo.getLng() );
				} catch ( NumberFormatException e) {
					d = 0;
				}
				stmt.setDouble(5, d );
			}
			
			@Override
			public int getBatchSize() {
				return newStations.size();
			}
		});
	}

	@Override
	public StationVO findStationsByName(String stationName) {
		String query = "select seq, station_name, station_addr, lat, lng, sido from stations where station_name = ?";
		StationVO vo = template.queryForObject(query, new Object[]{stationName}, new RowMapper<StationVO>() {

			@Override
			public StationVO mapRow(ResultSet rs, int rownum) throws SQLException {
				int num = rs.getInt("seq");
				String name = rs.getString("station_name");
				String addr = rs.getString("station_addr");
				String lat = rs.getString("lat");
				String lng = rs.getString("lng");
				
				StationVO station = new StationVO(num, name, addr, lat, lng);
				return station;
			}
			
		});
		
		return vo;
	}
	
	

}
