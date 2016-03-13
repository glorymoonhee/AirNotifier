package naver.mail.g6g6g63216.vo;

public class PmData {
	
	private String pm100; // "--"
	private int pm100Grade; // 0~5
	
	private String pm025;
	private int pm025Grade; // 0~5
	
	private String dataTime ;
	
	
	
	public PmData(String pm100, int pm100Grade, String pm025, int pm025Grade, String dataTime) {
		super();
		this.pm100 = pm100;
		this.pm100Grade = pm100Grade;
		this.pm025 = pm025;
		this.pm025Grade = pm025Grade;
		this.dataTime = dataTime;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getPm100() {
		return pm100;
	}
	
	public String getPm025() {
		return pm025;
	}
	
	public int getPm100Grade() {
		return pm100Grade;
	}

	public int getPm025Grade() {
		return pm025Grade;
	}

	@Override
	public String toString() {
		return "PmData [pm10=" + pm100 + ", pm25=" + pm025 + "]";
	}
	
	
}
