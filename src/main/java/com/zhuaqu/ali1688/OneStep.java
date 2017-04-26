package com.zhuaqu.ali1688;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ydj.common.dao.DaoFactory;
import com.ydj.simpleSpider.MyLog;
import com.zhuaqu.Common;
import com.zhuaqu.InitApp;
import com.zhuaqu.ali1688.ui.Toolbox;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class OneStep {
	
	static String getContactInfo(Ali1688Data data,String url){
		
		String ren = "" ,tel ="";
		
		try {
			String html = Common.getHtmlContent(url);
			
			Document doc = Jsoup.parse(html);
			
			
			Elements e = doc.select("div[class=m-content]");
			if(e != null){
				ren = e.select("a[class=membername]").text();
				tel = e.select("dl[class=m-mobilephone]").attr("data-no");
			}
			
			if("".equals(tel)){
				
				String href = doc.select("li[data-page-name=contactinfo]").select("a").attr("href");
				
				html = Common.getHtmlContent(href);
				
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
		
		System.out.println("\t"+ren+"\t"+tel);
		
		data.contact = ren;
		data.tel = tel;
		
		return ren+Common.TAB+tel;
	}
	

	private static int lessThanPageSizeOfPage = 100;
	private static int lessThanPageSizeOfPageSize = 0;
	
	private static int maxPage = 100;
	private static int noDataPage = 100;
	
	private static void getStoreInfo(int typeOf,String url,int page){
		
		if(page > lessThanPageSizeOfPage){
			MyLog.logError("由于此前获取到["+lessThanPageSizeOfPage+"]页数据["+lessThanPageSizeOfPageSize+"]小于30，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		if(page > noDataPage){
			MyLog.logError("由于此前获取到["+noDataPage+"]页也无数据，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		if(page > maxPage){
			MyLog.logError("本条目最多["+maxPage+"]页，故默认认为["+page+"]页没数据。");
			return ;
		}
		
		
		String html = Common.getHtmlContent(url);
		
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
		
		for(int i = 0 ; i < pageSize; i++){
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
			
			String contactInfo = "";//getContactInfo(data,storeURL);
			
			System.out.println("typeOf:"+typeOf+Common.TAB+page+"--->"+i+Common.TAB+company+Common.TAB+storeURL+Common.TAB+mainProduct+Common.TAB+areaaddress+Common.TAB+bussModel+Common.TAB+contactInfo);
			
			//System.out.println("---------------------");
			
			DaoFactory.getMyDao().save(typeOf, company, storeURL, mainProduct, areaaddress, bussModel, data.contact, data.tel,"");
			
			try {
				Thread.sleep(Common.getRandomNumber(600, 1800));
			} catch (Exception e) {
			}
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		new InitApp();
		
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=1 ;i<101;i++){
			list.add(i);
		}
		
		Map<Integer,String> all = new HashMap<Integer,String>();
		
//		all.put(4, "https://s.1688.com/company/company_search.htm?keywords=%B4%F2%B5%D7%C9%C0&sortType=pop&pageSize=30&offset=3&beginPage=");
//		all.put(5, "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%BF%E3&pageSize=30&offset=3&beginPage=");
//		all.put(21, "https://s.1688.com/company/company_search.htm?keywords=%D5%EB%D6%AF%C3%E6%C1%CF&pageSize=30&offset=3&beginPage=");
//		all.put(22, "https://s.1688.com/company/company_search.htm?smToken=230be9d1cb8249bbb5c5fdb877f3ed39&smSign=UIIQ6TsYSMLgxDNdFk4rSQ%3D%3D&keywords=%C9%B4%CF%DF&pageSize=30&offset=3&beginPage=");
//		all.put(23, "https://s.1688.com/company/company_search.htm?smToken=e1055a6c808b4e5785578bfb9841ea33&smSign=C5vVUhufrZ5MFo7DXThipg%3D%3D&keywords=%C5%F7%B2%BC&pageSize=30&offset=3&beginPage=");
//		all.put(24, "https://s.1688.com/company/company_search.htm?smToken=7b50a82cd7204b2e9eb1331ff1a111f8&smSign=CTwsYi2CkC11s8CCTgVi%2Fg%3D%3D&keywords=%B7%C4%D6%AF%B8%A8%C1%CF&pageSize=30&offset=3&beginPage=");
//		all.put(25, "https://s.1688.com/company/company_search.htm?smToken=6494d62f31bc4cf2ab00a843e3540512&smSign=aK3Ow7USNLKsDUPfA36jgA%3D%3D&keywords=%BB%AF%D1%A7%CF%CB%CE%AC&pageSize=30&offset=3&beginPage=");
//		all.put(26, "https://s.1688.com/company/company_search.htm?smToken=c4fa43964b52481aa5e0cf83acd28068&smSign=BOx2Wu7a3PlUk34DtbSTwQ%3D%3D&keywords=%C6%A4%B8%EF&pageSize=30&offset=3&beginPage=");
//		all.put(27, "https://s.1688.com/company/company_search.htm?showStyle=popular&smToken=57fb5649f9664288bc0aab3560e138fa&smSign=fA8oDZtcZkH5GfFLa7Gkxw%3D%3D&keywords=%BB%A4%B7%F4%C6%B7&pageSize=30&offset=3&beginPage=");
//		all.put(28, "https://s.1688.com/company/company_search.htm?keywords=%B2%CA%D7%B1%CF%E3%B7%D5&pageSize=30&offset=3&beginPage=");
//		all.put(29, "https://s.1688.com/company/company_search.htm?keywords=%C3%E6%C4%A4&pageSize=30&offset=3&beginPage=");
//		all.put(30, "https://s.1688.com/company/company_search.htm?showStyle=popular&keywords=%CA%D6%B9%A4%D4%ED&pageSize=30&offset=3&beginPage=");
//		all.put(31, "https://s.1688.com/company/company_search.htm?showStyle=noimg&smToken=64337435788c4138a4e05cced39da336&smSign=FD%2FOGf3zMqe8wxEfaisDug%3D%3D&keywords=%BD%DE%C3%AB%B8%E0&pageSize=30&offset=3&beginPage=");
//		all.put(32, "https://s.1688.com/company/company_search.htm?showStyle=noimg&keywords=BB˪&pageSize=30&offset=3&beginPage=");
//		all.put(33, "https://s.1688.com/company/company_search.htm?keywords=%D6%B8%BC%D7%D3%CD&pageSize=30&offset=3&beginPage=");
//		all.put(34, "https://s.1688.com/company/company_search.htm?smToken=4f735330954e485aa7eb76bb8e7d16d5&smSign=lnNGFO6nMpLkOCQ%2Fg5u8eA%3D%3D&keywords=%BC%D9%B7%A2&pageSize=30&offset=3&beginPage=");
		all.put(35, "https://s.1688.com/company/company_search.htm?smToken=f29496876bbb47be8d5decee56f61ccf&smSign=OnPYOIMUKNJa%2BXYBdSyufQ%3D%3D&keywords=%B1%AD%D7%D3&pageSize=30&offset=3&beginPage=");
		all.put(36, "https://s.1688.com/company/company_search.htm?smToken=216bb56dd2fe4a01949fc723d341b0f4&smSign=EN%2FLbwPaBXHvjm%2BjPGbqCQ%3D%3D&keywords=%B2%CD%BE%DF&pageSize=30&offset=3&beginPage=");
		all.put(37, "https://s.1688.com/company/company_search.htm?categoryStyle=false&button_click=top&smToken=086175b99a1e46a3baf0d1d3214358e4&smSign=6NZU0X7mnPmXBXQ97xH8jQ%3D%3D&keywords=%B1%ED&pageSize=30&offset=3&beginPage=");
		all.put(38, "https://s.1688.com/company/company_search.htm?smToken=86b1cc45279448b2a007891e6577f6c3&smSign=nn4iTbUnKMRkl1SsOA6xYw%3D%3D&keywords=%CA%D5%C4%C9&sortType=pop&pageSize=30&offset=3&beginPage=");
		all.put(39, "https://s.1688.com/company/company_search.htm?smToken=ab3a1c85a455422eaef7a10f905c83f7&smSign=7I6U%2BwCT%2Fa1XfIBp3AWoBw%3D%3D&keywords=%C1%B9%CF%AF&pageSize=30&offset=3&beginPage=");
		all.put(40, "https://s.1688.com/company/company_search.htm?smToken=9dd76ff1544a45a4b1f7abd96bced7e1&smSign=SEIp5qenTTYO%2FIuFRSQKAA%3D%3D&keywords=%BF%BF%B5%E6&pageSize=30&offset=3&beginPage=");
		all.put(41, "https://s.1688.com/company/company_search.htm?smToken=d6da2e3cd2fe40fe8a4ab6e5f6aafe8d&smSign=iQy3J7IcTDJ9%2BGvDLDZG4Q%3D%3D&keywords=%CD%CF%B0%D1&pageSize=30&offset=3&beginPage=");
		all.put(42, "https://s.1688.com/company/company_search.htm?smToken=012c8adff1484b8fb5ee221bd5476fe3&smSign=JmOVM58NS7FK8f5t3gM%2FBw%3D%3D&keywords=%B0%D9%BB%F5&sortType=pop&pageSize=30&offset=3&beginPage=");
		
		
		for(int key : all.keySet()){
			
			int typeOf = key;
			String url = all.get(key);
			
			if(Toolbox.isEmptyString(url)){
				continue ;
			}
			
			Collections.shuffle(list);//尽量打乱翻页顺序
			
			lessThanPageSizeOfPage = 100 ;//归位
			lessThanPageSizeOfPageSize = 0;
			maxPage = 100;
			noDataPage = 100;
			
			for(int page : list){
				getStoreInfo(typeOf,url+page,page);
			}
		}
		
	}
}
