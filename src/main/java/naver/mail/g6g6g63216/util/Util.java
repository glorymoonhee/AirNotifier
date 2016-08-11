package naver.mail.g6g6g63216.util;

public class Util {
	
	public static int s2i( String numForm, int defaultValue ){
		try {
			return Integer.parseInt(numForm);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
