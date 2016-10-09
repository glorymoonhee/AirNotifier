package naver.mail.g6g6g63216.vo;

public class PostingVO {
	 private int seq;
	 private String title;
	 private String writer;
	 private String content;
	 private String dateTime;
	 private int viewcount;

	
	
	
	public PostingVO(int seq, String title, String writer, String content, String dataTime, int viewcount) {
		super();
		this.seq = seq;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.dateTime = dataTime;
		this.viewcount = viewcount;
	}



	public int getViewcount() {
		return viewcount;
	}



	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dataTime) {
		this.dateTime = dataTime;
	}
	  
	 
	 
	 
	 
}
