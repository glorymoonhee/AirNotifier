package naver.mail.g6g6g63216.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

public class UserDao implements IUserDao {

	private JdbcTemplate template;
	
	public void setTpl( JdbcTemplate template) {
		this.template = template;
	}
	@Override
	public UserVO insertUser(final String email, final String password) { //왜 final 인지 
		
		final String query ="insert into users (email,pass) values (?, ?)";
		
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, email);
				stmt.setString(2, password);
				return stmt ;
			}
		};
		
		KeyHolder holder = new GeneratedKeyHolder();
		template.update(psc, holder); 
		
		Integer seq = holder.getKey().intValue();
		
		UserVO user = new UserVO( seq, email, password);
		return user ;
	}
	
	
	@Override
	public int updateUser(String email,String pass){ //
		
		System.out.println(email);
		System.out.println(pass);
		 String query = "update users set pass = ? where email= ?";
		int n =  template.update(query, pass,email);
		 
		return n;
	
	}
	@Override
	public UserVO findUser(String email, String pass) {
        String query = "select seq,email,pass from users where email=? and pass = ?";
        
        UserVO loginUser = template.queryForObject(query, new Object[]{email, pass},  new RowMapper<UserVO>() {

			@Override
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new UserVO(rs.getInt("seq"),rs.getString("email"),rs.getString("pass"));
			}
        	
        });
		return loginUser ;
	}
	
	@Override
	public void insertStation(Integer userSeq, StationVO station,int pm10Val) {
		String query = "insert into user_station (user,station,pm100 ) values (?,?,?)";
		int nInsert = template.update(query, new Object[]{userSeq,station.getSeq(),pm10Val});
		System.out.println("inserted new stations : " + nInsert);
		
	}
	
	@Override
	public void deleteStation(Integer seq, String station_name) {
		   System.out.println("userdao" + station_name);
		String query = "delete from user_station where station in (select seq from stations where station_name = ?) and user = ?";
	     template.update(query, new Object[]{station_name,seq});
		
	}
	
	@Override
	public List<StationVO> findplaces(Integer userSeq){
		/* select seq, station_name, station_addr, lat, lng, sido from stations where seq  in ( select station from user_station where user = 5 );
		 */
		String query = "select "
				+ " seq, station_name, station_addr, lat, lng, sido "
				+ " from stations "
				+ " where seq  in ( select station from user_station where user = ? )";
		
		/*
		 * 1. parameter를 두개 받는 경우 - 쿼리에 물음표가 없는 경우
		 *    param0 - 쿼리
		 *    param1 = row mapper 구현체 
		 *    
		 * 2. parameter를 3개 받는 경우 - 쿼리에 물음표가 있어서 그 곳에 들어갈 값들이 필요함.
		 *    param0 - 쿼리
		 *    param1 - Object 타입의 "배열"
		 *    param2 = row mapper 구현체
		 */
		Object [] args = new Object[]{userSeq};
		RowMapper<StationVO> rowMapper = new RowMapper<StationVO>() {

			@Override
			public StationVO mapRow(ResultSet rs, int rownum) throws SQLException {
//				Integer seq, String name, String addr, String lat, String lng
				 StationVO s = new StationVO(
						 rs.getInt("seq"),
						 rs.getString("station_name"),
						 rs.getString("station_addr"), 
						 rs.getString("lat"), 
						 rs.getString("lng"));
				return s;
			}
		};
		
		List<StationVO> stations = template.query(query, args, rowMapper);
		return stations;
	}

	      
	
	
	
	
}
