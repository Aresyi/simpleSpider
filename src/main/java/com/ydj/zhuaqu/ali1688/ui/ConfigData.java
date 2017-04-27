package com.ydj.zhuaqu.ali1688.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ydj.common.kit.Toolbox;
import com.ydj.zhuaqu.dao.DaoFactory;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class ConfigData {

	private static final Object[] ALERT_SET = new Object[]{5,10,15,20,25,30,50,100,200}; 
	private static final Object[] FREQUENCY_SET = new Object[]{"500毫秒~1秒","1秒~3秒","1秒~5秒","2秒~6秒","6秒~10秒","20秒~30秒","20秒~60秒"};
	
	
	private static Map<String,JSONObject> TEMP = new HashMap <String,JSONObject>();
	
	
	public static Object[] getAlertSet(){
		return ALERT_SET;
	}
	
	public static Object[] getAlertSet(int one){
		List<Integer> res = new ArrayList<Integer>();
		res.add(0, one);
		
		for(Object obj : ALERT_SET){
			
			int i = Integer.parseInt(obj.toString());
			
			if(i == one){
				continue ;
			}
			
			res.add(i);
		}
		
		return res.toArray();
	}
	
	public static Object[] getFrequencySet(){
		return FREQUENCY_SET;
	}
	
	
	public static Object[] getSpiders(){
		List<String> res = new ArrayList<String>();
		res.add("");
		
		try {
			List<JSONObject> list = DaoFactory.getMyDao().getUserInfoList();
			for(JSONObject one : list){
				String userName = one.getString("userName");
				res.add(userName);
				
				TEMP.put(userName, one);
			}
		} catch (Exception e) {
		}
		
		return res.toArray();
	}
	
	
	public static int getZhuaquSumCount(){
		try {
			return TEMP.get(Constant.currentUser).getInt("zhuaquCount");
		} catch (Exception e) {
		}
		
		return 0;
	}
	
	public static JSONObject getUserInfo(String userName){
		try {
			return TEMP.get(userName);
		} catch (Exception e) {
		}
		
		return null;
	}
	
	
	 public static void frequencySet(String choose){
	    	
	    	if("500毫秒~1秒".equals(choose)){
	    		Constant.frequencyMin = 500;
	    		Constant.frequencyMax = 1000;
	    	}
	    	
	    	if("1秒~3秒".equals(choose)){
	    		Constant.frequencyMin = 1000;
	    		Constant.frequencyMax = 3000;
	    	}
	    	
	    	if("1秒~5秒".equals(choose)){
	    		Constant.frequencyMin = 1000;
	    		Constant.frequencyMax = 5000;
	    	}
	    	
	    	if("2秒~6秒".equals(choose)){
	    		Constant.frequencyMin = 2000;
	    		Constant.frequencyMax = 6000;
	    	}
	    	
	    	if("6秒~10秒".equals(choose)){
	    		Constant.frequencyMin = 6000;
	    		Constant.frequencyMax = 10*1000;
	    	}
	    	
	    	if("20秒~30秒".equals(choose)){
	    		Constant.frequencyMin = 20*1000;
	    		Constant.frequencyMax = 30*1000;
	    	}
	    	
	    	if("20秒~60秒".equals(choose)){
	    		Constant.frequencyMin = 20*1000;
	    		Constant.frequencyMax = 60*1000;
	    	}
	    	
	}
	 
	 
	public static void setConfig(String userAgent,String cookie,Object alertSet,Object frequencySet){
		
		if(Toolbox.isNotEmpty(cookie)){
			Constant.cookie = cookie;
		}
		
    	if(Toolbox.isNotEmpty(userAgent)){
    		Constant.userAgent = userAgent;
    	}
    	
    	Constant.alertCount = Integer.parseInt(alertSet.toString());
    	
    	frequencySet(frequencySet.toString());
    	
    	try {
			DaoFactory.getMyDao().updateUserConfig(Constant.userAgent, Constant.cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
