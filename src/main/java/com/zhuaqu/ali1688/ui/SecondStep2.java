package com.zhuaqu.ali1688.ui;

import java.util.List;

import net.sf.json.JSONObject;

import com.ydj.common.dao.DaoFactory;
import com.ydj.simpleSpider.MyLog;
import com.zhuaqu.Common;
import com.zhuaqu.InitApp;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class SecondStep2 {
	
	
	public static  String savePath = "C:\\";
	
	public static  int alertCount = 5;
	
	
	public static  int frequencyMin = 500;
	
	public static  int frequencyMax = 1200;
	
	
	public static State state = State.def;
	
	
	
	
	boolean isRun = true;
	
	int sum = 0;
	int success = 0;
	int fail = 0;
	
	
	
	
	public SecondStep2() {
		new InitApp();
	}
	
	public void start() throws Exception{
		
		Ali1688Data ali1688Data = null ;
		
		int alert = 0;
		
		int i = 0 ;
		
		while (isRun){
			List<JSONObject> list = DaoFactory.getMyDao().getList();
			
			for(JSONObject one : list){
				
				if(!isRun){
					break;
				}
		
				String fileName = Common2.getDateString("yyyyMMddHH", System.currentTimeMillis())+".log";
				
				sum++;
				
				int id = one.getInt("id");
				String storeURL = one.getString("storeURL");
				
				ali1688Data = new Ali1688Data();
				
				
				
				OneStep2.getContactInfo(ali1688Data, storeURL);
				
				
				String txt = id+Common2.TAB+storeURL+Common2.TAB+ali1688Data.contact+Common2.TAB+ali1688Data.tel ;
				
				if( Common2.isEmptyString(ali1688Data.tel) ){
					i++;
					fail++;
					
					MyLog.logError(txt);
				} else {
					
					success++;
					
					if( i > 0){//成功一次 便置零
						i = 0;
					}
					
					Common2.save2File(savePath, fileName, txt,"UTF-8");
				}
				
				alert = i;
				
				if(alert > 50){//如果高频率失败，那么休眠10分钟，重新再来吧
					Thread.sleep(10*60*1000);
					
					i = 0 ;
				}

				if( !isContinue(alert) ){
					isRun = false;
					break;
				}
				
				DaoFactory.getMyDao().update(id, ali1688Data.contact, ali1688Data.tel);
				
				try {
					Thread.sleep(Common.getRandomNumber(frequencyMin, frequencyMax));
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void stop() {
		this.isRun = false;
	}
	
	
	private boolean isContinue(int alert){
		
		if(SecondStep2.state == State.needSignIn){
			MyLog.logError("抓取已经暂停，需要在‘浏览器’【重新登录】");
			return false;
		}
		
		if(SecondStep2.state == State.needCheckcode){
			MyLog.logError("抓取已经暂停，需要在‘浏览器’【输入验证码】");
			return false;
		}
		
		if( alert > alertCount){
			SecondStep2.state = State.gtFailCount;
			MyLog.logError("连续"+alert+"次未抓到联系方式，抓取已暂停，请检查是否需要【手动输入验证码】或【重新设置Cookie】或【重新登录】");
			
			return false;
		}
		
		return true;
	}
	

	/**
	 * @param args
	 *
	 * @author : Ares.yi
	 * @throws Exception 
	 * @createTime : 2017年4月18日 上午10:51:44
	 */
	public static void main(String[] args) throws Exception {
		
	}

}
