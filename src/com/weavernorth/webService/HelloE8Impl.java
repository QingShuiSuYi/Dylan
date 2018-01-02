package com.weavernorth.webService;

import weaver.interfaces.workflow.action.WorkflowToDoc;

public class HelloE8Impl implements HelloE8 {

	@Override
	public String Hello() {
		System.out.println("HelloE8");
		return "HelloE8";
	}

}
