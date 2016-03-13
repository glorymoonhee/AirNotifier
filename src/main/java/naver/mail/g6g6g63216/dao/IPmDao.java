package naver.mail.g6g6g63216.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import naver.mail.g6g6g63216.vo.PlaceVO;
import naver.mail.g6g6g63216.vo.PmData;

public interface IPmDao {

	void setJdbcTemplate(JdbcTemplate template);

//	String getApiKey();
//
//	void setApiKey(String apiKey);

	/**
	 * 주어진 시도의 평균 대기 정보를 구합니다.
	 * @param sido
	 * @return
	 */
	PmData getAvrData(String sido);

	List<PmData> queryBySido(String sido);

	/**
	 * 측정소별 대기오염 정보를 조회합니다.
	 * @param stationName 측정소 이름
	 * @return
	 * @throws IOException
	 */
	List<PmData> queryByStation(String stationName);

	List<PlaceVO> findAllPlaces();

}