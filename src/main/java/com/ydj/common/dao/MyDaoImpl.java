/** **/
package com.ydj.common.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.ydj.common.Constant;
import com.ydj.common.JSONPropertyRowMapper;
import com.ydj.common.MultiDataSourceDaoSupport;
import com.zhuaqu.ali1688.ui.Toolbox;

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
		
		int count = this.getJdbcTemplate().queryForObject("SELECT COUNT(id) FROM info_1688 WHERE storeURL=?",new Object[] {storeURL},Integer.class);
		
		if(count > 0){//此前未建立唯一索引  不知道不同分类中会抓到相同的店铺
			return ;
		}
		
		this.getJdbcTemplate()
        .update("INSERT INTO info_1688(typeOf,company,storeURL,mainProduct,address,bussModel,contact,tel,createTime,updateTime,spider) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
            new Object[] { typeOf,company,storeURL,mainProduct,address,bussModel,contact,tel,now,updateTime,Constant.currentUser});
	
//		this.getJdbcTemplate()
//        .update("update user_info set updateTime=?,zhuaquCount=zhuaquCount+1 where userName = ?",
//            new Object[] { now,Constant.currentUser});
	
	}

	
	
	@Override
	public List<JSONObject> getList()throws Exception {
		 return this.getJdbcTemplate().query("select id,storeURL from info_1688 where updateTime=0 and updateCount<3 ORDER BY RAND() limit 20",new JSONPropertyRowMapper());
	}



	@Override
	public int update(int id,String contact, String tel) {
		
		long now = System.currentTimeMillis();
		
		long updateTime = 0;
		
		if(Toolbox.isNotEmpty(tel)){
			updateTime = now;
		}
		
		this.getJdbcTemplate()
        .update("update info_1688 set contact=?,tel=?,updateTime=?,updateCount=updateCount+1,spider=? where id = ?",
            new Object[] { contact,tel,updateTime,Constant.currentUser,id});
		
		this.getJdbcTemplate()
        .update("update user_info set updateTime=?,zhuaquCount=zhuaquCount+1 where userName = ?",
            new Object[] { now,Constant.currentUser});
		
		return 1;
	}



	@Override
	public int getAllCount() {
		return this.getJdbcTemplate().queryForObject("SELECT COUNT(id) FROM info_1688 WHERE tel<>''",Integer.class);
	}



	@Override
	public List<JSONObject> getUserInfoList() throws Exception {
		return this.getJdbcTemplate().query("select * from user_info",new JSONPropertyRowMapper());
	}



	@Override
	public int updateUserConfig(String userAgent, String cookie) {
		
		String savePath = Constant.savePath;
		
		if(savePath.contains("/")){
			savePath = savePath.substring(0, savePath.indexOf("/"));
		}
		
		this.getJdbcTemplate().update("update user_info set userAgent=?,cookie=?,savePath=? where userName = ?",new Object[] { userAgent,cookie,savePath,Constant.currentUser});
		return 0;
	}
	
	
	
	
}
