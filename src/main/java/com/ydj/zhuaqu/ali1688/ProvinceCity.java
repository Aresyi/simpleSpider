package com.ydj.zhuaqu.ali1688;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ydj.common.kit.MyLog;

/**
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42
 * @version : 1.0
 * @description :
 *
 */
public class ProvinceCity {

	private final static Map<String, List<String>> temp = new HashMap<String, List<String>>();

	static {

		try {

			InputStream is = ProvinceCity.class.getResourceAsStream("myimprovecity.txt");

			InputStreamReader read = new InputStreamReader(is, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {

				String[] ary = lineTxt.split("\t");

				String key = ary[0];
				List<String> list = new ArrayList<String>();

				for (int i = 1; i < ary.length; i++) {
					list.add(ary[i]);
				}

				temp.put(key, list);

			}
			read.close();
		} catch (Exception e) {
			MyLog.logError("读取文件[myimprovecity.txt]出错");
			e.printStackTrace();
		}

	}
	
	
	public static Map<String, List<String>> getProvinceCity(){
		return temp;
	}
	

	public static Set<String> getProvinceList(){
		return temp.keySet();
	}
	
	public static void main(String[] args) {
		MyLog.logInfo(temp.size());
	}

}
