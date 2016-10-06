package naver.mail.g6g6g63216.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import naver.mail.g6g6g63216.vo.PostingVO;

public class BoardDao {
	
private JdbcTemplate template ;
	
	public void setJdbcTemplate ( JdbcTemplate template) {
		this.template = template;
	} 
	  
	public List<PostingVO> readAllpostings( ){
		
		String sql = "select * from postings";
		List<PostingVO> postings = template.query(sql, new RowMapper<PostingVO>(){

			@Override
			public PostingVO mapRow(ResultSet rs, int rowNum) throws SQLException {

				int seq = rs.getInt("seq");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String content = rs.getString("content");
				String date = rs.getString("date");
				int viewcount = rs.getInt("viewcount");
				//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
				//date.setDateFormat(df);
				
				System.out.println( writer+"~~~~~~~~~~~~~~~~~~~"+viewcount+"~~~~~~~~~~"+date);
				
				PostingVO p = new PostingVO(seq, title, writer, content, date, viewcount);
				
				return p;
			}
			
		});
		
		return postings;
	}
	
	
}
