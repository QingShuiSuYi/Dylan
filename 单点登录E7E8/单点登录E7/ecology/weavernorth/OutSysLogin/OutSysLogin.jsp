<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*,java.io.*,java.sql.*,weaver.general.*" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<HTML><HEAD>
<%
//1、/login/VerifyRtxLogin.jsp已经不需要做修改暂时。
//2、只有该页面需要做相应的调整。
//3、demo中测试地址如下：
//http://127.0.0.1:8080/weavernorth/OutSysLogin/OutSysLogin.jsp?loginid=sysadmin&index=1
//获取用户登录名，可以传入其它，需要对应修改查询密码的sql语句
String strLoginid=Util.null2String(request.getParameter("loginid"));
String index=Util.null2String(request.getParameter("index"));
String pageName="";
//登录不同界面可以根据index判断,具体跳转到的页面的参数需要做具体的问题来确定
//获取用户密码
String strPassWord = "";
RecordSet.executeSql("select password from hrmresource where loginid='"+strLoginid+"' union all select password from HrmResourceManager where loginid='"+strLoginid+"'");
		if(RecordSet.next()){
		strPassWord =Util.null2String(RecordSet.getString("password"));
		}
System.out.println("strPassWord: "+strPassWord);		
if("1".equals(index)){
	pageName="/car/CarInfoMaintenance_frm.jsp?subCompanyId=-1";
}

String strIpaddress = RecordSet.getPropValue("OutSysLogin", "ipaddress");
//ZoomDt可以做为第三方传过来的固定参数，也可写死。
//ZoomDt不写，就到门户页面。
String strRedirect = strIpaddress+"/login/VerifyRtxLogin.jsp?loginid="+strLoginid+"&password="+
					strPassWord+"&loginFromOther=ZoomDt&gopage="+pageName;
response.sendRedirect(strRedirect);
%>
