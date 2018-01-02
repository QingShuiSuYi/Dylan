<%@ page import="weaver.general.*"%>
<%@ page import="weaver.conn.*"%>
<%@ page import="java.util.*,java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<HTML>
	<HEAD>
	</head>

	<BODY>
		<%
			String username = Util.null2String(request.getParameter("username"));
			String sessionkey = Util.null2String(request.getParameter("sessionkey"));
			String bindid = Util.null2String(request.getParameter("bindid"));
            boolean b=username.equals("");
			//调用统一认证接口
			if (b) {
				out.println("<script>");
				out.println("alert('用户名不能为空!');");
				out.println("window.location.href='/';");
				out.println("</script>");
			}else{
				//登录成功，进入oa
				session.setAttribute("email",username);
				response.sendRedirect("LoginJump.jsp");
			}
		%>

	</body>
</html>