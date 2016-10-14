package naver.mail.g6g6g63216.dao;

import java.util.List;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.StationVO;
import naver.mail.g6g6g63216.vo.UserVO;

public interface IUserDao {
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public UserVO insertUser ( String email, String password,String name, String phonenumber);
	public int updateUser ( String email, String password);
	public UserVO findUser(String email, String pass);
	
	public void insertStation ( Integer userSeq, StationVO station, int pm10Val);
	public void deleteStation (Integer seq ,String station_name);
	
	public List<StationVO> findplaces(Integer userSeq);
}
