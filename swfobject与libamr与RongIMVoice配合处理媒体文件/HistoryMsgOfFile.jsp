
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ page import="weaver.general.Util" %>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONException"%>
<%@ page import="com.weavernorth.util.LogUtil"%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page"/>
<%@ taglib uri="/browserTag" prefix="brow"%>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<%
//判断是否有权限查询这个表
RecordSet.execute("select PROPERVALUE from WN_PROPERVTIES where PROPERNAME='SELECT_ALL_MSG_USER_ID' AND propervalue='"+user.getUID()+"'");
if(RecordSet.next()){
	if("1".equals(RecordSet.getString("PROPERVALUE"))){
		response.sendRedirect("/notice/noright.jsp");
		return;
	}
}else{
	response.sendRedirect("/notice/noright.jsp");
	return;
}
%>
<HTML><HEAD>
<LINK REL=stylesheet type=text/css HREF=/css/Weaver_wev8.css></HEAD>
<SCRIPT language="javascript" src="/js/weaver_wev8.js"></script>
<script language=javascript src="/js/ecology8/docs/docExt_wev8.js"></script>
<script language=javascript src="/js/ecology8/docs/docSearchInit_wev8.js"></script>

<%
int userId = user.getUID();
String flowTitle ="";

String sqlwhere = " where 1 = 1 ";
//查询时间
String strCxsj = Util.null2String(request.getParameter("cxsj"));
//开始日期
String startdate = Util.null2String(request.getParameter("startdate"));
//结束日期
String enddate = Util.null2String(request.getParameter("enddate"));
//发送人
String strFsr = Util.null2String(request.getParameter("fsr"));
//文件类型
String strWjlx = Util.null2String(request.getParameter("wjlx"));
//附件名称
String strXxnr = Util.null2String(request.getParameter("xxnr"));

/*
JSONObject jsonPra = new JSONObject();
jsonPra.put("strCxsj", strCxsj);
jsonPra.put("startdate", startdate);
jsonPra.put("enddate", enddate);
jsonPra.put("strXxnr", strXxnr);
jsonPra.put("strHhcx", strHhcx);
String strJsonPra = jsonPra.toString();
*/
if(!strCxsj.equals("")){
	switch (Integer.parseInt(strCxsj)){
		case  0 : 
			sqlwhere += " and SUBSTR(dateTime,0,10) between '" + TimeUtil.dateAdd(TimeUtil.getCurrentDateString(), -30) + "' and '" + TimeUtil.getCurrentDateString() + "'";  
		break;
		case  1 : 
			sqlwhere += " and SUBSTR(dateTime,0,10) between '" + TimeUtil.dateAdd(TimeUtil.getCurrentDateString(), -7) + "' and '"+TimeUtil.getCurrentDateString()+"'"; 
		break;
		case  2 : 
			sqlwhere += " and SUBSTR(dateTime,0,10) = '" + TimeUtil.getCurrentDateString() + "'"; 
		break;
		case  3 : 
			sqlwhere += " and SUBSTR(dateTime,0,10) between '" + startdate + "' and '" + enddate + "'"; 
		break;
		default: 
			sqlwhere += " and SUBSTR(dateTime,0,10) between '" + TimeUtil.dateAdd(TimeUtil.getCurrentDateString(), -30) + "' and '" + TimeUtil.getCurrentDateString() + "'";  
		break;
	}
}
if(!strWjlx.equals("")){
	switch (Integer.parseInt(strWjlx)){
		case  0 : 
			sqlwhere += " and  classname in ('RC:ImgMsg','FW:attachmentMsg','RC:VcMsg') ";  
		break;
		case  1 : 
			sqlwhere += " and  classname in ('FW:attachmentMsg') "; 
		break;
		case  2 : 
			sqlwhere += " and  classname in ('RC:ImgMsg') "; 
		break;
		case  3 : 
			sqlwhere += " and  classname in ('RC:VcMsg') "; 
		break;
		default: 
			sqlwhere += " and  classname in ('RC:ImgMsg','FW:attachmentMsg','RC:VcMsg') ";  
		break;
	}
}

if(!strFsr.equals("")){
	sqlwhere += " and fromuserid = '" + strFsr + "'";
}

if(!strXxnr.equals("")){
	sqlwhere += "and msgcontent like '%" + strXxnr + "%'";
}

sqlwhere += " and msgid is not null and type != 'null'  ";


//是否第一次进入
String strIsFirst=Util.null2String(request.getParameter("isFirst")) ;
if(strIsFirst.equals("1")){
	sqlwhere = " where 1=2 ";
}

int pagenum=Util.getIntValue(request.getParameter("pagenum"),1);
int	perpage=20;
%>

<%
String imagefilename = "/images/hdMaintenance_wev8.gif";
String titlename = "";
String needfav ="1";
String needhelp ="";
%>
<BODY>
<%@ include file="/systeminfo/TopTitle_wev8.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
<%
RCMenu += "{"+SystemEnv.getHtmlLabelName(197,user.getLanguage())+",javascript:check(),_self} " ;//搜索
RCMenuHeight += RCMenuHeightStep ;
%>
<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>

<table id="topTitle" cellpadding="0" cellspacing="0" >
			<tr>
				<td class="rightSearchSpan">
					<input type=button class="e8_btn_top" onclick="check();" value="<%="搜索"%>"></input>					
					<span title="<%=SystemEnv.getHtmlLabelName(23036,user.getLanguage())%>" class="cornerMenu"></span><!-- 菜单 -->
				</td>
			</tr>
		</table>
	<form id="frmmain" NAME="frmmain" STYLE="margin-bottom:0" action="/weavernorth/msg/HistoryMsgOfFile.jsp"  method=post> 
		<iframe id="selectChange" frameborder=0 scrolling=no src=""  style="display:none"></iframe>
		<input type="hidden" name="flowTitle2" value="<%=flowTitle%>">
		<wea:layout type="4col">
			<wea:group context="" attributes="{groupDisplay:none}">
				<wea:item>
					<%="查询时间"%>
				</wea:item>
				<wea:item>
					<select name="cxsj" id="cxsj">
					<option value="2" <% if (strCxsj.equals("2")) {  %> selected <%}%> >今天</option>
					<option value="0" <% if (strCxsj.equals("0")) { %> selected <%}%> >最近1个月</option>
					<option value="1" <% if (strCxsj.equals("1")) { %> selected <%}%> >最近7天</option>
					<option value="3" <% if (strCxsj.equals("3")) {  %> selected <%}%> >自定义</option>
				</wea:item>
				<wea:item>
					<div id = "kssj" name = "kssj" style="display:none;">
					<%="开始时间-结束时间"%>
					</div>
				</wea:item>
				<wea:item>
					<div id = "kssj1" name = "kssj1" style="display:none;">
					<input id="startdate" name="startdate" value="<%=startdate%>" type="hidden" _callback="changestartdate" class=wuiDate />
					<wea:required id="startdatespan0" required="true"></wea:required>-
					<input id="enddate" name="enddate" value="<%=enddate%>" type="hidden" _callback="changeenddate" class=wuiDate />
					<wea:required id="enddatespan0" required="true"></wea:required> 
					</div>
				</wea:item>
				<wea:item>
					<%="发送人"%>
				</wea:item>
				<wea:item>
					<brow:browser viewType="0" name="fsr" id="fsr" browserValue='<%=""+strFsr%>' onPropertyChange="changefsr()" browserOnClick="" browserUrl="/systeminfo/BrowserMain.jsp?url=/hrm/resource/ResourceBrowser.jsp" hasInput="true"  width="300px" isSingle="true" hasBrowser ='true' isMustInput='1' completeUrl="/data.jsp"  browserSpanValue='<%=Util.toScreen(ResourceComInfo.getResourcename(strFsr),user.getLanguage())%>'>
					</brow:browser>
					<wea:required id="fsrspan0" required="true"></wea:required> 
				</wea:item>	
				<wea:item>
					<%="文件类型"%>
				</wea:item>
				<wea:item>
					<select name="wjlx" id="wjlx">
					<option value="0" <% if (strWjlx.equals("0")) { %> selected <%}%> >全部</option>
					<option value="1" <% if  (strWjlx.equals("1")) {  %> selected <%}%> >附件</option>
					<option value="2" <% if  (strWjlx.equals("2")) {  %> selected <%}%> >图片</option>
					<option value="3" <% if  (strWjlx.equals("3")) {  %> selected <%}%> >语音</option>
					</select>
				</wea:item>
				<wea:item>
					<%="附件名称"%>
				</wea:item>
				<wea:item>
					<INPUT type="text" class=Inputstyle name="xxnr" value="<%=strXxnr%>">
				</wea:item>


			</wea:group>
			<wea:group context="">
				<wea:item type="toolbar">
				</wea:item>
			</wea:group>
		</wea:layout>
	</form>

		        	<%
		        	String backfields = " type,id,fromuserid,targetid,datetime,extra,msgcontent,classname,imageuri ";
		        	String fromSql =  " from historymsg ";
		        	String sqlWhere = sqlwhere;


		            
		            String orderby = "datetime" ;
		            
					//out.print("select "+ backfields +" "+fromSql+" "+sqlWhere);
					LogUtil.doWriteLog("附件查询主报表SQL："+"select "+ backfields +" "+fromSql+" "+sqlWhere);
		            String tableString = "";
		            tableString =" <table instanceid=\"CarTable\" tabletype=\"none\" pagesize=\""+perpage+"\" >"+
		                                     "		<sql backfields=\""+backfields+"\" sqlform=\""+fromSql+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlWhere)+"\"  sqlorderby=\""+orderby+"\"  sqlprimarykey=\"type\" sqlsortway=\"desc\" sqlisdistinct=\"false\"/>"+
		                                     "		<head>"+
		                                     "			 <col width=\"0%\" hide=\"true\"  text=\"\" column=\"id\"/>"+
		                                     "			 <col width=\"0%\" hide=\"true\"  text=\"\" column=\"fromuserid\"/>"+
		                                     "			 <col width=\"0%\" hide=\"true\"  text=\"\" column=\"imageuri\"/>"+
											 "           <col width=\"10%\"  text=\"文件名称\" column=\"msgcontent\" transmethod=\"com.weavernorth.transmethod.EMessageTransMethod.getContent\"   otherpara=\"column:classname+column:fromuserid+column:imageuri+column:extra\" otherpara2=\"column:imageuri\" />"+ 
											 "           <col width=\"10%\"  text=\"文件大小\" column=\"extra\" orderkey=\"extra\" transmethod=\"com.weavernorth.transmethod.PageUtil.getSize\" otherpara=\"column:imageuri+column:classname\"/>"+    
											 "           <col width=\"10%\"  text=\"文件类型\" column=\"classname\" transmethod=\"com.weavernorth.transmethod.EMessageTransMethod.getMsgType\" otherpara=\"column:extra\"  />"+   
											 "           <col width=\"10%\"  text=\"接收对象\" column=\"targetid\" orderkey=\"targetid\" transmethod=\"com.weavernorth.transmethod.EMessageTransMethod.getName\" />"+  
											 "           <col width=\"10%\"  text=\"最后对话时间\" column=\"datetime\" orderkey=\"datetime\" />"+   
		                                     "		</head>"+
		                            		 "      <operates>"+//相关操作                                    
		                            		 "      <popedom column=\"type\" transmethod=\"com.weavernorth.transmethod.PageUtil.getCanOperation\"></popedom> "+//用于控制操作菜单是否可用                                    											
		                            		 "      <operate href=\"javascript:delMsg();\" otherpara=\"column:type\"  text=\""+"删除"+"\"  target=\"_self\" index=\"1\" />"+                                    
		                            		 "      </operates>"+
		                                     "</table>";
		         %>
			
		         	<wea:SplitPageTag  tableString='<%=tableString%>'  mode="run" />
		         


<script language=vbs>
sub getStartDate()
	returndate = window.showModalDialog("/systeminfo/Calendar.jsp",,"dialogHeight:320px;dialogwidth:275px")
	document.all("startdatespan").innerHtml= returndate
	document.all("startdate").value=returndate
end sub

sub getEndDate()
	returndate = window.showModalDialog("/systeminfo/Calendar.jsp",,"dialogHeight:320px;dialogwidth:275px")
	document.all("enddatespan").innerHtml= returndate
	document.all("enddate").value=returndate
end sub
</script>

<!--[if !IE]><!--> 
<script src="./res/Libamr-2.2.5.min.js"></script>
<!--<![endif]-->
<!-- IE9下 swfobject 报错问题解决 -->
<script type="text/javascript" src="./res/RongIMVoice-2.2.6.js"></script>



<script language=javascript>
var voice;

var RongIMVoice = RongIMLib.RongIMVoice;
RongIMVoice.init();

function play(){
// 音频持续大概时间(秒)
var duration = voice.length/1024;
RongIMVoice.preLoaded(voice);
RongIMVoice.play(voice,duration);
}

function touchPlay(){
window.removeEventListener('touchstart', touchPlay, false);
play();
}

var isiOS = (/i(Phone|P(o|a)d)/.test(navigator.userAgent));

if(isiOS){
window.addEventListener('touchstart', touchPlay, false);
}




function bf(msg){
	voice = msg;
	play();

}
//更多操作触发函数
function delMsg(id){
	if(confirm("确定要删除吗？")){
		var msgurl = window.location.href;
		window.location.href="SendDelMsg.jsp?Msgids="+id+"&msgurl="+msgurl.replace(/&/g,"*");
	}
 }

function check(){
	var flag = "1";
	
	if($('#cxsj').val() == 3){
		if(!$('#startdate').val() || !$('#enddate').val()){
			flag = "0";
			alert("时间段不能为空！");
		}else if ($('#startdate').val() > $('#enddate').val()){
			flag = "0";
			alert("开始时间不能大于结束时间！");
		}else if ($('#enddate').val() - $('#startdate').val() > 365){
			flag = "0";
			alert("建议时间跨度为一年以内！");
		}
	}
	
	if(!$("#fsr").val()){
		flag = "0";
		alert("发送人不能为空！");
	}
	

	if(flag == "1"){
		document.frmmain.action="/weavernorth/msg/HistoryMsgOfFile.jsp";
		document.frmmain.submit();
	}
}


function doSearch(){
	//document.frmmain.action="CarInfoMaintenance.jsp";
	document.frmmain.action="";
	document.frmmain.submit();
}
function doDel(){
	if(isdel()){
		document.frmmain.submit();
	}
}
function onBtnSearchClick(){
	document.frmmain.flowTitle2.value = jQuery("#flowTitle").val();
	document.frmmain.carNo.value = jQuery("#flowTitle").val();
	jQuery("#frmmain").submit();
}

function changefsr(){
	if($("#fsr").val()){
		$("#fsrspan0").hide();
	}else{
		$("#fsrspan0").show();
	}
}

function changestartdate(){
	if(!$("#startdate").val()){
		$("#startdatespan0").show();
	}else{
		$("#startdatespan0").hide();	
	}
}

function changeenddate(){
	if(!$("#enddate").val()){
		$("#enddatespan0").show();	
	}else{
		$("#enddatespan0").hide();
	}	
}

var diag_vote;
function closeDlgARfsh(){
	diag_vote.close();
	doSearch();
}



	

	  //document加载完成后执行
   	jQuery(document).ready(function(){
   				
					if($('#cxsj').val() == 3){
   						jQuery("#kssj").css("display","block");
   						jQuery("#kssj1").css("display","block");
   					}else{
   						jQuery("#kssj").css("display","none");
   						jQuery("#kssj1").css("display","none");
   					}
				//隐藏和显示开始时间和结束时间
   				$('#cxsj').change(function(){
   					if($('#cxsj').val() == 3){
   						jQuery("#kssj").css("display","block");
   						jQuery("#kssj1").css("display","block");
   					}else{
   						jQuery("#kssj").css("display","none");
   						jQuery("#kssj1").css("display","none");
   					}
   				});
				
 					if($("#fsr").val()){
 						$("#fsrspan0").hide();
 					}else{
 						$("#fsrspan0").show();
 					}
 					if($("#startdate").val()){
 						$("#startdatespan0").hide();
 					}else{
 						$("#startdatespan0").show();
 					}
 					if($("#enddate").val()){
 						$("#enddatespan0").hide();
 					}else{
 						$("#enddatespan0").show();
 					}
				

					
				 
				   
				/*
				if($('#hhcx').is(':checked')){
					alert('选中');
				}else{
					alert('不选中');
				}
				*/
				



   		
   	});
</script>
</body>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" src="/js/selectDateTime_wev8.js"></script>
</html>
