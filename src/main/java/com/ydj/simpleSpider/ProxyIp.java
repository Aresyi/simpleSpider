package com.ydj.simpleSpider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.ydj.common.MyLog;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class ProxyIp implements Comparable<ProxyIp> {

	/**IP地址*/
	private String ip;
	
	/**端口号*/
	private int port;
	
	/**是否正在使用*/
	private boolean isUseing;
	
	/**被使用次数*/
	private int useCount;
	
	/**创建时间*/
	private long createTime;
	
	/**最近使用时间*/
	private long lastUseTime;
	
	
	private static final  SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ProxyIp(String ip,int port){
		this.ip = ip;
		this.port = port;
		this.createTime = System.currentTimeMillis();
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public boolean isUseing() {
		return isUseing;
	}


	public void setUseing(boolean isUseing) {
		this.isUseing = isUseing;
	}


	public int getUseCount() {
		return useCount;
	}


	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	public long getLastUseTime() {
		return lastUseTime;
	}


	public void setLastUseTime(long lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	
	
	@Override
	public int hashCode() {
		int result = 17;
	    result = 37 * result + ip.hashCode();
	    result = 37 * result + port;
	    result = 37 * result + (isUseing ? 0 : 1);
	    result = 37 * result + useCount;
	    result = 37 * result + ( int ) ( createTime ^ ( createTime >>> 32 )) ;
	    result = 37 * result + ( int ) ( lastUseTime ^ ( lastUseTime >>> 32 )) ;
	    
	    return result;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ProxyIp)){
			return false;
		}
		ProxyIp other = (ProxyIp)obj;
		return (
				this.ip.equals(other.ip) &&
				this.port == other.port &&
				this.isUseing == other.isUseing &&
				this.useCount == other.useCount &&
				this.createTime == other.createTime &&
				this.lastUseTime == other.lastUseTime
				);
	}


	@Override
	public String toString() {
		return ip+":"+port+"=>{isUseing="+isUseing+",useCount="+useCount+",createTime="+DATE_FORMAT.format(new Date(createTime))+",lastUseTime="+DATE_FORMAT.format(new Date(lastUseTime))+"}";
	}
	
	@Override
	public int compareTo(ProxyIp o) {
//		if(!this.ip.equals(o.ip)){  
//            return (this.ip.compareTo(o.ip));  
//        }else if(this.port != o.port){  
//            return (this.port > o.port ? 1 :-1);  
//        }else 
        if(this.isUseing != o.isUseing){  
            return this.isUseing ? 1 : -1;  
        }else if(this.useCount != o.useCount){  
            return (this.useCount > o.useCount ? 1 :-1);  
        }else{  
            return (this.lastUseTime - this.createTime) > (o.lastUseTime - o.createTime) ? 1 : -1;  
        }  
	}
	
	/**
	 * 使用IP
	 * 
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午4:43:10
	 */
	public void useThis(){
		this.isUseing = true;
		this.useCount += 1 ;
		this.lastUseTime = System.currentTimeMillis();
	}
	
	/**
	 * TEST
	 * @param args
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午4:38:13
	 */
	public static void main(String[] args) {
		ProxyIp obj0 = new ProxyIp("192.168.254.50", 8089);
		
		obj0.setLastUseTime(System.currentTimeMillis()+1*3600*1000L);
		obj0.setUseing(true);
		obj0.setUseCount(2);
		
		ProxyIp obj1 = new ProxyIp("192.168.254.50", 8080);
		obj1.setLastUseTime(System.currentTimeMillis()+2*3600*1000L);
		obj1.setUseing(true);
		obj1.setUseCount(1);
		
		ProxyIp obj2 = new ProxyIp("192.168.254.52", 8080);
		ProxyIp obj3 = new ProxyIp("192.168.254.52", 8080);
		
		List<ProxyIp> list = new ArrayList<ProxyIp>();
		list.add(obj0);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		
		for(ProxyIp one : list){
			MyLog.logInfo(one.toString() + " hashCode:"+one.hashCode() );
		}
		
		Collections.sort(list);
		
		MyLog.logInfo("=========after sort=============");
		for(ProxyIp one : list){
			MyLog.logInfo(one.toString() + " hashCode:"+one.hashCode() );
		}
		
	}


	
}
