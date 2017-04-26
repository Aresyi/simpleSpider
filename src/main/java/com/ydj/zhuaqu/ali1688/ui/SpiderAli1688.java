package com.ydj.zhuaqu.ali1688.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ydj.common.MyLog;
import com.ydj.common.dao.DaoFactory;
import com.ydj.zhuaqu.Common;
import com.ydj.zhuaqu.ali1688.Ali1688Data;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class SpiderAli1688 {
	
	private static int lessThanPageSizeOfPage = 100;
	private static int lessThanPageSizeOfPageSize = 0;
	
	private static int maxPage = 100;
	private static int noDataPage = 100;
	
	private static int sum = 0;
	
	/**
	 * 抓取店铺详情联系人页面信息
	 * 
	 * @param data
	 * @param url
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月21日 下午2:59:12
	 */
	public static String getContactInfo(Ali1688Data data,String url){
		
		String ren = "" ,tel ="";
		
		try {
			String html = Toolbox.getHtmlContent(url);
			
			Document doc = Jsoup.parse(html);
			
			
			if(doc.select("div[class=return-cbu-signin]").size() > 0){
				Constant.state = State.needSignIn;
				//MyLog.logError(html);
				Toolbox.save2File(Constant.savePath, url.replace("://", ".")+".html", html,"GBK");
				
				return "";
			}		
					
			if(doc.select("label[for=checkcodeInput]").size() > 0){
				Constant.state = State.needCheckcode;
				//MyLog.logError(html);
				Toolbox.save2File(Constant.savePath, url.replace("//", ".")+".html", html,"GBK");
				
				Constant.ali1688CheckCodeFormData = getCheckCodeFormData(url,html);
				
				return "";
			}
			
			Elements e = doc.select("div[class=m-content]");
			if(e != null){
				ren = e.select("a[class=membername]").text();
				tel = e.select("dl[class=m-mobilephone]").attr("data-no");
			}
			
			if("".equals(tel)){
				
				String href = doc.select("li[data-page-name=contactinfo]").select("a").attr("href");
				
				html = Toolbox.getHtmlContent(href);
				
				Document doc2 = Jsoup.parse(html);
				
				ren = doc2.select("a[class=membername]").text();
				tel = doc2.select("dl[class=m-mobilephone]").select("dd").text();
				if("".equals(tel)){
					tel = doc2.select("div[class=contcat-desc]").select("dl").get(0).select("dd").text();
				}
			}
			
			ren = Toolbox.cleanContactInfo(ren);
			tel = tel.replace("登录后可见", "");
		} catch (Exception e) {
		}
		
		data.setContact(ren);
		data.setTel(tel);
		
		return ren+Common.TAB+tel;
	}
	

	/**
	 * 抓取品类列表页中每家店铺信息
	 * 
	 * @param typeOf
	 * @param url
	 * @param page
	 * @param min
	 * @param max
	 * @param iuCode
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月21日 下午3:00:15
	 */
	public static void getStoreInfo(int typeOf,String url,int page,int min,int max,String iuCode){
		
		if(page > lessThanPageSizeOfPage){
			MyLog.logError(typeOf+" 由于此前获取到["+lessThanPageSizeOfPage+"]页数据["+lessThanPageSizeOfPageSize+"]小于30，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		if(page > noDataPage){
			MyLog.logError(typeOf+" 由于此前获取到["+noDataPage+"]页也无数据，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		if(page > maxPage){
			MyLog.logError(typeOf+" 本条目最多["+maxPage+"]页，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		String html = Toolbox.getHtmlContent(url);
		
		//System.out.println(html);

		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div[class=list-item-left]");
		
		Elements noResult =  doc.select("h2[class=noresult-hd]");
		if(noResult != null && noResult.text().contains("没找到")){
			MyLog.logError("NO Result:"+url);
			try {
				Thread.sleep(Common.getRandomNumber(800, 1500));
			} catch (Exception e) {
			}
			noDataPage  = page;
			return ;
		}
		
		try {
			String maxPageStr = doc.select("input[id=jumpto]").attr("data-max");
			maxPage = Integer.parseInt(maxPageStr);
		} catch (Exception e) {
		}
		
		
		int pageSize = elements.size();
		
		if(pageSize < (30 - 5) ){//发现规律：每页页面数据30+/-5,即浮动5条记录
			lessThanPageSizeOfPage = page;
			lessThanPageSizeOfPageSize = pageSize;
		}
		
		for(int i = 0 ; i < elements.size(); i++){
			Element one = elements.get(i);
			
			Elements elements2 =  one.select("a[class=list-item-title-text]");
			
			String company = elements2.attr("title");
			String storeURL = elements2.attr("href");
			
			Elements elements3 =  one.select("a[offer-stat=mainProduct]");
			String mainProduct = elements3.text();
			
			Elements elements4 =  one.select("a[class=sm-offerResult-areaaddress]");
			String areaaddress = elements4.attr("title");
			
			
			
			Elements rightDiv =  one.select("div[class=detail-right]");
			
			String bussModel = rightDiv.get(0).select("b").text();
			//String gongyi = rightDiv.get(1).attr("title");
			//String jiagong = rightDiv.get(2).attr("title");
			
			
			Ali1688Data data = new Ali1688Data(typeOf, company, storeURL,mainProduct, areaaddress,bussModel);
			
			String contactInfo = "";//getContactInfo(data,storeURL); //分步抓取
			
			MyLog.logInfo("typeOf:"+typeOf+Common.TAB+page+"--->"+i+Common.TAB+company+Common.TAB+storeURL+Common.TAB+mainProduct+Common.TAB+areaaddress+Common.TAB+bussModel+Common.TAB+contactInfo);
			
			int res = DaoFactory.getMyDao().save(typeOf, company, storeURL, mainProduct, areaaddress, bussModel, data.getContact(), data.getTel(),iuCode);
			
			if(res > 0){
				sum++;
			}
			
			try {
				Thread.sleep(Common.getRandomNumber(min, max));
			} catch (Exception e) {
			}
		}
		
	}
	
	/**
	 * 获取输入验证码页面信息
	 * 
	 * @param url
	 * @param checkCodePageHtml
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月24日 上午10:18:10
	 */
	public static Ali1688CheckCodeFormData getCheckCodeFormData(String url,String checkCodePageHtml){
		Ali1688CheckCodeFormData ali1688CheckCodeFormData= null;
		
		if(Toolbox.isEmptyString(checkCodePageHtml)){
			return ali1688CheckCodeFormData;
		}
		
		Document doc = Jsoup.parse(checkCodePageHtml);
		
		String action = doc.select("input[name=action]").attr("value");
	    String event_submit_do_query = doc.select("input[name=event_submit_do_query]").attr("value");
	    String smPolicy = doc.select("input[name=smPolicy]").attr("value");
	    String smReturn = doc.select("input[name=smReturn]").attr("value");
	    String smApp = doc.select("input[name=smApp]").attr("value");
	    String smCharset = doc.select("input[name=smCharset]").attr("value");
	    String smTag = doc.select("input[name=smTag]").attr("value");
	    String smSign = doc.select("input[name=smSign]").attr("value");
	    String identity = doc.select("input[name=identity]").attr("value");
	    String captcha = doc.select("input[name=captcha]").attr("value");
	    
	    String sessionid = doc.select("img[id=checkcodeImg]").attr("src"); 
	    
	    sessionid = sessionid.substring(sessionid.indexOf("sessionid=")+10,sessionid.indexOf("&"));
		
		ali1688CheckCodeFormData = new Ali1688CheckCodeFormData(action, event_submit_do_query, smPolicy, smReturn, smApp, smCharset, smTag, smSign, identity, captcha, sessionid,url);
	    
		return ali1688CheckCodeFormData;
	}
	
	/**
	 * 提交验证码
	 * 
	 * @param checkcode
	 *
	 * @author : Ares.yi
	 * @throws UnsupportedEncodingException 
	 * @createTime : 2017年4月24日 上午10:18:36
	 */
	public static String submitCheckCode(String checkcode) throws UnsupportedEncodingException{
		CloseableHttpClient client = HttpClients.createDefault();

		String smApp = Constant.ali1688CheckCodeFormData.getSmApp();
		String smPolicy = Constant.ali1688CheckCodeFormData.getSmPolicy();
		String smCharset = Constant.ali1688CheckCodeFormData.getSmCharset();
		String smTag = Constant.ali1688CheckCodeFormData.getSmTag();
		String smReturn = Constant.ali1688CheckCodeFormData.getSmReturn();
		String smSign = Constant.ali1688CheckCodeFormData.getSmSign();
		
		String get = "smApp="+smApp+"&smPolicy="+smPolicy+"&smCharset="+smCharset+"&smTag="+smTag+"&smReturn="+smReturn+"&smSign="+smSign;
		
		try {
			get = java.net.URLEncoder.encode(get,"utf-8");
		} catch (UnsupportedEncodingException e1) {
		}
		
		String formAction = "https://sec.1688.com/query.htm?"+get;
		
		System.out.println("formAction:"+formAction);
		
	    HttpPost httpPost = new HttpPost(formAction);
	    Map<String,String> parameterMap = new HashMap<String,String>();
	    parameterMap.put("action", Constant.ali1688CheckCodeFormData.getAction());
	    parameterMap.put("event_submit_do_query", Constant.ali1688CheckCodeFormData.getEvent_submit_do_query());
	    parameterMap.put("smPolicy", smPolicy);
	    parameterMap.put("smReturn", smReturn);
	    parameterMap.put("smApp", smApp);
	    parameterMap.put("smCharset", smCharset);
	    parameterMap.put("smTag", smTag);
	    parameterMap.put("smSign", smSign);
	    parameterMap.put("identity", Constant.ali1688CheckCodeFormData.getIdentity());
	    parameterMap.put("captcha", Constant.ali1688CheckCodeFormData.getCaptcha());
	    parameterMap.put("checkcode", checkcode);
	    
	    

	    UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
	    httpPost.setEntity(postEntity);
	    
	    httpPost.addHeader("HOST", "sec.1688.com");
	    httpPost.addHeader("User-Agent", Constant.userAgent);
	    httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    httpPost.addHeader("Cookie", Constant.cookie);
	    
	    MyLog.logInfo("request line:" + httpPost.getRequestLine());
	    try {
	      // 执行post请求
	      HttpResponse httpResponse = client.execute(httpPost);
	      
	      
	      Header header = httpResponse.getFirstHeader("Location");
	      
	      if (header != null && Toolbox.isNotEmpty(header.getValue())) {
	    	  	MyLog.logInfo("location:"+header.getValue());
	    	  	return "SUCCESS";
	      }else{
	    	  String html = printResponse(httpResponse);
	    	  
	    	  Constant.ali1688CheckCodeFormData = getCheckCodeFormData(smReturn,html);
	      }

	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        // 关闭流并释放资源
	        client.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	    
	    return "";
	}
	
	 @SuppressWarnings("rawtypes")
	public static List<NameValuePair> getParam(Map parameterMap) {
		    List<NameValuePair> param = new ArrayList<NameValuePair>();
		    Iterator it = parameterMap.entrySet().iterator();
		    while (it.hasNext()) {
		      Entry parmEntry = (Entry) it.next();
		      param.add(new BasicNameValuePair((String) parmEntry.getKey(),
		          (String) parmEntry.getValue()));
		    }
		    return param;
		  }
	 
	 public static String printResponse(HttpResponse httpResponse)
		      throws ParseException, IOException {
		    // 获取响应消息实体
		    HttpEntity entity = httpResponse.getEntity();
		    // 响应状态
		   MyLog.logInfo("status:" + httpResponse.getStatusLine());
		   MyLog.logInfo("headers:");
		    HeaderIterator iterator = httpResponse.headerIterator();
		    while (iterator.hasNext()) {
		    	MyLog.logInfo("\t" + iterator.next());
		    }
		    // 判断响应实体是否为空
		    if (entity != null) {
		      String responseString = EntityUtils.toString(entity);
		      MyLog.logInfo("response length:" + responseString.length());
		      MyLog.logInfo("response content:"+ responseString.replace("\r\n", ""));
		      return responseString;
		    }
		    
		    return "";
	}
	 
	 
	 public static void makeZero(){
		 lessThanPageSizeOfPage = 100 ;//归位
		 lessThanPageSizeOfPageSize = 0;
		 maxPage = 100;
		 noDataPage = 100;
		 sum = 0;
	 }
	
	 public static int getSum(){
		 return sum;
	 }
	
	public static void main(String[] args) throws Exception {
		String sessionid="https://pin.aliyun.com/get_img?sessionid=1b8446edf673ba260fc14486afc8b48d&identity=sm-kylin&type=default";
		
		sessionid = sessionid.substring(sessionid.indexOf("sessionid=")+10,sessionid.indexOf("&"));
		
		System.out.println(sessionid);
	}
}
