package com.ydj.zhuaqu.ali1688.localrun;

import java.util.List;

import net.sf.json.JSONObject;

import com.ydj.common.dao.DaoFactory;
import com.ydj.zhuaqu.Common;
import com.ydj.zhuaqu.InitApp;
import com.ydj.zhuaqu.ali1688.Ali1688Data;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class SecondStep {

	/**
	 * @param args
	 *
	 * @author : Ares.yi
	 * @throws Exception 
	 * @createTime : 2017年4月18日 上午10:51:44
	 */
	public static void main(String[] args) throws Exception {
		new InitApp();
		
		Ali1688Data data = null ;
		
		boolean flag = true;
		int i = 0 ;
		
		while (flag){
			List<JSONObject> list = DaoFactory.getMyDao().getList();
			
			
			for(JSONObject one : list){
				int id = one.getInt("id");
				String storeURL = one.getString("storeURL");
				
				System.out.println(id+" "+storeURL);
				
				data = new Ali1688Data();
				
				OneStep.getContactInfo(data, storeURL);
				
				if("".equals(data.getTel())){
					i++;
				}else{
					if( i > 0){
						i--;
					}
				}
				
				if( i > 30){
					System.err.println("连续"+i+"次未抓到联系方式，程序自动退出，请检查是否需要输入验证码");
					flag = false;
					break;
				}
				
				DaoFactory.getMyDao().update(id, data.getContact(), data.getTel());
				
				try {
					Thread.sleep(Common.getRandomNumber(1000, 5000));
				} catch (Exception e) {
				}
			}
		}
		
	}

}
