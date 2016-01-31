package naver.mail.g6g6g63216.util;

public class Util {
//  데이터가 -- 로 전달된 경우. 
	public static int s2i( String numForm, int defaultValue ){
		try {
			return Integer.parseInt(numForm);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
