package com.weavernorth.httpconn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.weavernorth.util.FileUtil;
/**
 * 上传附件到泛微系统
 * @author Dylan
 *
 */
public class HttpURLConnrctionToFile {
	
	public static String uploadFile(String uploadUrl, String file) {  
		byte[] bbyte = FileUtil.File2byte(file);
	    String end = "\r\n";  
	    String twoHyphens = "--";  
	    String boundary = "---------------------------Dylan";  
	    try {  
	        URL url = new URL(uploadUrl);  
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();  
	        httpURLConnection.setDoInput(true);  
	        httpURLConnection.setDoOutput(true);  
	        httpURLConnection.setUseCaches(false);  
	        httpURLConnection.setRequestMethod("POST");  
	        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");  
	        httpURLConnection.setRequestProperty("Charset", "UTF-8");  
	        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);  
	   
	        String name = new File(file).getName();
	        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());  
	        dos.writeBytes(twoHyphens + boundary + end);  
	        dos.writeBytes("Content-Disposition: form-data; name=\"file1\"; filename=\""+new String(name.getBytes("UTF-8"), "ISO_8859_1")+"\"" + end);  
	        dos.writeBytes("Content-Type: application/octet-stream;" + end);
	        dos.writeBytes(end);  
	        dos.write(bbyte);  
	        dos.writeBytes(end);  
	        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
	        dos.flush();  
	  
	        // 读取服务器返回结果  
	        InputStream is = httpURLConnection.getInputStream();  
	        InputStreamReader isr = new InputStreamReader(is, "utf-8");  
	        BufferedReader br = new BufferedReader(isr);  
	        String result = br.readLine();  
	        System.out.println("response" + result);  
	        is.close();  
	        return  result;  
	  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return "";  
	}

	

}
