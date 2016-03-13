package naver.mail.g6g6g63216.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import naver.mail.g6g6g63216.vo.StationVO;

public interface IStationDao {

	void setApiKey(String key);

	/**
	 * 시도 이름을 받아서 그 지역내의 관측소 이름만 반환합니다. 
	 * @param sidoName
	 * @return
	 */
	List<String> findStationsBySido(String sidoName);

	List<StationVO> findStationsBySido2(String sidoName);

	/**
	 * 주어진 관측소 이름으로 관측서 정보를 조회합니다.
	 * @param stationName
	 * @return
	 */
	StationVO findStationsByName(String stationName);

}