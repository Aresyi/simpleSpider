package com.ydj.zhuaqu.ali1688.local.run;

import java.util.ArrayList;
import java.util.List;

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
public class HuiCong {

	
	private static List<JSONObject> getData(int currentId) throws Exception{
		List<JSONObject> list = DaoFactory.getMyDao().getHuiCongDataListByLimitPage(currentId);
		
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
			String location = json.optString("location");
			String contactsUser = json.optString("contactsUser");
			String mobile = json.optString("mobile");
			String industrytwo = json.optString("industrytwo");
			
			if(Toolbox.isEmptyString(mobile)){
				continue;
			}
			
			if(Toolbox.isEmptyString(address)){
				address = location;
			}
			
			String chineseName = contactsUser.replace("有限公司", "").replace(" 女士", "").replace(" 先生", "");
			String personIUCode = industrytwo;
			String companyAddress = address.replace("地址：", "").replace(":", "").replace("中国", "").replace("中国 ", "").replace("： ", "").replace("：", "").replace("：", "");
			String province = "";
			String city = "";
			
			
			if(chineseName.length() == 1){
				chineseName = chineseName+"先生";
			}
			
			if(Toolbox.isNotEmpty(companyAddress)){
				List<String> cityList = new ArrayList<String>();
				
				for(String s : ProvinceCity.getProvinceList()){
					if(companyAddress.contains(s)){
						province = s;
						cityList = ProvinceCity.getProvinceCity().get(s);
						break;
					}
				}
				
				for(String s : cityList){
					if(companyAddress.contains(s)){
						city = s;
						break;
					}
				}
			}
			
			if(Toolbox.isEmptyString(province) && Toolbox.isNotEmpty(location)){
				List<String> cityList = new ArrayList<String>();
				
				for(String s : ProvinceCity.getProvinceList()){
					if(location.contains(s)){
						province = s;
						cityList = ProvinceCity.getProvinceCity().get(s);
						break;
					}
				}
				
				for(String s : cityList){
					if(location.contains(s)){
						city = s;
						break;
					}
				}
			}
			
			String  mobilePhone="", otherPhone1st="", otherPhone2nd="", otherPhone3rd="";
			String[] allMobile = mobile.replace(" ", "").split(",");
			
			if(allMobile.length == 1){
				mobilePhone = allMobile[0];
			}else if(allMobile.length == 2){
				mobilePhone = allMobile[0];
				otherPhone1st = allMobile[1];
			}else if(allMobile.length == 3){
				mobilePhone = allMobile[0];
				otherPhone1st = allMobile[1];
				otherPhone2nd = allMobile[2];
			}else if(allMobile.length > 3){
				mobilePhone = allMobile[0];
				otherPhone1st = allMobile[1];
				otherPhone2nd = allMobile[2];
				otherPhone3rd = allMobile[3];
			}
			
			
			maxId = id;
			MyLog.logInfo(id+"---->\t"+company+"\t"+address+"\t"+province+"\t"+city+"\t"+chineseName+"\t"+mobilePhone+"\t"+otherPhone1st);
			
			try {
				DaoFactory.getMyDao().insertHuiCongData(chineseName, company, companyAddress, province, city, mobilePhone, otherPhone1st, otherPhone2nd, otherPhone3rd, personIUCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
