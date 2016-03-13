package naver.mail.g6g6g63216.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import naver.mail.g6g6g63216.vo.UserVO;

public class UserDao implements IUserDao {

	private JdbcTemplate template;
	
	public void setTpl( JdbcTemplate template) {
		this.template = template;
	}
	@Override
	public UserVO insertUser(final String email, final String password) {
		
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
		template.update(psc, holder); // 서버로 쿼리 날린 후 seq값을 얻어와서 holder에 넣어줍니다.
		Integer seq = holder.getKey().intValue();
		
		UserVO user = new UserVO( seq, email, password);
		return user ;
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

}
