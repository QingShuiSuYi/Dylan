<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ include file="/systeminfo/init_wev8.jsp" %>

<%

%>

<html>
<head>
<title><%=SystemEnv.getHtmlLabelName(16641,user.getLanguage())%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="/wui/common/jquery/plugin/jquery.overlabel_wev8.js"></script>

<link href="/homepage/css/homepage.css" type="text/css" rel="stylesheet">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
<td><span id="showSpan">加载中……</span></td>
        </tr>
      </table>	  
</body>
<div id="content"></div>
</html>


<script type="text/javascript">


	function queryxucunhetong(){

		var callback="result2";
		var size="15";
		var query="CCF4FE5565BA5AF6EEA169C20B701443103331303038393839133C9D6T36471";

		jQuery.ajax({
			url: 'http://10.196.1.65/cms/CMPendServlet',
			type: 'get',
			dataType: 'jsonp',
			jsonp: 'jsoncallback',
			data: {callback:callback,size:size,query:query},
			success: function(json){
				
			} 
		});
	}

	function result2(data){
		if(console){
			console.log(data);
		}
		if(data.success){
			alert("data.count="+data.count);
			var showSpanHtml="<table width='100%' class='elementdatatable' > <tbody>";
			for(var m = 0;m < data.data.length ; m++){
				showSpanHtml+="<tr>";
                showSpanHtml +="<td width='8'><img name='esymbol' src='/page/resource/userfile/image/ecology8/pointer_wev8.png'></td>";
				for(var n = 0;n < data.data[m].items.length; n++){
					showSpanHtml+="<td class='reqdetail' width='*' ><a class='ellipsis' href='"+data.data[m].items[n].url+"' target='_self'>";
					showSpanHtml+="<font class='font'><font class='font'>";
					showSpanHtml+="<span class='reqname'>"+data.data[m].items[n].title+"</span></font></font></a></td>";
					showSpanHtml+="<td width='130'><font class='font'>"+data.data[m].items[n].date+"</font></td>";
					showSpanHtml+="<td width='100'><font class='font'>"+data.data[m].items[n].type+"</font></td>";


				}
				showSpanHtml+="</tr>";
			}
			showSpanHtml+="<tbody></table>";
			jQuery("#showSpan").html(showSpanHtml);
		}
	}

jQuery(document).ready(function(){

	queryxucunhetong();

});
</script>

