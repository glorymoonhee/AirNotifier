package naver.mail.g6g6g63216.dao;

import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

public interface IUserDao {
	/**
	 *  회원 가입 처리
	 * @param email
	 * @param password
	 * @return
	 */
	public UserVO insertUser ( String email, String password);

	public UserVO findUser(String email, String pass);
	
	public void insertStation ( Integer userSeq, StationVO station);
}
