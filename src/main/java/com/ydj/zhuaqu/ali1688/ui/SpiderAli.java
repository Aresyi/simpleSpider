package com.ydj.zhuaqu.ali1688.ui;

import java.util.List;

import net.sf.json.JSONObject;

import com.ydj.common.MyLog;
import com.ydj.common.dao.DaoFactory;
import com.ydj.zhuaqu.Common;
import com.ydj.zhuaqu.ali1688.Ali1688Data;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class SpiderAli {
	
	
	private boolean isRun = true;
	
	int sum = 0;
	int success = 0;
	int fail = 0;
	
	
	public SpiderAli() {
	}
	
	public void start() throws Exception{
		
		this.isRun = true;
		
		Ali1688Data ali1688Data = null ;
		
		int alert = 0;
		
		int i = 0 ;
		
		while (isRun){
			List<JSONObject> list = DaoFactory.getMyDao().getList();
			
			for(JSONObject one : list){
				
				if(!isRun){
					break;
				}
		
				String fileName = Toolbox.getDateString("yyyyMMddHH", System.currentTimeMillis())+".log";
				
				sum++;
				
				int id = one.getInt("id");
				String storeURL = one.getString("storeURL");
				
				ali1688Data = new Ali1688Data();
				
				
				
				SpiderAli1688.getContactInfo(ali1688Data, storeURL);
				
				
				String txt = id+Constant.TAB+storeURL+Constant.TAB+ali1688Data.getContact()+Constant.TAB+ali1688Data.getTel() ;
				
				if( Toolbox.isEmptyString(ali1688Data.getTel()) ){
					i++;
					fail++;
					
					MyLog.logError(txt);
				} else {
					
					success++;
					
					if( i > 0){//成功一次 便置零
						i = 0;
					}
					
					Toolbox.save2File(Constant.savePath, fileName, txt,"UTF-8");
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
				
				DaoFactory.getMyDao().update(id, ali1688Data.getContact(), ali1688Data.getTel());
				
				try {
					Thread.sleep(Common.getRandomNumber(Constant.frequencyMin, Constant.frequencyMax));
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void stop() {
		this.isRun = false;
	}
	
	
	private boolean isContinue(int alert){
		
		if(Constant.state == State.needSignIn){
			MyLog.logError("抓取已经暂停，需要在‘浏览器’【重新登录】");
			return false;
		}
		
		if(Constant.state == State.needCheckcode){
			MyLog.logError("抓取已经暂停，需要在‘浏览器’【输入验证码】");
			return false;
		}
		
		if( alert > Constant.alertCount){
			Constant.state = State.gtFailCount;
			MyLog.logError("连续"+alert+"次未抓到联系方式，抓取已暂停，请检查是否需要【手动输入验证码】或【重新设置Cookie】或【重新登录】");
			
			return false;
		}
		
		return true;
	}
	
	public boolean isRun() {
		return isRun;
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
