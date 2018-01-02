package com.weavernorth.transmethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.file.ImageFileManager;

import com.weavernorth.util.LogUtil;

public class EMessageTransMethod {
	
	/**getMsgType
	 * 根据消息类别的消息标志获取消息类别
	 * strMessageType ：消息标识
	 * strJsonExtra： extra的json串
	 * 返回：strMessageType：对应的中文类型
	 *           
	 */
	public String getMsgType(String strMessageType ,String strJsonExtra){
		LogUtil.doWriteLog("-------EMessageTransMethod----getMsgType-----"+strMessageType+"-----"+strJsonExtra);
		
		
		//返回类别
		String strTypeName="";
		try {
			JSONObject  json=null;
		if("FW:CustomShareMsg".equals(strMessageType.trim())){
			json=new JSONObject(strJsonExtra);

		}
	
		
		
		if ("FW:attachmentMsg".equals(strMessageType.trim())) {
		//附件	
			strTypeName="<span>附件</span>";
		}else if ("FW:CustomShareMsg".equals(strMessageType.trim()) &&"doc".equals(json.get("sharetype"))) {
			//文档
			strTypeName="<span>文档</span>";
		}else if ("FW:CustomShareMsg".equals(strMessageType.trim()) &&"workflow".equals(json.get("sharetype"))) {
			//流程
			strTypeName="<span>流程</span>";
		}else if ("RC:ImgMsg".equals(strMessageType.trim())) {
			//图片	
			strTypeName="<span>图片</span>";
		}else if ("RC:TxtMsg".equals(strMessageType.trim())) {
			//文本	
			strTypeName="<span>文本</span>";
		}else if ("RC:VcMsg".equals(strMessageType.trim())) {
			//文本	
			strTypeName="<span>语音</span>";
		}
		
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.doWriteLog("----getMsgType--error----"+e.toString());
		}
		return strTypeName;
	}
	/**
	 * getName
	 *根据对象的id 获取对象的名称
	 *
	 * strTargetId：对象的id 
	 * 
	 * 返回：strObjectName： 返回人员的名称或者群名
	 *           
	 */
	public String getName(String strTargetId){
		
		LogUtil.doWriteLog("-------EMessageTransMethod----getName-----"+strTargetId);
		
		if(strTargetId.equals("所有人")){
			return strTargetId;
		}
		
		String strObjectName="";
		RecordSet rs =new RecordSet();
		boolean flag=false;
		rs.execute("select lastname from hrmresource where  id="+strTargetId);
		if(rs.next()){
			strObjectName="<span>"+rs.getString("lastname")+"</span>";
			flag=true;
		}else{
			
			flag=false;
			
		}
		if(!flag){
			rs.execute("select targetname from social_imconversation where  targetid='"+strTargetId+"'");
			
			if(rs.next()){
				strObjectName="<span>群名："+rs.getString("targetname")+"</span>";
				
				
			}
			
		}
	
	return strObjectName;
	}
	
	
	/**
	 * getContent
	 *根据对象的id 获取对象的名称
	 *
	 * strTargetId：对象的id 
	 * strJson：里面包含 消息标识 和 extra
	 * 返回：strObjectName： 返回人员的名称或者群名
	 * @throws IOException 
	 *           
	 */
	public String getContent(String strContent ,String strJson) throws IOException{
		String fileEncode = System.getProperty("file.encoding"); 
		
		LogUtil.doWriteLog("-------EMessageTransMethod----getContent-----"+strJson+"----"+strContent);
		//返回类别
		String strTypeName="";
		try {
			//strJsonExtra=strJsonExtra.replaceAll("["]", "[\]["]")
		String[]  strOtherpara=strJson.split("[+]");
		//消息标识
	String strMessageType="";
	strMessageType=strOtherpara[0];
	//发送人id
	String strId=strOtherpara[1];
	//imageuri
	String strImageURI="";
	strImageURI=strOtherpara[2];
	
	//extra
	String strExtra="";
	for(int i = 3;i < strOtherpara.length;i++){
		strExtra += strOtherpara[i];
	}
	
	//	
	JSONObject  jsonExtra=null;
	LogUtil.doWriteLog("-------EMessageTransMethod----getContent-----"+strMessageType+"----"+strId+"-----"+strImageURI+"-----"+strExtra);
	
	if(!"RC:ImgMsg".equals(strMessageType.trim())){
		
		jsonExtra=new JSONObject(strExtra);
		
			
	}
		if ("FW:attachmentMsg".equals(strMessageType.trim())) {
		//附件	
			//LogUtil.doWriteLog("-------附件------");
			///usr/weaver/ecology/ftptemp/
//			ImageFileManager im = new ImageFileManager();
//			im.getImageFileInfoById(Integer.parseInt(jsonExtra.getString("fileid")));
//			InputStream inputStream = im.getInputStream();
//			OutputStream  os = new FileOutputStream(new String(("/usr/weaver/ecology/weavernorth/msg/filetemp/" + strContent).getBytes("UTF-8"),fileEncode));
//	        //文件拷贝      
//	        byte flush[]  = new byte[1024];  
//	        int len = 0;  
//	        while(0<=(len=inputStream.read(flush))){  
//	            os.write(flush, 0, len);  
//	        }  
//	        //关闭流的注意 先打开的后关  
//	        os.close();  
//	        inputStream.close(); 
			
			strTypeName = "<a href=\"/weaver/weaver.file.FileDownload?fileid="+jsonExtra.getString("fileid")+"&download=1\"   target=\"_blank\" >"+strContent+"</a>";
			LogUtil.doWriteLog("-------附件返回值------" + strTypeName);
			//strTypeName="<a href=\"javascript:this.openFullWindowForXtable('/docs/docs/DocDsp.jsp?id="+jsonExtra.getString("fileid")+"')\" class=\"opdiv\" _fileid=\""+jsonExtra.getString("fileid")+"\" onclick=\"downAccFile(this)\" _filename=\""+strContent+"\">"+strContent+"</a>";
			//strTypeName = "<a style=\"word-break: break-all; word-wrap: break-word;\" href=\"javascript:this.openFullWindowForXtable('/docs/docs/DocDsp.jsp?id="+jsonExtra.getString("fileid")+"')\">"+strContent+"</a>";
		}else if ("FW:CustomShareMsg".equals(strMessageType.trim()) &&"doc".equals(jsonExtra.get("sharetype"))) {
			//文档
			//LogUtil.doWriteLog("-------文档------");
			strTypeName="<div class=\"shareDetail opdiv\" onclick=\"viewShare(this)\" _shareid=\""+jsonExtra.getString("shareid")+"\" _sharetype=\"doc\" _senderid=\""+strId+"\">"+strContent+"	</div>";
		}else if ("FW:CustomShareMsg".equals(strMessageType.trim()) &&"workflow".equals(jsonExtra.get("sharetype"))) {
			//流程
			//LogUtil.doWriteLog("-------流程------");
			strTypeName="<div class=\"shareDetail opdiv\" onclick=\"viewShare(this)\" _shareid=\""+jsonExtra.getString("shareid")+"\" _sharetype=\"workflow\" _senderid=\""+strId+"\">"+strContent+"</div>";
		}else if ("RC:ImgMsg".equals(strMessageType.trim())) {
			//图片	
			//LogUtil.doWriteLog("-------图片------");
			RecordSet rs=new RecordSet();
			rs.execute("select imagefilename from imagefile where imagefileid="+strImageURI);
			if(rs.next()){
				//strTypeName="<a href=\"data:image/jpg;base64,"+strContent+"\"   target=\"_blank\" onclick=\"AHrefclick(this)\" >"+rs.getString("imagefilename")+" </a>";
				strTypeName = "<a href=\"/weaver/weaver.file.FileDownload?fileid="+strImageURI+"&download=1\"   target=\"_blank\" >"+rs.getString("imagefilename")+"</a>";
			}else{
				strTypeName="<span>没有找到该图片信息 </span>";
				
				
			}
			
			
		}else if ("RC:TxtMsg".equals(strMessageType.trim())) {
			LogUtil.doWriteLog("-------文本------");
			//文本	
			strTypeName="<span>"+strContent+"</span>";
		}else{
			LogUtil.doWriteLog("-------语音------");
			//文本	
			//strTypeName="<span>语音</span>";
			//strTypeName="<a href=\"data:audio/amr;base64,"+strContent+"\"   target=\"_blank\" onclick=\"AHrefclick(this)\" >语音 </a>";
			//strTypeName="<a href=\"javascript:this.openFullWindowForXtable('/weavernorth/msg/amr.jsp?type="+strContent+"')\" class=\"opdiv\">语音</a>";
			strTypeName="<button  type=\"button\" onclick=\"bf('"+strContent+"')\">语音</button>";
			//strTypeName="<a href=\"javascript:bf('"+strContent+"')\" class=\"opdiv\">语音</a>";
		}
		
		
		} catch (JSONException e) {
			//strTypeName="<a href=\"data:audio/amr;base64,"+strContent+"\"   target=\"_blank\" onclick=\"AHrefclick(this)\" >语音 </a>";
			//strTypeName="<a href=\"javascript:this.openFullWindowForXtable('/weavernorth/msg/amr.jsp?type="+strContent+"')\" class=\"opdiv\">语音</a>";
			//strTypeName="<a href=\"javascript:bf('"+strContent+"')\" class=\"opdiv\">语音</a>";
			strTypeName="<button type=\"button\" onclick=\"bf('"+strContent+"')\">语音</button>";
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.doWriteLog("----getContent--error----"+e.toString());
		}
		LogUtil.doWriteLog("-------返回显示------"+strTypeName);
		return strTypeName;
	}
	
	

	
	
	
	public void deleteFile(String strMsgID){
		RecordSet rs =new RecordSet();
		RecordSet rs1 =new RecordSet();
		
		//类型
		String strType="";
		//其它
		String strExtra="";
		//图片路径
		String strImageUri="";

		JSONObject  jsonExtra=null;
		//文件id
	String strFileId="";
		try {
		
		rs.execute("select classname,extra,imageuri from historymsg where  type='"+strMsgID+"'");
			if(rs.next()){
				
				strType=rs.getString("classname");
				strExtra=rs.getString("extra");
				strImageUri=rs.getString("imageuri");
			}

			if(!"RC:ImgMsg".equals(strType.trim())){
				
			
					jsonExtra=new JSONObject(strExtra);
				
						
			}
		if("FW:attachmentMsg".equals(strType.trim())){
			
			strFileId=jsonExtra.getString("fileid");
		}else if("RC:ImgMsg".equals(strType.trim())){
			
			strFileId=strImageUri;
			
		}
		
		if(!"".equals(strFileId)){
			
			
			rs.execute("select filerealpath,imagefileid from imagefile where imagefileid="+strFileId+"");
			boolean flag=false;
			while(rs.next()){
				
				File f = new File(rs.getString("filerealpath"));
				if(f.exists()){
					
					flag=f.delete();
					if(flag){
						rs1.execute("update  social_imfile set isdel=1 where fileid='"+rs.getString("imagefileid")+"'");
						
						rs1.execute("commit;");
						
					}else{
						LogUtil.doWriteLog("------EMessageTransMethod--------deleteFile-"+rs.getString("imagefileid")+"-删除失败----");
						
					}
				}else{
					LogUtil.doWriteLog("------EMessageTransMethod--------deleteFile-"+rs.getString("imagefileid")+"文件不存在-----");
										
				}
			 	
				
			}

			
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		LogUtil.doWriteLog("-------EMessageTransMethod--------deleteFile--"+e.toString());
		}
		
	}
	/**
	 * 查找字符串
	 * @param str
	 * @param c
	 * @param times
	 * @return
	 */
    public static int getIndex(String str, String c, int times) {
        int index = 0;
        String[] arr = str.split(c);
        int length = arr.length > times ? times : arr.length;
        for (int i = 0; i < length; i++) {
            index += arr[i].length();
        }
        return index + times - 1;
    }
	
	
	
	
}
