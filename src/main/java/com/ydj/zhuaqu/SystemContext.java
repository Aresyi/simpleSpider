/** **/
package com.ydj.zhuaqu;

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
	
    static void init(){
    	try {
    		context = new ClassPathXmlApplicationContext("classpath*:business-datasource.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
