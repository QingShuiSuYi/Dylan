<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*,java.io.*,java.sql.*,weaver.general.*" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<HTML><HEAD>
<%
//1��/login/VerifyRtxLogin.jsp�Ѿ�����Ҫ���޸���ʱ��
//2��ֻ�и�ҳ����Ҫ����Ӧ�ĵ�����
//3��demo�в��Ե�ַ���£�
//http://127.0.0.1:8080/weavernorth/OutSysLogin/OutSysLogin.jsp?loginid=sysadmin&index=1
//��ȡ�û���¼�������Դ�����������Ҫ��Ӧ�޸Ĳ�ѯ�����sql���
String strLoginid=Util.null2String(request.getParameter("loginid"));
String index=Util.null2String(request.getParameter("index"));
String pageName="";
//��¼��ͬ������Ը���index�ж�,������ת����ҳ��Ĳ�����Ҫ�������������ȷ��
//��ȡ�û�����
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
//ZoomDt������Ϊ�������������Ĺ̶�������Ҳ��д����
//ZoomDt��д���͵��Ż�ҳ�档
String strRedirect = strIpaddress+"/login/VerifyRtxLogin.jsp?loginid="+strLoginid+"&password="+
					strPassWord+"&loginFromOther=ZoomDt&gopage="+pageName;
response.sendRedirect(strRedirect);
%>
