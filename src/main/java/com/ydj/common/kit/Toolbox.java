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
}
