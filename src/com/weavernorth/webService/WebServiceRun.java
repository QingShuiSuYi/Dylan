package com.weavernorth.webService;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Service;
import com.weavernorth.file.IOUtil;
import com.weavernorth.util.LogUtil;
//import _138._126._1._10.services.UMC.UMCSoapBindingStub;

public class WebServiceRun {
	
	public static String executeCommandsRun(){

		/*
	    String wsdl = "http://10.1.126.138/services/UMC";
	    Service service = new Service();
	    UMCSoapBindingStub stub = null;
		try {
			stub = new UMCSoapBindingStub(new java.net.URL(wsdl), service);
		} catch (AxisFault e) {
			LogUtil.doWriteLog("UMCSoapBindingStub初始化异常");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			LogUtil.doWriteLog("UMCSoapBindingStub初始化异常");
			e.printStackTrace();
		}
	    // 有些webservice需要登录，登陆后才能进行一些操作，这个需要设置如下两个参数： 
	    //1、 超时时间 
	    stub.setTimeout(1000 * 60 * 20); 
	    //2、 次数设置true，登录后才能保持登录状态，否则第二次调用ws方法时仍然会提示未登录。 
	    stub.setMaintainSession(true);
	    String readFileByLines = IOUtil.readFileByLines("/weavernorth/xml/executeCommandsRun.xml");
	    String response = "-1";
		try {
			response = stub.executeCommands(readFileByLines);
		} catch (RemoteException e) {
			LogUtil.doWriteLog("executeCommands方法调用异常");
			e.printStackTrace();
		}
		*/
		return "";
	}
	


}
