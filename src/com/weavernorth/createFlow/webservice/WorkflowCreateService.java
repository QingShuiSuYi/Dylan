package com.weavernorth.createFlow.webservice;


import weaver.general.BaseBean;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowServiceImpl;

/**
 * 触发生成流程
 * @author Dylan
 *
 */
public class WorkflowCreateService {
	/**
	 * 用于创建收文流程
	 * @param ob	表单数据对象
	 * @param workFlowId	流程ID
	 * @return
	 */
	public static String create(OfficialBean ob,String workFlowId){
		//返回值
		String strReturn = "";
		//主表数据
		WorkflowRequestTableField[] wrti = null;
		//创建人
		String strCreatorId = "";
		BaseBean bb = new BaseBean();
		
		/**
		 * 公司文件式（信函式）公文收文流程
		 * workFlowId	92
		 */
		if("92".equals(workFlowId)){
			wrti = PropVal2WorkFlowEnum.companyRecFlow.getWrti(ob);
			strCreatorId = bb.getPropValue("companyRecFlow", "swr");
		}
		/**
		 * 党委文件式（信函式）公文收文流程
		 * workFlowId	50
		 */
		if("50".equals(workFlowId)){
			wrti = PropVal2WorkFlowEnum.partyRecFlow.getWrti(ob);
			strCreatorId = bb.getPropValue("partyRecFlow", "swr");
		}
		/**
		 * 纪委文件式（信函式）公文收文流程
		 * workFlowId	144
		 */
		if("144".equals(workFlowId)){
			
			wrti = PropVal2WorkFlowEnum.disciplineRecFlow.getWrti(ob);
			strCreatorId = bb.getPropValue("disciplineRecFlow", "swr");
		}
		//如果主表数据不为空，在执行创建
		if(wrti != null){
			WorkflowServiceImpl workFlowService = new WorkflowServiceImpl();
			// 主表start
			WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];// 主字段只有一行数据
			wrtri[0] = new WorkflowRequestTableRecord();// 将第一天记录初始化
			wrtri[0].setWorkflowRequestTableFields(wrti);// 将字段加入到记录中
			WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();// 初始化主表信息
			wmi.setRequestRecords(wrtri);// 将记录加入到主表中
			// 主表end
			WorkflowBaseInfo wbi = new WorkflowBaseInfo();
			
			wbi.setWorkflowId(workFlowId);// workflowid 
	
			WorkflowRequestInfo wri = new WorkflowRequestInfo();// 流程基本信息
			wri.setCreatorId(strCreatorId);// 创建人id
			wri.setRequestLevel("0");// 0 正常，1重要，2紧急
			wri.setRequestName(ob.getTitle());// 流程标题
			wri.setRemark("  ");
			wri.setWorkflowMainTableInfo(wmi);// 添加主字段数据
			wri.setWorkflowBaseInfo(wbi);
			// 执行创建流程接口
			strReturn = workFlowService.doCreateWorkflowRequest(wri, Integer.parseInt(strCreatorId));
		}
		
		return strReturn;
	}

}
