package naver.mail.g6g6g63216.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import naver.mail.g6g6g63216.vo.PostingVO;
import naver.mail.g6g6g63216.vo.UserVO;

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
		
				
				PostingVO p = new PostingVO(seq, title, writer, content, date, viewcount);
				
				return p;
			}
			
		});
		
		return postings;
	}
	
	
	public void insertPost(String title, String writer,String content){
		String sql = "insert into postings (title,writer,content) values (?,?,?)";
        template.update(sql, new Object[]{title,2,content});
          		
	}
	
	


	
	public PostingVO findBySeq(Integer postseq) {
		String sql = "select * from postings where seq = ?";
		List<PostingVO> postings = template.query(sql,new Object[]{postseq}, new RowMapper<PostingVO>(){
         
			
			@Override
			public PostingVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                
				int seq = rs.getInt("seq");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String content = rs.getString("content");
				String date = rs.getString("date");
				int viewcount = rs.getInt("viewcount");
				
				PostingVO p = new PostingVO(seq, title, writer, content, date, viewcount);
				
				return p;
			}
			
		});
		
		return postings.get(0);
	}
	
	
	public UserVO findUserByseq (Integer postseq){
		String sql ="select * from users where users.seq in (select postings.writer from postings where postings.seq = ?)";
	    List<UserVO> user = template.query(sql, new Object[]{postseq}, new RowMapper<UserVO>(){

			@Override
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				 String email = rs.getString("email");
				 String pass = rs.getString("pass");
				 Integer seq = rs.getInt("seq");
				  
				 UserVO u = new UserVO(seq, email, pass);
				 
				return u;
			}
	    	
	    });
		return user.get(0);
	}
	
	
	public int getCountPostings() {
		 String sql = "select count(*) from postings";
		 int num = template.queryForInt(sql);
		 return num;
	}
	
	
	public int deletePost(int postnum){
		System.out.println(postnum + "postnum이 뭔가요");
		String sql = "delete from postings where seq = ?";
		int n = template.update(sql, new Object[]{postnum});
		System.out.println("삭제된 posting 개수 : " + n );
		
		return n;
	}


	
	
}
