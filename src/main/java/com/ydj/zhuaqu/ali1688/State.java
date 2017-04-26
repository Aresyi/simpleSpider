package com.ydj.zhuaqu.ali1688;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public enum  State {

	def,
	
	/**需要登录*/
	needSignIn,
	
	/**需要输入验证码*/
	needCheckcode,
	
	/**大于了N次失败*/
	gtFailCount;
}
