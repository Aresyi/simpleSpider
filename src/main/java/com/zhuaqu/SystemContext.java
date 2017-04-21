/** **/
package com.zhuaqu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author : Ares.yi
 * @createTime : 2013-8-16 下午12:24:57
 * @version : 1.0
 * @description :
 *
 */
public class SystemContext {
	public static ApplicationContext context ;
	
	private final static String  HOME_DIR = System.getProperty("server.home");
	
    static void init(){
    	try {
//    		String[] config = new String[]{
//            		"classpath:business-datasource.xml",
//            		"classpath:business-dao-context.xml"
//            };
    		context = new ClassPathXmlApplicationContext("classpath*:business-datasource.xml");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("ERR:"+e.getMessage());
			System.err.println("ERR:"+e);
			e.printStackTrace();
		}
		System.out.println("context:"+context);
	}
}
