package com.weavernorth.emobile;

import java.util.HashMap;
import java.util.Map;

import weaver.mobile.plugin.ecology.service.PushNotificationService;
/**
 * ecology向emobile发送消息提醒
 * @author Dylan
 *
 */
public class MessagePush {

	public static void push(){
		String reviceIds ="ywy,ny";
		PushNotificationService service = new PushNotificationService();
		Map<String, String> para = new HashMap<String, String>();
		para.put("module", "-2");
		para.put("messagetypeid", "1");
		para.put("url", "http://www.baidu.com?a=sss&b=EE");
		service.push(reviceIds, "ecology消息推送提醒", 1, para);
	}
}
