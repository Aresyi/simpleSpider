package com.ydj.zhuaqu;

import java.io.File;
import java.io.IOException;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class InitApp {

	public InitApp(){
		initSys();
	}
	
	private void initSys(){//注意顺序
		setServerHome();
		SystemContext.init();
	}

	private void setServerHome(){
		String userDir = System.getProperty("user.dir");
		String os = System.getProperty("os.name");
		
		System.out.println("{osName:"+os+",userDir:"+userDir+"}");
		
		String value = "";
		try {
			value = new File(userDir, "..").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(os.toLowerCase().contains("win")){// TEST NEED
			value = userDir;
		}
		
		System.setProperty("server.home", value);
	}
}
