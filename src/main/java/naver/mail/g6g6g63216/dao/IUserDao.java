package naver.mail.g6g6g63216.dao;

import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

public interface IUserDao {
	/**
	 *  ȸ�� ���� ó��
	 * @param email
	 * @param password
	 * @return
	 */
	public UserVO insertUser ( String email, String password);

	public UserVO findUser(String email, String pass);
	
	public void insertStation ( Integer userSeq, StationVO station);
}
