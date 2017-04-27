/** **/
package com.ydj.zhuaqu.dao;


import com.ydj.zhuaqu.SystemContext;

/**
 * 
 * @author : Ares.yi
 * @createTime : 2013-8-15 下午09:26:09
 * @version : 1.0
 * @description :
 *
 */
public class DaoFactory {

	private static MyDao myDao ;
	
	private DaoFactory(){
	}
	
	public static MyDao getMyDao(){
		return myDao;
	}
	
	static{
		myDao = getBean("myDao", MyDao.class);
	}
	
	private static <T> T getBean(String name,Class<T> clazz){
		return SystemContext.context.getBean(name,clazz);
	}
}
