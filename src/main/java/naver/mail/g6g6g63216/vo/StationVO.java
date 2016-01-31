package naver.mail.g6g6g63216.vo;

public class StationVO {
	private String name;
	private String addr;
	private String lat;
	private String lng;
	public StationVO(String name, String addr, String lat, String lng) {
		super();
		this.name = name;
		this.addr = addr;
		this.lat = lat;
		this.lng = lng;
	}
	public String getName() {
		return name;
	}
	public String getAddr() {
		return addr;
	}
	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
	}
	
	@Override
	public String toString() {
		return "StationVO [name=" + name + ", addr=" + addr + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
	
	
}
