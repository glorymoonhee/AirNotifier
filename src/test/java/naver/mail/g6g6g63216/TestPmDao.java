package naver.mail.g6g6g63216;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import naver.mail.g6g6g63216.dao.IPmDao;
import naver.mail.g6g6g63216.dao.PmDao;
import naver.mail.g6g6g63216.vo.PmData;

public class TestPmDao {

	@Test
	public void test() {
		PmDao dao = new PmDao();
		dao.setApiKey("9uEAG%2BU2%2BHVtKWKAhRYggrPYT7ZlzsITWSMOhNTf%2BCbYsUXW8Q36H9bq6xRp1P9l9Tdclwc%2FjndIGvPvtKBm4A%3D%3D");
		List<PmData> dataList = dao.queryBySido("서울");
		System.out.println(dataList);
		assertEquals ( 39, dataList.size());
		PmData seoul = dao.getAvrData("서울");
		System.out.println(seoul);
		
	}
	
	
}
