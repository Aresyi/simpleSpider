package com.zhuaqu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class Common {
	
	public final static String TAB = "\t"; 
	
	public static int getRandomNumber(int min, int max) {

		if (min >= max)
			return max;

		if (min + 1 == max)
			return min;

		// long to int
		return (int) Math.round(Math.random() * (max - min) + min);

	}
	
	public static String getHtmlContent(String url, int connectTimeout,
			int readTimeout, String charset) {
		
		if(url == null || (!url.startsWith("https://") && !url.startsWith("http://")) ){
			return "";
		}

		StringBuffer inputLine = new StringBuffer();

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
					.openConnection();

			HttpURLConnection.setFollowRedirects(true);
			urlConnection.setConnectTimeout(connectTimeout);
			urlConnection.setReadTimeout(readTimeout);
			
			urlConnection.setRequestProperty("Connection","keep-alive");
			urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConnection.setRequestProperty("Cookie","cna=aATkEMsbBQ4CAYzOsO4Va2Po; alicnweb=touch_tb_at%3D1492483831720%7Chp_newbuyerguide%3Dtrue%7Clastlogonid%3Dyidej; l=AtHRD/N6IYYtIsh2mFXlva954ddrCEWw; isg=AtfX-lObQ9v9U8dSZN9rGvtPZ0sng6t-lHKUsSkEyqYNWPeaMew7zpV6zEM4; ali_beacon_id=58.247.112.82.1492155919756.578154.9; UM_distinctid=15b6b72e8129-0994a8e6af019e8-1262694a-1fa400-15b6b72e813429; ali_ab=58.247.112.82.1492156345558.0; ali_apache_track=\"c_ms=1|c_mid=b2b-448694704|c_lid=yidej\"; _cn_slid_=oR9YnG3Kbf; last_mid=b2b-448694704; __last_loginid__=yidej; alisw=swIs1200%3D1%7C; ad_prefer=\"2017/04/18 11:30:47\"; h_keys=\"%u9488%u7ec7%u886b#%u5973%u5f0fT%u6064#%u5973%u88c5\"; JSESSIONID=8L78AGZv1-SqVXbNfrwZ3AdwfNn5-bx0okGQ-LRn1; _csrf_token=1492425570104; _tmp_ck_0=Us6kfQ%2FxEOMJsqqu5vi%2FKDoUxV%2Ber6ANX4RVtkITUU4j%2BRGC0WMNDkYG2xxQjsg7et1%2B0QA5WWDcA24Zxq22Dx8pd6Geuv1YQIWxZYuz%2BR3rkgYtJVHB3BUF0v%2BbpbqZcROFN9pgfXPPjn9hPu6IMEvS09%2BGBzoGbLMrz%2F9kJo1ftvUQIFA1cowisHst6XwvElpQnG8OacuIcHrKR7EVm1C3UAahR1oYDs1x%2BNxH4UkGVtAzllWrFL%2B602WeHRifZ50kpW4E1u7IfPEsMyujzFHZdt72SYTva7x7sbyaKtgPDYtthtarsNaqEMynhfhZ%2F6xIYMQ2MYd%2BQnwdT9EtEDOpMslWHSP1ebVsWUeyVnwd8joTi04Dvl25X5GrZ1rSDGTu1W4ioUUKd3IkrXjl%2FUX%2Ftzej8qmrVGHOhronxwLzf2TyZvG4y7R6dWz%2Fp5V6JmpMNFAG%2FB%2B521HSRW6SFCAsKclA2qZM3TurTMZiUjRQPxYtpyapoaLT7F%2FWK9uC4N8WVliiMACEVO9fZIb%2BsxXkaQ6H2vspGq7sTBlo8Xw%3D; _ITBU_IS_FIRST_VISITED_=ali0351%3Apm0iehjk88; ali_apache_tracktmp=\"c_w_signed=Y\"; _nk_=ZoRzR58gc8I%3D; tbsnid=IxWbHQSbsP0SGZ%2Bs7c0YPkppXdA7rg9JbTv7jXp51046sOlEpJKl9g%3D%3D; LoginUmid=\"xcPp4VBGfMVPQDJ3vMiz6YTjEN794nIUNbT6UWjKkk8ogBSl%2FbuCDg%3D%3D\"; userID=\"RjxqNVNTkQ6cwrfA0X%2BjY0iLyDfg7Rak2PZEg0Y4jRg6sOlEpJKl9g%3D%3D\"; userIDNum=3JO27xBdmfIDBPJBu4sTUQ%3D%3D; login=\"kFeyVBJLQQI%3D\"; __cn_logon__=true; __rn_alert__=false; _is_show_loginId_change_block_=b2b-448694704_false; _show_force_unbind_div_=b2b-448694704_false; _show_sys_unbind_div_=b2b-448694704_false; _show_user_unbind_div_=b2b-448694704_false; __cn_logon_id__=yidej; cn_tmp=\"Z28mC+GqtZ0Ldvy+h3uXO4uwk+LCnw9n15V6pHYItoNGv+JDzDL6UaJuOmzoiEESU2etvy4NuGsq2juFCIhFdomeQ1KcMOkn1Tp09HcapmEowMiB+z4LgxsRAz4r+8M7Kzx+ALbvPRuBVX4VhD3PKu/lmn1hfGfXHFsYu7GEmARpDTCNAi9CgcNXFhTv2/A46tyDoszZt/rp/T4AYawpukur/Xs+nHVwP6EvaKtB2NY=\"; unb=448694704");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));

			String str;
			while ((str = in.readLine()) != null)
				inputLine.append(str).append("\r\n");

			in.close();
		} catch (Exception e) {
			//e.printStackTrace();
			//System.err.println(url);
		}

		return inputLine.toString();
	}
	
	
	public static String getHtmlContent(String url) {
		
		String html = getHtmlContent(url,5000,5000,"GBK");
		
		return html;
	}

}
