<%@ page import="weaver.general.*"%>
<%@ page import="weaver.conn.*"%>
<%@ page import="java.util.*,java.sql.Timestamp"%>
<%@ page import="weaver.hrm.User,weaver.hrm.OnLineMonitor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="BaseBean" class="weaver.general.BaseBean" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<HTML>
	<head>
		<title>Login..</title>
	</head>
	<BODY>
		<%
			try {
				String email = session.getAttribute("email").toString();
				if ("".equals(email)) {
					response.sendRedirect("/");
				}

				String loginPage = Util.null2String(new String(Util.null2String(BaseBean.getPropValue("qc127296", "loginPage")).getBytes("ISO-8859-1"), "gbk")).trim();
				
				rs.executeSql("select * from HrmResource where loginid='" + email + "'");
				User user_new = null;
				if (rs.next()) {//OA有相关人员
					user_new = new User();
					user_new.setUid(rs.getInt("id"));
					user_new.setLoginid(rs.getString("loginid"));
					user_new.setFirstname(rs.getString("firstname"));
					user_new.setLastname(rs.getString("lastname"));
					user_new.setAliasname(rs.getString("aliasname"));
					user_new.setTitle(rs.getString("title"));
					user_new.setTitlelocation(rs.getString("titlelocation"));
					user_new.setSex(rs.getString("sex"));
					user_new.setPwd(rs.getString("password"));
					String languageidweaver = rs.getString("systemlanguage");
					user_new.setLanguage(Util.getIntValue(languageidweaver, 0));

					user_new.setTelephone(rs.getString("telephone"));
					user_new.setMobile(rs.getString("mobile"));
					user_new.setMobilecall(rs.getString("mobilecall"));
					user_new.setEmail(rs.getString("email"));
					user_new.setCountryid(rs.getString("countryid"));
					user_new.setLocationid(rs.getString("locationid"));
					user_new.setResourcetype(rs.getString("resourcetype"));
					user_new.setStartdate(rs.getString("startdate"));
					user_new.setEnddate(rs.getString("enddate"));
					user_new.setContractdate(rs.getString("contractdate"));
					user_new.setJobtitle(rs.getString("jobtitle"));
					user_new.setJobgroup(rs.getString("jobgroup"));
					user_new.setJobactivity(rs.getString("jobactivity"));
					user_new.setJoblevel(rs.getString("joblevel"));
					user_new.setSeclevel(rs.getString("seclevel"));
					user_new.setUserDepartment(Util.getIntValue(rs.getString("departmentid"), 0));
					user_new.setUserSubCompany1(Util.getIntValue(rs.getString("subcompanyid1"), 0));
					user_new.setUserSubCompany2(Util.getIntValue(rs.getString("subcompanyid2"), 0));
					user_new.setUserSubCompany3(Util.getIntValue(rs.getString("subcompanyid3"), 0));
					user_new.setUserSubCompany4(Util.getIntValue(rs.getString("subcompanyid4"), 0));
					user_new.setManagerid(rs.getString("managerid"));
					user_new.setAssistantid(rs.getString("assistantid"));
					user_new.setPurchaselimit(rs.getString("purchaselimit"));
					user_new.setCurrencyid(rs.getString("currencyid"));
					user_new.setLastlogindate(rs.getString("currentdate"));
					user_new.setLogintype("1");
					user_new.setAccount(rs.getString("account"));

					user_new.setLoginip(request.getRemoteAddr());
					request.getSession(true).setMaxInactiveInterval(60 * 60 * 24);
					request.getSession(true).setAttribute("weaver_user@bean", user_new);

					request.getSession(true).setAttribute("moniter", new OnLineMonitor("" + user_new.getUID(), user_new.getLoginip()));
					//Util.setCookie(response, "loginfileweaver", loginPage, 172800);
					//Util.setCookie(response, "loginidweaver", "" + user_new.getUID(), 172800);
					//Util.setCookie(response, "languageidweaver", languageidweaver, 172800);

					Map logmessages = (Map) application.getAttribute("logmessages");
					if (logmessages == null) {
						logmessages = new HashMap();
						logmessages.put("" + user_new.getUID(), "");
						application.setAttribute("logmessages", logmessages);
					}

					request.getSession(true).setAttribute("logmessage", getLogMessage(user_new.getUID() + ""));
					response.sendRedirect(loginPage);
				} else {//OA中查无此人，必须重新登录
					response.sendRedirect("/");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		%>
		<%!private String getLogMessage(String uid) {
		String message = "";
		RecordSet rs = new RecordSet();
		String sqltmp = "";
		if (rs.getDBType().equals("oracle")) {
			sqltmp = "select * from (select * from SysMaintenanceLog where relatedid = " + uid + " and operatetype='6' and operateitem='60' order by id desc ) where rownum=1 ";
		} else if (rs.getDBType().equals("db2")) {
			sqltmp = "select * from SysMaintenanceLog where relatedid = " + uid + " and operatetype='6' and operateitem='60' order by id desc fetch first 1 rows only ";
		} else {
			sqltmp = "select top 1 * from SysMaintenanceLog where relatedid = " + uid + " and operatetype='6' and operateitem='60' order by id desc";
		}

		rs.executeSql(sqltmp);
		if (rs.next()) {
			message = rs.getString("clientaddress") + " " + rs.getString("operatedate") + " " + rs.getString("operatetime");
		}

		return message;
	}%>
	</body>
</html>



