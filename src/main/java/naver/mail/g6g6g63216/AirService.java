package naver.mail.g6g6g63216;

import java.util.List;

import naver.mail.g6g6g63216.dao.IPmDao;
import naver.mail.g6g6g63216.vo.PmData;

public class AirService {
	private IPmDao pmDao;
	
	public void setPmDao ( IPmDao dao) {
		this.pmDao = dao;
	}
	
	public List<PmData> getDataByStation(String stationName) {
		return pmDao.queryByStation(stationName);
	}
}
