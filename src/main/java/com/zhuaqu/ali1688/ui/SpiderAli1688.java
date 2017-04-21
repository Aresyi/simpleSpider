package com.zhuaqu.ali1688.ui;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ydj.common.Constant;
import com.ydj.common.dao.DaoFactory;
import com.ydj.simpleSpider.MyLog;
import com.zhuaqu.Common;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class SpiderAli1688 {
	
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
		
		data.contact = ren;
		data.tel = tel;
		
		return ren+Common.TAB+tel;
	}
	

	/**
	 * 抓取品类列表页中每家店铺信息
	 * 
	 * @param typeOf
	 * @param url
	 * @param page
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月21日 下午3:00:15
	 */
	public static void getStoreInfo(int typeOf,String url,int page){
		
		String html = Toolbox.getHtmlContent(url);
		
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
		
	}
}
