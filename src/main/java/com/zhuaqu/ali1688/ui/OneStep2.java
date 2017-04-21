package com.zhuaqu.ali1688.ui;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ydj.common.dao.DaoFactory;
import com.ydj.simpleSpider.MyLog;
import com.zhuaqu.Common;
import com.zhuaqu.InitApp;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class OneStep2 {
	
	static String getContactInfo(Ali1688Data data,String url){
		
		String ren = "" ,tel ="";
		
		try {
			String html = Common2.getHtmlContent(url);
			
			Document doc = Jsoup.parse(html);
			
			
			if(doc.select("div[class=return-cbu-signin]").size() > 0){
				SecondStep2.state = State.needSignIn;
				//MyLog.logError(html);
				Common2.save2File(SecondStep2.savePath, url.replace("://", ".")+".html", html,"GBK");
				
				return "";
			}		
					
			if(doc.select("label[for=checkcodeInput]").size() > 0){
				SecondStep2.state = State.needCheckcode;
				//MyLog.logError(html);
				Common2.save2File(SecondStep2.savePath, url.replace("//", ".")+".html", html,"GBK");
				return "";
			}
			
			Elements e = doc.select("div[class=m-content]");
			if(e != null){
				ren = e.select("a[class=membername]").text();
				tel = e.select("dl[class=m-mobilephone]").attr("data-no");
			}
			
			if("".equals(tel)){
				
				String href = doc.select("li[data-page-name=contactinfo]").select("a").attr("href");
				
				html = Common2.getHtmlContent(href);
				
				Document doc2 = Jsoup.parse(html);
				
				ren = doc2.select("a[class=membername]").text();
				tel = doc2.select("dl[class=m-mobilephone]").select("dd").text();
				if("".equals(tel)){
					tel = doc2.select("div[class=contcat-desc]").select("dl").get(0).select("dd").text();
				}
			}
			
			tel = tel.replace("登录后可见", "");
		} catch (Exception e) {
		}
		
		data.contact = ren;
		data.tel = tel;
		
		return ren+Common.TAB+tel;
	}
	

	
	private static void getStoreInfo(int typeOf,String url,int page){
		
		String html = Common2.getHtmlContent(url);
		
		//System.out.println(html);

		Elements elements = Jsoup.parse(html).select("div[class=list-item-left]");
		
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
			
			String contactInfo = getContactInfo(data,storeURL);
			
			MyLog.logInfo(page+"--->"+i+Common.TAB+company+Common.TAB+storeURL+Common.TAB+mainProduct+Common.TAB+areaaddress+Common.TAB+bussModel+Common.TAB+contactInfo);
			
			MyLog.logInfo("---------------------");
			
			DaoFactory.getMyDao().save(typeOf, company, storeURL, mainProduct, areaaddress, bussModel, data.contact, data.tel);
			
			try {
				Thread.sleep(Common.getRandomNumber(1000, 2000));
			} catch (Exception e) {
			}
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		new InitApp();
		
		Set<Integer> set = new HashSet<Integer>();
		
		while(set.size() < 100 ){
			int i = Common.getRandomNumber(1, 100);
			
			if(!set.contains(i)){
				set.add(i);
			}
		}
		
		int typeOf = 5;
		String url = "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%BF%E3&pageSize=30&smToken=6a5002852252417fba73b7d54a2e4d76&smSign=FIh9LqMsw0SLr5bJZKbn%2BA%3D%3D&offset=3&beginPage=";
		
		for(int page : set){
			getStoreInfo(typeOf,url+page,page);
		}
		
	}
}
