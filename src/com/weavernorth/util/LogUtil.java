package com.weavernorth.util;

import weaver.general.BaseBean;


public class LogUtil {
	
	public LogUtil(){}
	
	/**
	 * 测试用输出日志。将字符串输出到log日志文件中。
	 * 系统自动读取wnutil.properties文件中的设置，如果设置为输出日志时，才输出
	 * 代码调用如下：
	 * LogUtil.debugLog("需要输出的日志内容");
	 * 
	 * @param strLogMessage 需要输出的日志信息
	 */
	public static void debugLog(String strLogMessage){
		BaseBean bb=new BaseBean();
		//读取日志文件的信息
		String strIsLog=bb.getPropValue("wnutil", "islog");
		//如果输出标志为Y，则输出日志
		if("Y".equalsIgnoreCase(strIsLog)){
			bb.writeLog(strLogMessage);
		}
	}
	/**
	 * 将字符串输出到log日志文件中。
	 * 系统自动读取wnutil.properties文件中的设置，如果设置为输出日志时，才输出
	 * 代码调用如下：
	 * LogUtil.doWriteLog("需要输出的日志内容");
	 * 
	 * @param strLogMessage 需要输出的日志信息
	 */
	public static void doWriteLog(String strLogMessage){
		BaseBean bb=new BaseBean();
		//读取日志文件的信息
		String strIsLog=bb.getPropValue("wnutil", "islog");
		//如果输出标志为Y，则输出日志
		if("Y".equalsIgnoreCase(strIsLog)){
			bb.writeLog(strLogMessage);
		}
	}

	
	/**
	 * 发布版本固定输出日志。将字符串直接输出到log日志文件中。
	 * 代码调用如下：
	 * LogUtil.releaseLog("需要输出的日志内容");
	 * 
	 * @param strLogMessage 需要输出的日志信息
	 */
	public static void releaseLog(String strLogMessage){
		BaseBean bb=new BaseBean();
		bb.writeLog(strLogMessage);
	}

}
