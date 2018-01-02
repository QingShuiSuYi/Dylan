package com.weavernorth.request;

import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.resource.ResourceComInfo;
import weaver.workflow.request.RequestInfo;

public class RequestUtil {
	
	/**
	 * 根据UID获取基本的User对象
	 * 注意：此方法只能初始化内部对象，会话内存储的信息部分无法获取
	 * @param uid
	 * @return
	 */
	public static User getUser(int uid){
		User user = new User();
		int userid = uid;
		user.setUid(userid);
		ResourceComInfo rci = new ResourceComInfo();
        user.setLoginid(rci.getLoginID("" + userid));
        user.setLogintype("1");
        user.setFirstname(rci.getFirstname("" + userid));
        user.setLastname(rci.getLastname("" + userid));
        user.setSex(rci.getSexs("" + userid));
        user.setLanguage(Util.getIntValue(rci.getSystemLanguage("" + userid), 7));
        user.setTelephone(rci.getTelephone("" + userid));
        user.setMobile(rci.getMobile("" + userid));
        user.setEmail(rci.getEmail("" + userid));
        user.setLocationid(rci.getLocationid("" + userid));
        user.setResourcetype(rci.getResourcetype("" + userid));
        user.setJobtitle(rci.getJobTitle("" + userid));
        user.setJoblevel(rci.getJoblevel("" + userid));
        user.setSeclevel(rci.getSeclevel("" + userid));
        user.setUserDepartment(Util.getIntValue(rci.getDepartmentID("" + userid)));
        user.setUserSubCompany1(Util.getIntValue(rci.getSubCompanyID("" + userid)));
        user.setManagerid(rci.getManagerID("" + userid));
        user.setAssistantid(rci.getAssistantID("" + userid));
        user.setAccount(rci.getAccount("" + userid));
		return user;
	}
	
	public static RequestInfo getRequestInfo(int requestid,int uid){
		RequestInfo rif = new RequestInfo(requestid, getUser(uid));
		return rif;
	}
	

	

}
