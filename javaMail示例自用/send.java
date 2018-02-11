package com.weavernorth.mail.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class send {

	public static void main(String[] args){  
		//发送地址
		String strMail2="511404506@qq.com";
		//抄送地址，多个地址之间用逗号分隔
		String strCopy2="344625445@qq.com";
		String strBCopy2="344625445@qq.com";
		
   	 	//判断邮件接收者地址是否为空，如果为空，则不做任何处理。
		if(strMail2!=null&&!"".equals(strMail2)){
			//这个类主要是设置邮件  
			MailSenderInfo mailInfo = new MailSenderInfo();
			// 发送邮件的服务器的IP 
			mailInfo.setMailServerHost("smtp.126.com");   
			// 发送邮件的服务器的端口 
			mailInfo.setMailServerPort("25");   
			// 是否需要身份验证   
			mailInfo.setValidate(true);   
			// 登陆邮件发送服务器的用户名和密码   
			mailInfo.setUserName("13933823602@126.com");   
			mailInfo.setPassword("dylan1994");   
			// 邮件发送者的地址   
	   	 	mailInfo.setFromAddress("13933823602@126.com"); 
	   	 	// 邮件接收者的地址    
	   	 	mailInfo.setToAddress(strMail2);
	   	 	// 抄送的地址。
	   	 	mailInfo.setCcAddress(strCopy2);
	   	 	// 密送的地址。
		   	mailInfo.setBccAddress(strBCopy2);
	     
	   	 	// 邮件主题   
	   	 	mailInfo.setSubject("设置邮件标题");   
	   	 	// 邮件的文本内容   
	   	 	mailInfo.setContent("设置邮件内容");   
	   	 	//所有附件（绝对路径），发送html格式时有效 
	     	Map<String,String> map=new HashMap<String,String>();
	     	map.put("filepathname", "F:/上单0杠5.txt");
	     	map.put("filerealname", "0杠5.txt");
	     	List<Map<String,String>> listFiles=new ArrayList<Map<String, String>>();
	     	listFiles.add(map);
	     	mailInfo.setListFiles(listFiles);
	     	
	     	//发送邮件  
	     	MailSender sms = new MailSender();  
	     	sms.sendTextMail(mailInfo);//发送文本格式
	     
	     //	MailSender.sendHtmlMail(mailInfo);//发送html格式  
			
		}
	}  
}
