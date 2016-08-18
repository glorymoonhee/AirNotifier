package naver.mail.g6g6g63216.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="rawDataLoader")
public class AirDataLoader {

    private Logger logger = LoggerFactory.getLogger(AirDataLoader.class);

    @Value("${apikey}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey( String apiKey) {
        System.out.println("    API KEY : " + apiKey );
        this.apiKey = apiKey;
    }


    public String loadAirData ( String sido ) {
        return loadAirData(sido, 5*1000);
    }

    public String loadAirData( String sido, long timeout ) {
        String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc"
                + "/getCtprvnRltmMesureDnsty?"
                + "ServiceKey=${key}&"
                + "numOfRows=100&pageSize=50&pageNo=1&startPage=1&"
                + "sidoName=${sido}"
                + "&ver=1.0";

        try {
            uri = uri.replace("${sido}", URLEncoder.encode(sido, "utf-8"))
                     .replace("${key}", this.apiKey);
            logger.info("[" + sido + "] " + uri);

            org.jsoup.Connection con = Jsoup.connect(uri);
            con.timeout((int)timeout);
            return con.execute().body();
        } catch ( UnsupportedEncodingException e) {
            throw new RuntimeException ( e );
        } catch (IOException e) {
            throw new APICallException ( e );
        }
    }
}