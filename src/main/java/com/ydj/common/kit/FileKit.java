package com.ydj.common.kit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private FileKit() {
	}

	/**
	 * 写保存文件
	 * 
	 * @param saveAsPath
	 * @param fileName
	 * @param fileContent
	 * @param append
	 * @throws IOException
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月28日 下午4:07:08
	 */
	public static void save2File(String saveAsPath, String fileName,
			String fileContent, boolean append) throws IOException {
		// long now = System.currentTimeMillis();

		FileWriter fw = null;

		File path = new File(saveAsPath);
		if (!path.exists()) {
			path.mkdirs();
		}

		File file = new File(saveAsPath + fileName);

		// if(file.exists()){
		// fileName = now+"_"+fileName;
		// file = new File(saveAsPath+fileName);
		// }

		fw = new FileWriter(file, append);

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

	/**
	 * 写保存文件
	 * 
	 * @param saveAsPath
	 * @param fileName
	 * @param fileContent
	 * @param charset
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月28日 下午4:07:27
	 */
	public static void save2File(String saveAsPath, String fileName,
			String fileContent, String charset) {

		File path = new File(saveAsPath);
		if (!path.exists()) {
			path.mkdirs();
		}

		File file = new File(saveAsPath + fileName);

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true), charset));
			out.write(fileContent + "\r\n");
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

	/**
	 * 读取文本文件
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 *
	 * @author : Ares.yi
	 * @createTime : 2017年4月28日 下午4:07:40
	 */
	public static String readTxtFile(String filePath, String encoding) {

		StringBuffer sbf = new StringBuffer();
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {

					sbf.append(lineTxt);

				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		return sbf.toString();

	}

}
