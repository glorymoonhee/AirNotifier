package naver.mail.g6g6g63216;

import java.util.List;

import naver.mail.g6g6g63216.dao.PmDao;
import naver.mail.g6g6g63216.vo.PmData;

public class AirService {
	private PmDao pmDao;
	
	public void setPmDao ( PmDao dao) {
		this.pmDao = dao;
	}
	
	public List<PmData> getDataByStation(String stationName) {
		return pmDao.queryByStation(stationName);
	}
}
