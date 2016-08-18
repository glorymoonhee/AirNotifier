package naver.mail.g6g6g63216.util;

import java.security.MessageDigest;

public class Util {
	
	public static int s2i( String numForm, int defaultValue ){
		try {
			return Integer.parseInt(numForm);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static String encode ( MessageDigest md, String plainText ) {
		md.update( plainText.getBytes());
		byte [] digested = md.digest();
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < digested.length ; i++){
			sb.append(Integer.toString((digested[i]&0xff) + 0x100, 16).substring(1));
		}
		
		return sb.toString();
	}
}
