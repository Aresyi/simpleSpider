/** **/
package com.ydj.zhuaqu.dao;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 
 * @author : Ares.yi
 * @createTime : 2013-8-15 下午09:17:31
 * @version : 1.0
 * @description :
 *
 */
public interface MyDao {

	public int save(int typeOf,String company,String storeURL,String mainProduct,String address,String bussModel,String contact,String tel,String iuCode);
	
	public List<JSONObject> getList()throws Exception;
	
	public List<JSONObject> getListByLimitPage(int currentId)throws Exception;
	
	public int update(int id,String contact, String city,String province,String mobilePhone);
	
	public int update(int id,String contact,String tel);
	
	public int getAllCount();
	
	public List<JSONObject> getUserInfoList()throws Exception;
	
	public int updateUserConfig(String userAgent,String cookie);
	
	
	public int saveStore(String keyword,String startURL,String iuCode,String iuName);
	
	public JSONObject getStore(String startURL);
	
	public int updateStore(String startURL,int spiderCount);
	
	
	public List<JSONObject> getUserSpiderInfoReport()throws Exception;
	
	public List<JSONObject> getCategorySpiderReport()throws Exception;
	
	public List<JSONObject> getReport()throws Exception;
	
	
	public List<JSONObject> getMyCategoryReport()throws Exception;
	
	public int updateZero();
	
}
