/**
 * 
 */
package com.ydj.simpleSpider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.ydj.common.MyLog;

/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
*/
public class ProxyIpPool {

	/**设置最多IP数*/
	private static final int MAX_IP = 100;
	
	/**设置最少IP数（最好控制和外部使用线程数一致）*/
	@SuppressWarnings("unused")
	private static final int MIN_IP = 10;
	
//	public static ConcurrentHashMap<Integer,Integer> canUseIPs = new ConcurrentHashMap<Integer,Integer>();
	public static List<ProxyIp> canUseIpList = Collections.synchronizedList(new ArrayList<ProxyIp>(MAX_IP));
	private static LRUMap notCanUseIPsTemp = new LRUMap(2000);
	
	/**每次抓取IP数*/
	private static final int NUM = 20;
	private static final String ORDER_ID = "904557733280949";
	private static final String KDL_URL = "http://dev.kuaidaili.com/api/getproxy?orderid="+ORDER_ID+"&num="+NUM+"&quality=1&an_ha=1&dedup=1&format=json";
	
	
	private ProxyIpPool(){
	}
	
	/**
	 * 启动抓取代理IP线程 
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午5:58:54
	 */
	public static void startCrawl(){
		final int period = 3;
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			int i = 0 ;
			@Override
			public void run() {
				produceIP(i);
				i++;
			}
		}, 1, period,TimeUnit.MINUTES);
	}
	
	private static void produceIP(int i){
		int currentSize = canUseIpList.size();
		
		if( currentSize >= MAX_IP){
			MyLog.logInfo(i+":current proxyPool size is:"+currentSize+",no need crawl new ip.NotCanUseIPsTemp size is:"+notCanUseIPsTemp.size());
			return ;
		}
		
		JSONArray ips = getIPFromKuaiDaiLi();
		produceIP(ips);
		
		MyLog.logInfo(i+":current proxyPool size is:"+canUseIpList.size()+",notCanUseIPsTemp size is:"+notCanUseIPsTemp.size());
	}
	
	private static void produceIP(JSONArray ips){
		if(ips == null || ips.isEmpty()){
			return ;
		}
		for(int i = 0 ;i < ips.size() ;i++ ){
			Object one  = ips.get(i);
			String s[] = one.toString().split(":");
			String ip = s[0];
			int port = Integer.valueOf(s[1]);
			
			ProxyIp proxyIp = new ProxyIp(ip, port);

			if(isCanUse(ip, port)){
				addIP(proxyIp);
			}else{
				removeIP(proxyIp);
			}
		}
	}
	
	public static ProxyIp useOneProxyIp(){
		
		if(canUseIpList.isEmpty()){
			MyLog.logInfo(Thread.currentThread().getName()+" useOneProxyIp,but proxyPool is empty,need to wait 2 min crawl IP.");
			try {
				Thread.sleep(2 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Collections.sort(canUseIpList);
		
		ProxyIp proxyIp = canUseIpList.remove(0);
		proxyIp.useThis();
		
		return proxyIp;
		
	}
	
	public static void returnProxyIp(ProxyIp proxyIp){
		proxyIp.setUseing(false);
		canUseIpList.add(proxyIp);
		return ;
	}
	
	/**
	 * 从快代理网站获取代理IP
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午2:36:05
	 */
	private static JSONArray getIPFromKuaiDaiLi(){
		JSONArray ips = new JSONArray();
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod(KDL_URL);
		
		HttpMethodParams param = method.getParams();
		param.setContentCharset("UTF-8");
		
		try {
			client.executeMethod(method);
			String res = method.getResponseBodyAsString();
			JSONObject json = JSONObject.fromObject(res);
			if(json != null && json.containsKey("data")){
				ips = json.getJSONObject("data").getJSONArray("proxy_list");
				MyLog.logInfo(ips);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ips;
	}
	
	/**
	 * 从更多的网站获取代理IP
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午2:46:40
	 */
	@SuppressWarnings("unused")
	private static JSONArray getIPFromXXX(){
		JSONArray ips = new JSONArray();
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod("XXX");
		
		HttpMethodParams param = method.getParams();
		param.setContentCharset("UTF-8");
		
		try {
			client.executeMethod(method);
			String res = method.getResponseBodyAsString();
			JSONObject json = JSONObject.fromObject(res);
			if(json != null && json.containsKey("data")){
				ips = json.getJSONObject("data").getJSONArray("proxy_list");
				MyLog.logInfo(ips);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ips;
	}
	
	/**
	 * 检测代理IP是否可用
	 * 
	 * @param ip
	 * @param port
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午2:37:22
	 */
	private static boolean isCanUse(String ip,int port){
		if(port < 0 ){
			return false;
		}
			
		if(notCanUseIPsTemp.containsKey(ip)){
			MyLog.logInfo(ip+":"+port+" can't use again.");
			return false;
		}
		
		if(!checkIp(ip, port)){
			return false;
		}
		
		return checkIpUseTargetSite(ip, port);
	}
	
	/**
	 * 检测代理IP是否可用
	 * 
	 * @param ip
	 * @param port
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午12:35:28
	 */
	private static boolean checkIp(String ip,int port){
		Socket server = null;
		try {
			server = new Socket();
			InetSocketAddress address = new InetSocketAddress(ip,port);
			server.connect(address, 3000);
			MyLog.logInfo(ip+":"+port+" is ok!");
			return true;
		}catch (UnknownHostException e) {
			//e.printStackTrace();
			MyLog.logInfo(ip+":"+port+" is wrong!");
		} catch (IOException e) {
			//e.printStackTrace();
			MyLog.logInfo(ip+":"+port+" is wrong!!");
		}
		return false;
	}
	
	/**
	 * 到目标网站准确检测代理IP是否可用
	 * 
	 * @param ip
	 * @param port
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午12:06:03
	 */
	private static boolean checkIpUseTargetSite(String ip,int port){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		
		HttpHost proxy = new HttpHost(ip,port, "http");
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setProxy(proxy).build();
		HttpGet httpGet = new HttpGet("http://www.autozi.com/partCategory.html/");
		httpGet.setConfig(config);
		
		try {
			CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
			HttpEntity httpentity = response.getEntity();
			String html =  EntityUtils.toString(httpentity, "UTF-8");
			if(Jsoup.parse(html).select("div[class=header fix]").first() != null){
				return true;
			}
		} catch (Exception exc){
//			exc.printStackTrace();
			MyLog.logError(exc.getMessage());
		}
		
		return false;
	}
	
	
	public static void removeIP(ProxyIp proxyIp){
		canUseIpList.remove(proxyIp);
		notCanUseIPsTemp.put(proxyIp.getIp(),proxyIp.getPort());
	}
	
	public static void addIP(ProxyIp proxyIp){
		canUseIpList.add(proxyIp);
		notCanUseIPsTemp.remove(proxyIp.getIp());
	}
	
	/**
	 * 测试使用代理IP 
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午6:00:16
	 */
	private static void testUseProxyIp(){
		ExecutorService threadPool = Executors.newFixedThreadPool(10); 
		
		for(int i=0 ;i <20 ;i++){
			final int flag = i;
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					ProxyIp proxyIp = useOneProxyIp();
					MyLog.logInfo(flag+" job "+Thread.currentThread().getName()+" get proxyIp is : "+proxyIp.toString());
					
					long millis = new Random().nextInt(10) * 1000;
					try {
						Thread.sleep(millis);//每个线程随机sleep N秒，模拟线程在工作
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					returnProxyIp(proxyIp);
					
					MyLog.logInfo(flag+" job "+Thread.currentThread().getName()+" use proxyIp is : "+proxyIp.toString()+",work use time "+millis+" end and return to pool.");
				}
			});
		}
		
	}
	
	/**
	 * TEST
	 * @param args
	 * @throws IOException
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月29日 下午5:51:24
	 */
	public static void main(String[] args) throws IOException {
		ProxyIpPool.startCrawl();
		ProxyIpPool.testUseProxyIp();
	}
}
