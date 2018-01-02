package com.weavernorth.createDoc.webservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.axis.encoding.Base64;

import com.weavernorth.util.FileUtil;
import com.weavernorth.util.LogUtil;

import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocService;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;

public class DocCreateService {
	private static DocService service = null;
	private static String strLoginId = "";
	private static String strPassword = "";
	private static String strIP = "127.0.0.1";
	private static String strSession = "";
	/**
	 * 有参构造
	 * @param strLoginIdPa 登录账号
	 * @param strPassWordPa 登录密码
	 */
	public  DocCreateService(String strLoginIdPa,String strPassWordPa){
		try {
			
			service = new DocServiceImpl();
			DocCreateService.strLoginId = strLoginIdPa;
			DocCreateService.strPassword = strPassWordPa;
			int logintype = 0;
			//登陆验证
			strSession = getSession(strLoginId,strPassword,logintype,strIP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param strFileName 文档名称
	 * @param strFileRealPath 文档路径
	 * strFileId  文档存储目录ID
	 */
	public String init(byte[] bytes,String strFjName,String strFjhz){
		BaseBean bb = new BaseBean();
		//文件名
		String strFileName = strFjName + "." + strFjhz;
		//文件临时存放路径
		String strFilePath = bb.getPropValue("zjDocumentFlow", "filepath");
		//判断该路径是否存在，不存在则创建
		FileUtil.judeDirExists(new File(strFilePath));
		//将数组转换成文件
		FileUtil.byte2File(bytes, strFilePath, strFileName);
		//文件真实路径
		String strFileRealPath = strFilePath + strFileName;
		//知识模块存储目录
		String strFileId = bb.getPropValue("zjDocumentFlow", "catalog");
		//创建文档
		String strCreateNewDoc = "-1";
		try {
			strCreateNewDoc =  createNewDoc(strSession,strFileName,strFileRealPath,strFileId);
		} catch (Exception e) {
			LogUtil.releaseLog("创建文档异常"+e);
		}
		
		//成功后删除临时文件
		if(Integer.parseInt(strCreateNewDoc) > 0){
			if(FileUtil.delete(strFileRealPath)){
				LogUtil.doWriteLog("成功创建后删除临时文件成功");
			}else{
				LogUtil.doWriteLog("成功创建后删除临时文件失败");
			};
			
		}
		
		return strCreateNewDoc;
	}
	/**
	 * 验证登陆
	 * @return session
	 * @throws Exception 
	 */
	private static String getSession(String loginid,String password,int logintype,String ip) throws Exception{
		// 登陆 //用户名，密码，登陆方式(0 数据库验证;1 动态密码验证;2 LDAP验证)，ip
		String session = service.login(loginid, password, 0, ip);
		LogUtil.debugLog("登录成功，strSession"+session);
		return session;
	}
	
	
	
	/**
	 * 创建新文档
	 * @param session 登陆session
	 * @param strFileRealPath 文档实际存放路径 -绝对路径 
	 * @throws Exception 
	 */
	public static String createNewDoc(String session,String strFileName,String strFileRealPath,String strFileId) throws Exception{
		byte[] content = new byte[102400];
		// 上传附件，创建html文档
		int intFileId = Integer.parseInt(strFileId);
		//String strImagefileid = "";
		content = null;

        /**
         * 获取文件byte数组
         */
		try {
			int byteread;
			byte data[] = new byte[1024];
			InputStream input = new FileInputStream(new File(strFileRealPath));

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((byteread = input.read(data)) != -1) {
				out.write(data, 0, byteread);
				out.flush();
			}
			content = out.toByteArray();
			input.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        /**
         * DocAttachment此对象为附件对象，每创建一个DocAttachment，
         * 代表创建一个文档中的附件
         */
		DocAttachment da = new DocAttachment();
			
		da.setDocid(0);
		da.setImagefileid(0);
		da.setFilecontent(Base64.encode(content));
		da.setFilerealpath(strFileRealPath);
		da.setIszip(1);
		da.setFilename(strFileName);
	
		da.setDocfiletype("3");
		da.setIsextfile("0");

        //创建文档
        DocInfo doc = new DocInfo();
		doc.setDoccreaterid(3);//
		doc.setDoccreatertype(0);
		doc.setAccessorycount(1);
		//doc.setMaincategory(10);//主目录id
		//doc.setSubcategory(6);//分目录id
		doc.setSeccategory(intFileId);//子目录id
		doc.setOwnerid(3);
		doc.setDocStatus(1);
		doc.setId(0);
		doc.setDocType(2);
		//文档的主题设置
		doc.setDocSubject(strFileName);
		//文档的正文内容，可以识别HTML标签
		doc.setDoccontent(strFileName);
		//将之前创建好的附件数组关联到这篇文档中
		doc.setAttachments(new DocAttachment[] { da });
		int strdocid = service.createDoc(doc, session); 
		LogUtil.doWriteLog("新文档id:"+strdocid);

		
		return strdocid + "";
	}
}

