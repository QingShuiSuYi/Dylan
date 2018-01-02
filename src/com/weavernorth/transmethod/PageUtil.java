package com.weavernorth.transmethod;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.ConnStatement;
import weaver.conn.RecordSet;
import weaver.general.Util;

import com.weavernorth.util.LogUtil;

/**
 * 报表工具类
 * @author Dylan
 *
 */
public class PageUtil {

	/**
     * 获取能不能进行操作，进行权限判断
     * @param id
     * @param userid
     * @return
     */
    public ArrayList<String> getCanOperation(String id){
        ArrayList<String> resultlist = new ArrayList<String>();
        resultlist.add("true");
        resultlist.add("true");
        return resultlist;
    }
    /**
     * 显示时剪短信息长度
     * @param content
     * @return
     */
    public String getContentAll(String content,String classname){
    	if(classname.equals("FWBJ:AudioMsg")){
    		switch (Integer.parseInt(content)) {
			case 0:
				return "<span>音频通话：已拒绝</span>";
			case 2:
				return "<span>音频通话：正常通话</span>";
			case 4:
				return "<span>音频通话：已取消</span>";
			case 1:
				return "<span>音频通话：已取消</span>";
			case 3:
				return "<span>音频通话：已取消</span>";
			default:
				return "<span>未定义</span>";
			}
    	}else if(classname.equals("RC:VcMsg")){
    		return "<span>语音通话</span>";
    	}else{
        	if(content.length() < 20){
        		return content;
        	}else{
        		return content.substring(0,20) + "...";
        	}
    	}

    }
    
    /**
     * 获取文件大小
     * @param strExtra
     * @param imguri
     * @return
     */
    public String getSize(String strExtra,String imguri){
    	String size = "0.00KB";
    	LogUtil.doWriteLog("getSize----imguri----:" + imguri);
    	
    	
    	
    	if(imguri.split("\\+")[1].trim().equals("RC:ImgMsg")){
    		imguri = imguri.split("\\+")[0];
    		RecordSet rs = new RecordSet();
    		rs.executeSql(" select filesize from ImageFile b where imagefileid = '"+imguri+"'");
    		if(rs.next()){
    			String fileSize = Util.null2String(rs.getString("filesize"));
    			if(!fileSize.equals("")){
    				size = fileSize;
    	    		int parseInt = Integer.parseInt(size);
    	    		double dd = parseInt*1.0/1024;
    	    		DecimalFormat df = new DecimalFormat("######0.00");
    	    		size = df.format(dd) + "KB";
    			}
    		}
    	}else if(imguri.split("\\+")[1].trim().equals("FW:attachmentMsg")){
    		JSONObject jsonExtra;
    		String strSize ="";
    		try {
    			jsonExtra=new JSONObject(strExtra);
    			strSize = jsonExtra.getString("fileSize");
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    		
    		int parseInt = Integer.parseInt(strSize);
    		double dd = parseInt*1.0/1024;
    		DecimalFormat df = new DecimalFormat("######0.00");
    		size = df.format(dd) + "KB";
    	}else if(imguri.split("\\+")[1].trim().equals("RC:VcMsg")){
    		int length = strExtra.length()/2;
    		double dd = length*1.0/1024;
    		DecimalFormat df = new DecimalFormat("######0.00");
    		size = df.format(dd) + "KB";
    	}
    	
    	
    	return size;
    }

    
}
