package com.weavernorth.httpconn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.weavernorth.util.FileUtil;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 利用 HttpsURLConnection发送 HTTP或者HTTPS请求
 * 若涉及SSL证书验证，必须在openConnection之前绕过验证
 * 否则就会出现那个著名的异常No subject alternative names matching IP address ***.**.*.*** found
 * 请将请求头的设置放在写出缓存数据之前，否则会提示连接提前终止的异常
 * @author Dylan
 */
public class HttpsURLConnectionUtil {
	
	public static StringBuffer doPut(String param,String filed) throws IOException, JSONException {  
		//查询邮件模块附件地址
		RecordSet rs = new RecordSet();
	    //真实路径
	    String strFilerealpath = "";
	    //查询邮件附件相关信息
	    rs.executeSql("select filename,filerealpath,filesize from mailresourcefile where id = '" + filed + "'");
	    if(rs.next()){
	    	strFilerealpath = Util.null2String(rs.getString("filerealpath"));
	    }
		// 数据处理 转换JSON对象
		JSONObject jsonParams;
		JSONArray arrDetail = null;
		try {
			jsonParams = new JSONObject(param);
			//strAuthrequest为HTTP的PUT请求中的请求协议配置项，为数组类型
			String strAuthrequest = jsonParams.getString("authrequest");
			//请求协议配置项
			arrDetail = new JSONArray(strAuthrequest);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		//绕过SSL验证
		try {
			SslUtils.ignoreSsl();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//存储响应数据
		StringBuffer sbuffer=null;
		System.out.println(arrDetail.getString(1));
        URL uri = new URL(arrDetail.getString(1));  
        HttpsURLConnection conn = (HttpsURLConnection) uri.openConnection(); 
        conn.setDoInput(true);// 允许输入  
        conn.setDoOutput(true);// 允许输出  
        conn.setUseCaches(false); // 不允许使用缓存  
        conn.setRequestMethod(arrDetail.getString(0));//设置请求方式
        if (arrDetail != null) {
        	for (int i = 2; i < arrDetail.length(); i++) {
        		try {
        			String strVal = arrDetail.getString(i);
        			String[] split = strVal.split(":");
        			String substring = strVal.substring(strVal.indexOf(split[0]) + split[0].length() + 2, strVal.length());
        			//设置请求头
        			conn.setRequestProperty(split[0], substring);
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
        	}
        	System.out.println("请求头设置完毕");
        	
        }
        conn.setReadTimeout(10000);//设置读取超时时间          
        conn.setConnectTimeout(10000);//设置连接超时时间           
        conn.connect();
        OutputStream out = conn.getOutputStream();//向对象输出流写出数据，这些数据将存到内存缓冲区中          
        File file = new File(strFilerealpath);
        //判断文件是否存在
		if (file.exists()) {
			System.out.println("上传云盘的文件存在！");
			out.write(FileUtil.File2byte(strFilerealpath));
			out.flush();
			// 关闭流对象,此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中
			out.close();
			System.out.println("写出完毕");
		}else{
			System.out.println("需要上传云盘的文件不存在！");
		}
		sbuffer = new StringBuffer(conn.getResponseCode() + "");
		// 读取响应
		// if (conn.getResponseCode()==200){
		// 从服务器获得一个输入流
		InputStreamReader inputStream = new InputStreamReader(
				conn.getInputStream());// 调用HttpURLConnection连接对象的getInputStream()函数,
										// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
		BufferedReader reader = new BufferedReader(inputStream);
		String lines;
		sbuffer = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "UTF-8");
			sbuffer.append(lines);
		}
		reader.close();
		System.out.println(sbuffer);
		// 断开连接
		conn.disconnect();
		
		return sbuffer;    
        

  
    }  
	// base64解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
