package com.ydj.zhuaqu.ali1688;

/**
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42
 * @version : 1.0
 * @description :
 *
 */
public class Industry{
    	String key;
    	String value;
    	
    	public Industry(String code,String name){
    		this.key = code;
    		this.value = name;
    	}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
    	
    	
    	
}