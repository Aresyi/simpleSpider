package com.ydj.zhuaqu.ali1688.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ydj.common.kit.FileKit;
import com.ydj.common.kit.HttpKit;
import com.ydj.common.kit.MyLog;
import com.ydj.common.kit.Toolbox;
import com.ydj.zhuaqu.ali1688.Ali1688CheckCodeFormData;
import com.ydj.zhuaqu.ali1688.Ali1688Data;
import com.ydj.zhuaqu.ali1688.State;
import com.ydj.zhuaqu.dao.DaoFactory;

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
			String html = HttpKit.getHtmlContent(url);
			
			Document doc = Jsoup.parse(html);
			
			
			if(doc.select("div[class=return-cbu-signin]").size() > 0){
				Constant.state = State.needSignIn;
				//MyLog.logError(html);
				FileKit.save2File(Constant.savePath, url.replace("://", ".")+".html", html,"GBK");
				
				return "";
			}		
					
			if(doc.select("label[for=checkcodeInput]").size() > 0){
				Constant.state = State.needCheckcode;
				//MyLog.logError(html);
				FileKit.save2File(Constant.savePath, url.replace("//", ".")+".html", html,"GBK");
				
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
				
				html = HttpKit.getHtmlContent(href);
				
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
		
		return ren+Constant.TAB+tel;
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
		
		String html = HttpKit.getHtmlContent(url);
		
		//System.out.println(html);

		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("div[class=list-item-left]");
		
		Elements noResult =  doc.select("h2[class=noresult-hd]");
		if(noResult != null && noResult.text().contains("没找到")){
			MyLog.logError("NO Result:"+url);
			try {
				Thread.sleep(Toolbox.getRandomNumber(800, 1500));
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
			
			MyLog.logInfo("typeOf:"+typeOf+Constant.TAB+page+"--->"+i+Constant.TAB+company+Constant.TAB+storeURL+Constant.TAB+mainProduct+Constant.TAB+areaaddress+Constant.TAB+bussModel+Constant.TAB+contactInfo);
			
			int res = DaoFactory.getMyDao().save(typeOf, company, storeURL, mainProduct, areaaddress, bussModel, data.getContact(), data.getTel(),iuCode);
			
			if(res > 0){
				sum++;
			}
			
			try {
				Thread.sleep(Toolbox.getRandomNumber(min, max));
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
	public static String submitCheckCode(String checkcode) throws UnsupportedEncodingException, IOException{

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
	    
	    String res = HttpKit.postRequest(formAction, parameterMap,  "UTF-8");
	    
	    if (Toolbox.isNotEmpty(res) && "SUCCESS".equals(res)) {
	    	  	return "SUCCESS";
	    }else{
	    	  String html = res;
	    	  Constant.ali1688CheckCodeFormData = getCheckCodeFormData(smReturn,html);
	    } 
	    
	    return "";
	}
	
	 /**
	  * 归零
	  *
	  * @author : Ares.yi
	  * @createTime : 2017年4月27日 下午2:25:33
	  */
	 public static void makeZero(){
		 lessThanPageSizeOfPage = 100 ;//归位
		 lessThanPageSizeOfPageSize = 0;
		 maxPage = 100;
		 noDataPage = 100;
		 sum = 0;
	 }
	
	 /**
	  * 获取总数
	  * @return
	  *
	  * @author : Ares.yi
	  * @createTime : 2017年4月27日 下午2:25:48
	  */
	 public static int getSum(){
		 return sum;
	 }
	
}
