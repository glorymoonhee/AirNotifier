package naver.mail.g6g6g63216.vo;

public class UserVO {
	private Integer seq;
	private String email;
	private String password;
    
	private Integer pm100; //이거 추가해도 되나 모르겠음.
	
    
	
	
	public UserVO(Integer seq2, String email2, String password2) {
		this.seq = seq2;
		this.email = email2;
		this.password = password2;
	}

	
	////////////////////////////////////////////////이것도 모름
	public UserVO(Integer seq, Integer pm100) {
		super();
		this.seq = seq;
		this.pm100 = pm100;
	}



	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getPm100() {
		return pm100;
	}

	public void setPm100(Integer pm100) {
		this.pm100 = pm100;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserVO [seq=" + seq + ", email=" + email + ", password=" + password + "]";
	}
	
}
