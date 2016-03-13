package naver.mail.g6g6g63216.vo;

public class UserVO {
	private Integer seq;
	private String email;
	private String password;

	public UserVO(Integer seq2, String email2, String password2) {
		this.seq = seq2;
		this.email = email2;
		this.password = password2;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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
