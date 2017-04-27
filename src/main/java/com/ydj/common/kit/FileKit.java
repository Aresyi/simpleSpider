package com.ydj.common.kit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**  
 *
 * @author : Ares.yi
 * @createTime : 2014-11-10 上午11:13:42 
 * @version : 1.0 
 * @description : 
 *
 */
public class FileKit {

	private FileKit(){}
	
	
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

}
