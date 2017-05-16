package com.ydj.common.kit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class Toolbox {
	
	
	/**
	 * 判断Email (Email由帐号@域名组成，格式为xxx@xxx.xx)<br>
	 * 帐号由英文字母、数字、点、减号和下划线组成，<br>
	 * 只能以英文字母、数字、减号或下划线开头和结束。<br>
	 * 域名由英文字母、数字、减号、点组成<br>
	 * www.net.cn的注册规则为：只提供英文字母、数字、减号。减号不能用作开头和结尾。(中文域名使用太少，暂不考虑)<br>
	 * 实际查询时-12.com已被注册。<br>
	 * 以下是几大邮箱极限数据测试结果<br>
	 * 163.com为字母或数字开头和结束。<br>
	 * hotmail.com为字母开头，字母、数字、减号或下划线结束。<br>
	 * live.cn为字母、数字、减号或下划线开头和结束。hotmail.com和live.cn不允许有连续的句号。
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		return isEmptyString(email) ? false
				: PatternKit
						.regex("^[\\w_-]+([\\.\\w_-]*[\\w_-]+)?@[\\w-]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?$",
								email, true);
	}

	/**
	 * 从输入字符串中截取EMAIL
	 * 
	 * @param input
	 * @return
	 */
	public static String parseEmail(String input) {

		String regex = "[\\s\\p{Punct}]*([\\w_-]+([\\.\\w_-]*[\\w_-]+)?@[\\w-]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?)[\\s\\p{Punct}]*";

		return PatternKit.parseStr(input, regex, 1);
	}
	
	
	/**
	 * 判断是否为手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {

		return isEmptyString(mobile) ? false : PatternKit.regex(
				"^(\\+86(\\s)?)?0?1(3|4|5|7|8)\\d{9}$", mobile, true);

	}
	
	
	/**
	 * 将带有区号的手机号进行标准格式转化
	 * 
	 * @param mobile
	 * @return
	 */
	public static String getPhoneNumber(String phoneNumber, boolean mobileOnly) {

		if (isEmptyString(phoneNumber))
			return "";

		phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
		if (phoneNumber.startsWith("86"))
			phoneNumber = phoneNumber.replaceFirst("86", "");

		String ret = PatternKit.parseStr(phoneNumber.replaceAll("\\s*", ""),
				"0?(1(3|4|5|8)\\d{9})", 1);

		return isMobile(ret) ? phoneNumber.startsWith("0") ? phoneNumber
				.replaceFirst("0", "") : phoneNumber : mobileOnly ? "" : ret;

	}
	
	
	/**
	 * 获取指定范围的随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月27日 下午2:39:14
	 */
	public static int getRandomNumber(int min, int max) {

		if (min >= max)
			return max;

		if (min + 1 == max)
			return min;

		// long to int
		return (int) Math.round(Math.random() * (max - min) + min);

	}
	
	/**
	 * 是否为空
	 * 
	 * @param src
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月27日 下午2:39:38
	 */
	public static boolean isEmptyString(String src) {

		return src == null || src.trim().length() < 1;

	}
	
	/**
	 * 是否非空
	 * 
	 * @param src
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月27日 下午2:39:53
	 */
	public static boolean isNotEmpty(String src){
		return !isEmptyString(src);
	}
	
	/**
	 * 时间戳时间转换
	 * 
	 * @param format
	 * @param millis
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月27日 下午2:40:07
	 */
	public static String getDateString(String format, long millis) {

		Date date = new Date(millis);

		if (isEmptyString(format))
			format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);

	}
	
	/**
	 * 字符段去重
	 * 
	 * @param contactInfo
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月27日 下午2:40:39
	 */
	public static String cleanContactInfo(String contactInfo){
		if(isEmptyString(contactInfo)){
			return "";
		}
		
		if(contactInfo.contains(" ")){
			
			Set<String> set = new HashSet<String>();
			for(String one : contactInfo.split(" ")){
				set.add(one);
			}
			
			return set.toString().replace(",", " ").replace("[", "").replace("]", "") ;
		}
		
		return contactInfo;
	}
	
	/**
	 * 字符段去重
	 * 
	 * @param tel
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年5月16日 上午10:48:11
	 */
	public static Set<String> cleanTelInfo(String tel){
		Set<String> set = new HashSet<String>();

		if(isEmptyString(tel)){
			return set;
		}
		
		
		if(tel.contains(" ")){
			
			
			for(String one : tel.split(" ")){
				if(isMobile(one)){
					set.add(one);
				}
			}
			
		}else{
			if(isMobile(tel)){
				set.add(tel);
			}
		}
		
		return set;
	}
	
	
	/**
	 * 将首字母转小写
	 * @param src
	 * @return
	 */
	public static String indexToLowerCase(String src){//A-Z:65~90 a-z:97~122
		if(src == null || "".equals(src.trim())){
			return "";
		}
		int i = src.charAt(0);
		if(i>=65 && i<=90){
			return src.replaceFirst(src.charAt(0)+"", (char)(i+32)+""); //Note : 不可使用replace()方法
		}
		return src;
	}
	
	/**
	 * 将首字母转大写
	 * @param src
	 * @return
	 */
	public static String indexToUpperCase(String src){//A-Z:65~90 a-z:97~122
		if(src == null || "".equals(src.trim())){
			return "";
		}
		int i = src.charAt(0);
		if(i>=97 && i<=122){
			return src.replaceFirst(src.charAt(0)+"", (char)(i-32)+""); //Note : 不可使用replace()方法
		}
		return src;
	}
	

	
	/**
	 * 将mysql数据库中数据类型转换为java中数据类型
	 * <p>
	 * <B>只�?�虑了目前主要使用的数据类型</B>
	 * @param jdbcType
	 * @return
	 */
	public static String jdbcType2JavaType(String jdbcType){
		if(jdbcType == null){
			return "String";
		}
		jdbcType = jdbcType.toUpperCase();
		if("INTEGER".equals(jdbcType) || "INT".equals(jdbcType)){
			return "int";
		}
		if("NUMERIC".equals(jdbcType) || "DOUBLE".equals(jdbcType)){
			return "double";
		}
		if("FLOAT".equals(jdbcType)){
			return "float";
		}
		if("BIGINT".equals(jdbcType)){
			return "long";
		}
		return "String";
	}
	
	/**
	 * 获取默认值
	 * @param javaType
	 * @return
	 */
	public static String getDefaultValue(String javaType){
		if(javaType == null){
			return "''";
		}
		
		javaType = javaType.toLowerCase();
		
		if("int".equals(javaType) 
				|| "long".equals(javaType)
				|| "double".equals(javaType)
				|| "float".equals(javaType)
				|| "short".equals(javaType)){
			return "0";
		}
		
		return "''";
	}

	
	public static void main(String[] args) {
		MyLog.logInfo(isMobile("86"));
		MyLog.logInfo(cleanTelInfo("86"));
	}
}
