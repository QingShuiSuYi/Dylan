package com.weavernorth.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import lbw.com.UpFtpServiceHttpBindingStub;


import org.apache.axis.AxisFault;





public class WebServiceTest {
	public static void Test(){
	        String wsdl = "http://192.168.1.104:8087//services/UpFtpService";
	        String response = "";
	        try {
	        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
	        UpFtpServiceHttpBindingStub stub;
			
				stub = new UpFtpServiceHttpBindingStub(
				    new java.net.URL(wsdl), service);

	        // 有些webservice需要登录，登陆后才能进行一些操作，这个需要设置如下两个参数： 
	        //1、 超时时间 
	        stub.setTimeout(1000 * 60 * 20); 
	        //2、 次数设置true，登录后才能保持登录状态，否则第二次调用ws方法时仍然会提示未登录。 
	        stub.setMaintainSession(true);
	        response = stub.upFtpFile("167", "763");
	        //String response = stub.urgeWorkOrderServiceSheet(requestStr); //调用ws提供的方法
			} catch (AxisFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("response >>> " + response);
		
	}
	public static void main(String[] args) throws RemoteException {
		Test();
//		UpFtpServicePortTypeProxy pp = new UpFtpServicePortTypeProxy();
//		String upFtpFile = pp.upFtpFile("167", "763");
//		System.out.println(upFtpFile);
	}
}
