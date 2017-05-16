package com.ydj.zhuaqu.ali1688.local.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import com.ydj.common.kit.MyLog;
import com.ydj.common.kit.Toolbox;
import com.ydj.zhuaqu.InitApp;
import com.ydj.zhuaqu.ali1688.ProvinceCity;
import com.ydj.zhuaqu.dao.DaoFactory;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class RinseData {
	
	private static List<JSONObject> getData(int currentId) throws Exception{
		List<JSONObject> list = DaoFactory.getMyDao().getListByLimitPage(currentId);
		
		return list;
	}
	
	
	private static int processData(List<JSONObject> list){
		if(list == null || list.isEmpty()){
			return Integer.MAX_VALUE;
		}
		
		
		int maxId = Integer.MAX_VALUE ;
		
		for(int i = 0 ; i < list.size() ; i++){
			
			JSONObject json = list.get(i);
			
			int id = json.optInt("id");
			String company = json.optString("company");
			String address = json.optString("address");
			String contact = json.optString("contact");
			String tel = json.optString("tel");
			
			String province = "";
			String city = "";
			
			List<String> cityList = new ArrayList<String>();
			
			for(String s : ProvinceCity.getProvinceList()){
				if(address.contains(s)){
					province = s;
					cityList = ProvinceCity.getProvinceCity().get(s);
					break;
				}
			}
			
			for(String s : cityList){
				if(address.contains(s)){
					city = s;
					break;
				}
			}
			
			contact = Toolbox.cleanContactInfo(contact);
			
			Set<String> tels = Toolbox.cleanTelInfo(tel);
			
			if(tels.isEmpty()){
				tel = "";
			}
			
			int ii = 0;
			String otherPhone1st = "";
			for(String s : tels){
				if(ii == 0){
					tel = s;
				}
				if(ii == 1){
					otherPhone1st = s;
				}
				ii++;
			}
			
			maxId = id;
			MyLog.logInfo(id+"---->\t"+company+"\t"+address+"\t"+province+"\t"+city+"\t"+contact+"\t"+tel+"\t"+otherPhone1st);
			
			DaoFactory.getMyDao().update(id, contact, city, province, tel);
		}
		
		return maxId;
	}
	
	public static void main(String[] args) throws Exception {
		
		new InitApp();
		
		List<JSONObject> list = getData(0);
		
		while(true){
			int maxId = processData(list);
			
			if(maxId == Integer.MAX_VALUE){
				break;
			}
			
			list = getData(maxId);
		}
		
	}

}
