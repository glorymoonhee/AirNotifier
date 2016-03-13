package naver.mail.g6g6g63216.vo;

public class StationVO {
	private Integer seq ;
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
	public StationVO(Integer seq, String name, String addr, String lat, String lng) {
		super();
		this.seq = seq;
		this.name = name;
		this.addr = addr;
		this.lat = lat;
		this.lng = lng;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
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
