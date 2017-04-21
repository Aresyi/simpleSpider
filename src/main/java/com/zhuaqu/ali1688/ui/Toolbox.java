package com.zhuaqu.ali1688.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ydj.common.Constant;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class Toolbox {
	
	public static int getRandomNumber(int min, int max) {

		if (min >= max)
			return max;

		if (min + 1 == max)
			return min;

		// long to int
		return (int) Math.round(Math.random() * (max - min) + min);

	}
	
	public static String getHtmlContent(String url, int connectTimeout,
			int readTimeout, String charset) {
		
		if(url == null || (!url.startsWith("https://") && !url.startsWith("http://")) ){
			return "";
		}
		
		if("".equals(Constant.cookie)){
			System.err.println("请先设置Cookie");
			return "";
		}

		StringBuffer inputLine = new StringBuffer();

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
					.openConnection();

			HttpURLConnection.setFollowRedirects(true);
			urlConnection.setConnectTimeout(connectTimeout);
			urlConnection.setReadTimeout(readTimeout);
			
			urlConnection.setRequestProperty("Connection","keep-alive");
			urlConnection.setRequestProperty("User-Agent",Constant.userAgent);
			urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConnection.setRequestProperty("Cookie",Constant.cookie);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));

			String str;
			while ((str = in.readLine()) != null)
				inputLine.append(str).append("\r\n");

			in.close();
		} catch (Exception e) {
			//e.printStackTrace();
			//System.err.println(url);
		}

		return inputLine.toString();
	}
	
	
	public static String getHtmlContent(String url) {
		
		String html = getHtmlContent(url,5000,5000,"GBK");
		
		return html;
	}

	
	
	public static void save2File(String saveAsPath,String fileName,String fileContent) throws IOException{
//		long now = System.currentTimeMillis();
		
		FileWriter fw = null;
		
		File path = new File(saveAsPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		File file = new File(saveAsPath+fileName);
		
//		if(file.exists()){
//			fileName = now+"_"+fileName;
//			file = new File(saveAsPath+fileName);
//		}
		
		fw = new FileWriter(file, true);
		
		PrintWriter pw = new PrintWriter(fw);
		pw.println(fileContent);
		pw.flush();
		
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void save2File(String saveAsPath,String fileName,String fileContent,String charset) {
		
		File path = new File(saveAsPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		File file = new File(saveAsPath+fileName);
		
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),charset));
			out.write(fileContent+"\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public static boolean isEmptyString(String src) {

		return src == null || src.trim().length() < 1;

	}
	
	public static boolean isNotEmpty(String src){
		return !isEmptyString(src);
	}
	
	public static String getDateString(String format, long millis) {

		Date date = new Date(millis);

		if (isEmptyString(format))
			format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);

	}
	
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
