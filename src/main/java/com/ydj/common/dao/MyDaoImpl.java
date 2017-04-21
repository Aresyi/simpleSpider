/** **/
package com.ydj.common.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.ydj.common.JSONPropertyRowMapper;
import com.ydj.common.MultiDataSourceDaoSupport;
import com.zhuaqu.ali1688.ui.Common2;

/**
 * 
 * @author : Ares.yi
 * @createTime : 2013-8-15 下午09:19:04
 * @version : 1.0
 * @description :
 *
 */
public class MyDaoImpl extends MultiDataSourceDaoSupport implements MyDao {


	@Override
	public void save(int typeOf,String company,String storeURL,String mainProduct,String address,String bussModel,String contact,String tel) {
		
		long now = System.currentTimeMillis();
		
		long updateTime = 0;
		
		if(tel != null && !"".equals(tel)){
			updateTime = now;
		}
		
		this.getJdbcTemplate()
        .update("INSERT INTO info_1688(typeOf,company,storeURL,mainProduct,address,bussModel,contact,tel,createTime,updateTime) VALUES(?,?,?,?,?,?,?,?,?,?)",
            new Object[] { typeOf,company,storeURL,mainProduct,address,bussModel,contact,tel,now,updateTime});
	}

	
	
	@Override
	public List<JSONObject> getList()throws Exception {
		 return this.getJdbcTemplate().query("select id,storeURL from info_1688 where updateTime=0 and updateCount<3 ORDER BY RAND() limit 20",new JSONPropertyRowMapper());
	}



	@Override
	public void update(int id,String contact, String tel) {
		
		long now = System.currentTimeMillis();
		
		long updateTime = 0;
		
		if(Common2.isNotEmpty(tel)){
			updateTime = now;
		}
		
		this.getJdbcTemplate()
        .update("update info_1688 set contact=?,tel=?,updateTime=?,updateCount=updateCount+1 where id = ?",
            new Object[] { contact,tel,updateTime,id});
		
	}



	@Override
	public int getAllCount() {
		return this.getJdbcTemplate().queryForObject("SELECT COUNT(id) FROM info_1688 WHERE tel<>''",Integer.class);
	}
	
	
	
}
