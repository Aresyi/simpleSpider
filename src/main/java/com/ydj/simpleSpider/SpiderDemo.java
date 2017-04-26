package com.ydj.simpleSpider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.ydj.common.MyLog;

/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
*/
public class SpiderDemo {
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(10); //最大线程数最好和代理IP池中最小IP数一致
	private static String url = "http://www.autozi.com/goods/carModels.do?goodsId=";

	
	/**
	 * 使用代理获取网页内容
	 * 
	 * @param url
	 * @param proxyIp
	 * @param proxyPort
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月30日 上午9:55:21
	 */
	public static String getHtml(String url,String proxyIp,int proxyPort) throws ParseException, IOException {
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		
		HttpHost proxy = new HttpHost(proxyIp,proxyPort, "http");
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).setProxy(proxy).build();
		
		HttpPost httpGet = new HttpPost(url);
		httpGet.setConfig(config);
		
		String html = "";
		CloseableHttpResponse response = null;
		
		try {
			response = closeableHttpClient.execute(httpGet);
		}catch(Exception exc){
			exc.printStackTrace();
//			File file = new File("C:/Users/Administrator/Desktop/wronglog/httpproxytools2log11.txt");
//			System.setOut(new PrintStream(new FileOutputStream(file,true)));
			System.out.println("get请求失败!");
			return "cannot connect";
		}
		
		HttpEntity httpEntity = response.getEntity();
		if (httpEntity != null) {
			// 打印响应内容
			try{
				html =  EntityUtils.toString(httpEntity, "UTF-8");
			}catch(Exception excep){
				System.out.println(url);
			}	
		}else{
			return "cannot connect";
		}
		
		closeableHttpClient.close();
		return html;
	}
	
	
	private static void demoZCF(){
		int totalPage = 100;//TODO : 读取数据库计算
		
		for(int page = 1 ;page <= totalPage; page++){
			
			List<String> productIdList = new ArrayList<String>();//TODO : 翻页读取数据库
			
			if(productIdList == null || productIdList.isEmpty()){
				continue;
			}
		
			for(int i = 0 ; i < productIdList.size() ;i++){
				String productId = productIdList.get(i);
				demo(page, i, productId);
			}
		}
		
	}
	
	private static void demo(final int page,final int i,final String productId){
		
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					ProxyIp proxyIp = ProxyIpPool.useOneProxyIp();
					MyLog.logInfo("this is ("+i+"/"+page+") job "+Thread.currentThread().getName()+" get proxyIp is : "+proxyIp.toString());
					
					long begin = System.currentTimeMillis();
					
					demoParseZCF(productId,proxyIp);
					
					long end = System.currentTimeMillis();
					
					ProxyIpPool.returnProxyIp(proxyIp);
					
					MyLog.logInfo("this is ("+i+"/"+page+") job "+Thread.currentThread().getName()+" use proxyIp is : "+proxyIp.toString()+",work use time "+(end-begin)+" end and return to pool.");
				}
			});
	}

	private static void demoParseZCF(String productId,final ProxyIp proxyIp){
		
		if(proxyIp == null){
			return ;
		}
		
		String ip = proxyIp.getIp();
		int port = proxyIp.getPort();

		String html = "";
		try {
			html = getHtml(url + productId, ip, port);
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (html.equals("cannot connect")) {
			MyLog.logInfo(productId + " Error");
		}

		List<Element> list = new ArrayList<Element>();
		
		try {
			Element e = Jsoup.parse(html);
			Element ul = e.select("ul").first();

			list = ul.select("li[class=suitable-type-item]");
		} catch (NullPointerException npe) {
			MyLog.logInfo(">>>>>>>>>>>1:" + url + productId);
		}
		
		for (Element p : list) {
			List<Element> list2 = new ArrayList<Element>();
			try {
				list2 = p.select("a[class=car-type]");
			} catch (NullPointerException npe2) {
				MyLog.logInfo(">>>>>>>>>>2:" + url + productId);
				continue;
			}
			for (Element a : list2) {
				String seriesId = a.attr("s_id");
				MyLog.logInfo(">>>>>>>>>>>>3:" + productId + ":" + seriesId+ ":::");
			}
		}
		
	}
	
	
	/**
	 * TEST
	 * 
	 * @param args
	 * @throws IOException
	 *
	 * @author : Ares.yi
	 * @createTime : 2015年10月30日 上午10:01:04
	 */
	public static void main(String[] args) throws IOException {
		ProxyIpPool.startCrawl();
		demoZCF();
	}
}
