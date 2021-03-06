<%@ page import="weaver.general.Util,
                 java.util.Map,
                 java.util.HashMap,
                 weaver.hrm.settings.RemindSettings" %>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.hrm.OnLineMonitor"%>
<%@ page import="weaver.systeminfo.template.UserTemplate"%>
<jsp:useBean id="verifylogin" class="weaver.login.VerifyRtxLogin" scope="page" /> 
<jsp:useBean id="rsExtend" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="DocCheckInOutUtil" class="weaver.docs.docs.DocCheckInOutUtil" scope="page"/>
<jsp:useBean id="PoppupRemindInfoUtil" class="weaver.workflow.msg.PoppupRemindInfoUtil" scope="page"/>
<jsp:useBean id="RtxLdaplog" class="weaver.rtx.RtxLdapLog" scope="page"/>
<%@ page language="java" contentType="text/html; charset=GBK" %>

<%
String urlfrom = Util.null2String(request.getParameter("urlfrom")) ;
String para = Util.null2String(request.getParameter("para")) ;
String linkurltmp = para;
if(urlfrom.equals("workflowtodoc")){//针对流程存为文档
	verifylogin.setIsfromwftodoc(1);
	para = Util.null2String(request.getParameter("para1")) + "#" + Util.null2String(request.getParameter("para2")) + "#" + Util.null2String(request.getParameter("para3"));
	String para1 = Util.null2String(request.getParameter("para1"));
	String requestid = "-1";
	int index = para1.indexOf("requestid=");
	if(index>0) requestid = para1.substring(index+10);
	session.setAttribute("urlfrom_workflowtodoc_"+requestid,"true");
}else{
	para = PoppupRemindInfoUtil.decrypt(para);
}

String str[] = Util.TokenizerString2(para,"#");

String loginfile = Util.null2String(request.getParameter("loginfile")) ;
String logintype = Util.null2String(request.getParameter("logintype")) ;
/**
 * 2016-09-27 乔轩 修改 开始           
 */
String loginid = "";
String userpassword = "";
String RedirectFile = "";
if(!"".equals(para)) {
	loginid = Util.null2String(str[1]);
	userpassword = Util.null2String(str[2]);
	RedirectFile = Util.null2String(str[0]);
} else {
    loginid = Util.null2String(request.getParameter("loginid"));
	userpassword = Util.null2String(request.getParameter("password"));
	RedirectFile = "/main.jsp";
}

if(logintype.equals("")) logintype = "1";

//解决rtx消息提醒是ldap登录验证(ldap是没有loginid和password)td13185
if(!"1".equals(session.getAttribute("istest"))){//流程测试不验证rtx
 boolean rtxladptmp = RtxLdaplog.checkRtxLadp(loginid,linkurltmp);
 if(rtxladptmp && !urlfrom.equals("workflowtodoc")) {
  response.sendRedirect("/login/Login.jsp?logintype=1&message=19");
  return;
 }
}
/**
 * 2016-09-27 乔轩 修改 结束           
 */ 
String serial=Util.null2String(request.getParameter("serial"));
String username = Util.null2String(request.getParameter("username"));
String rnd=Util.null2String(request.getParameter("rnd"));
String loginPDA=Util.null2String(request.getParameter("loginPDA"));
session.setAttribute("loginPAD",loginPDA);
if(RedirectFile.indexOf("/main.jsp")>-1&&"1".equals(session.getAttribute("istest"))){
    session.removeAttribute("istest");
}
if (loginPDA.equals("1"))
{
RedirectFile= "/mainPDA.jsp";
}

String validatecode=Util.null2String(request.getParameter("validatecode"));


String gopage=Util.null2String(request.getParameter("gopage"));
/**
 * 2016-09-12 乔轩 修改 开始           
 */

String loginFromOther=Util.null2String(request.getParameter("loginFromOther"));
if(gopage.length()>0){
	if(loginFromOther.length()>0){
		RedirectFile = gopage ;
	}else{
		RedirectFile = "/main.jsp?gopage="+gopage ;
	}
}
System.out.println("RedirectFile="+RedirectFile+"##############"+"gopage="+gopage);
/**
 * 2016-09-12 乔轩 修改 结束           
 */ 

if (loginfile.equals("")) loginfile="/login/Login.jsp?logintype="+logintype+"&gopage="+gopage;

Cookie[] ck = request.getCookies();
int ckLength = 0 ;
try {
	ckLength = ck.length;
} catch (NullPointerException ex) {//流程存为文档，读不到cookie
	//response.sendRedirect("/login/Login.jsp?logintype="+logintype+"&noAllowIe=yes") ;
	//return ;
}

String isIE = Util.null2String(request.getParameter("isie"));
if(isIE.equals("false")){
	isIE = "true";
}
session.setAttribute("browser_isie",isIE);


if (logintype.equals("2")) RedirectFile = "/portal/main.jsp" ;

if(loginid.equals("") || userpassword.equals("") ) response.sendRedirect(loginfile + "&message=18") ;
else  { RemindSettings settings=(RemindSettings)application.getAttribute("hrmsettings");
    String needusb=settings.getNeedusb();
	String usercheck ;
	if(needusb!=null&&needusb.equals("1")&& false){//屏蔽usbkey
			usercheck= verifylogin.getUserCheck(request,response,loginid,userpassword,serial,username,rnd,logintype,loginfile,validatecode);
	}else{
		usercheck= verifylogin.getUserCheck(request,response,loginid,userpassword,logintype,loginfile,validatecode);
	}
	System.out.println("usercheck="+usercheck+"##############");
	if(usercheck.equals("15") || usercheck.equals("16") || usercheck.equals("17")|| usercheck.equals("45")|| usercheck.equals("46")|| usercheck.equals("47")|| usercheck.equals("52")|| usercheck.equals("55"))
    {
        String tmploginid=(String)session.getAttribute("tmploginid");
        if(tmploginid!=null&&loginid.equals(tmploginid))
           session.setAttribute("tmploginid1",loginid);
        else
           session.removeAttribute("tmploginid");
        response.sendRedirect(loginfile + "&message="+usercheck) ;
	}
	else if(usercheck.equals("19")){
		response.sendRedirect("/system/InLicense.jsp") ;
	} else if (usercheck.equals("26")) {
		response.sendRedirect("/login/Login.jsp?logintype=1&message="+usercheck);
	}else if(usercheck.equals("101")){
        session.setAttribute("tmploginid",loginid);
        session.setAttribute("tmploginid1",loginid);
        if (!loginPDA.equals("1")) {
        response.sendRedirect("/login/Login.jsp?logintype=1&message="+usercheck) ;
		}
		else
		{
		response.sendRedirect("/login/LoginPDA.jsp?logintype=1&message="+usercheck) ;
		}
    }
	else {
            User user = HrmUserVarify.getUser (request , response) ;

            if(user == null) { response.sendRedirect(loginfile ) ; return;}
            session.setAttribute("password",userpassword);
	        session.setAttribute("fromlogin","yes");
            session.removeAttribute("tmploginid");
            session.setAttribute("RtxFromLogin","true");

            DocCheckInOutUtil.docCheckInWhenVerifyLogin(user,request);

		if(request.getSession(true).getAttribute("layoutStyle")!=null && request.getSession(true).getAttribute("layoutStyle").equals("1")){
			session.setAttribute("istimeout","no");
%>
			<SCRIPT LANGUAGE=VBS>
				window.parent.returnvalue = "1"
				window.parent.close
			</script>
<%
		}else{
			if("2".equals(logintype)){
				response.sendRedirect(RedirectFile) ;
				return;
			} else {
				UserTemplate  ut=new UserTemplate();
				
				ut.getTemplateByUID(user.getUID(),user.getUserSubCompany1());
				int templateId=ut.getTemplateId();
				int extendTempletid=ut.getExtendtempletid();
				int extendtempletvalueid=ut.getExtendtempletvalueid();

				if(extendTempletid!=0){
					rsExtend.executeSql("select id,extendname,extendurl from extendHomepage  where id="+extendTempletid);
					if(rsExtend.next()){
						int id=Util.getIntValue(rsExtend.getString("id"));
						String extendurl=Util.null2String(rsExtend.getString("extendurl"));	
						/**
						 * 2016-09-27 乔轩 修改 开始           
						 */
						if(para.length()>0||loginFromOther.length()>0){
						/**
						 * 2016-09-27 乔轩 修改 结束           
						 */
							response.sendRedirect(RedirectFile);
						}else{
							response.sendRedirect(extendurl+"/index.jsp") ;
						}
						return;				  
					}
				} else {
					response.sendRedirect(RedirectFile) ;
					return;
				}
			}
		}
	}
}
%>