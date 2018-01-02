package com.weavernorth.createFlow.webservice;

import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.workflow.webservices.WorkflowRequestTableField;

/**
 * 此枚举用于封装流程主表数据
 * @author Dylan
 *
 */
public enum PropVal2WorkFlowEnum {
	
	companyRecFlow{

		@Override
		public WorkflowRequestTableField[] getWrti(OfficialBean ob) {
			BaseBean bb = new BaseBean();
			WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[30]; // 字段信息
//			标题
			wrti[0] = new WorkflowRequestTableField();
			wrti[0].setFieldName(bb.getPropValue(companyRecFlow.toString(), "bt"));
			wrti[0].setFieldValue(ob.getTitle());
			wrti[0].setView(true);// 字段是否可见
			wrti[0].setEdit(true);// 字段是否可编辑
//			缓急
			wrti[1] = new WorkflowRequestTableField();
			wrti[1].setFieldName(bb.getPropValue(companyRecFlow.toString(), "hj"));
			wrti[1].setFieldValue(ob.getHuanji());
			wrti[1].setView(true);// 字段是否可见
			wrti[1].setEdit(true);// 字段是否可编辑
//			文号
			wrti[2] = new WorkflowRequestTableField();
			wrti[2].setFieldName(bb.getPropValue(companyRecFlow.toString(), "wh"));
			wrti[2].setFieldValue(ob.getWenhao());
			wrti[2].setView(true);// 字段是否可见
			wrti[2].setEdit(true);// 字段是否可编辑
//			文种
			if(!"".equals(Util.null2String(bb.getPropValue(companyRecFlow.toString(), "wz")))){
				wrti[3] = new WorkflowRequestTableField();
				wrti[3].setFieldName(bb.getPropValue(companyRecFlow.toString(), "wz"));
				wrti[3].setFieldValue(ob.getWenzhong());
				wrti[3].setView(true);// 字段是否可见
				wrti[3].setEdit(true);// 字段是否可编辑
			}
//			发文单位
			if(!"".equals(Util.null2String(bb.getPropValue(companyRecFlow.toString(), "fwdw")))){
				wrti[4] = new WorkflowRequestTableField();
				wrti[4].setFieldName(bb.getPropValue(companyRecFlow.toString(), "fwdw"));
				wrti[4].setFieldValue(ob.getFwdw());
				wrti[4].setView(true);// 字段是否可见
				wrti[4].setEdit(true);// 字段是否可编辑
			}
//			联系人
			if(!"".equals(Util.null2String(bb.getPropValue(companyRecFlow.toString(), "lxr")))){
				wrti[5] = new WorkflowRequestTableField();
				wrti[5].setFieldName(bb.getPropValue(companyRecFlow.toString(), "lxr"));
				wrti[5].setFieldValue(ob.getLxrname());
				wrti[5].setView(true);// 字段是否可见
				wrti[5].setEdit(true);// 字段是否可编辑
			}
//			联系电话
			if(!"".equals(Util.null2String(bb.getPropValue(companyRecFlow.toString(), "lxdh")))){
				wrti[6] = new WorkflowRequestTableField();
				wrti[6].setFieldName(bb.getPropValue(companyRecFlow.toString(), "lxdh"));
				wrti[6].setFieldValue(ob.getLxrphone());
				wrti[6].setView(true);// 字段是否可见
				wrti[6].setEdit(true);// 字段是否可编辑
			}
//			类别
			if(!"".equals(Util.null2String(bb.getPropValue(companyRecFlow.toString(), "lb")))){
				wrti[7] = new WorkflowRequestTableField();
				wrti[7].setFieldName(bb.getPropValue(companyRecFlow.toString(), "lb"));
				wrti[7].setFieldValue(ob.getFwtype());
				wrti[7].setView(true);// 字段是否可见
				wrti[7].setEdit(true);// 字段是否可编辑
			}
//			发送时间
			wrti[8] = new WorkflowRequestTableField();
			wrti[8].setFieldName(bb.getPropValue(companyRecFlow.toString(), "fssj"));
			wrti[8].setFieldValue(TimeUtil.getCurrentDateString());
			wrti[8].setView(true);// 字段是否可见
			wrti[8].setEdit(true);// 字段是否可编辑
//			正文
			wrti[9] = new WorkflowRequestTableField();
			wrti[9].setFieldName(bb.getPropValue(companyRecFlow.toString(), "zw"));
			wrti[9].setFieldValue(ob.getZwid());
			wrti[9].setView(true);// 字段是否可见
			wrti[9].setEdit(true);// 字段是否可编辑
//			附件
			wrti[10] = new WorkflowRequestTableField();
			wrti[10].setFieldName(bb.getPropValue(companyRecFlow.toString(), "fj"));
			wrti[10].setFieldValue(ob.getFjids());
			wrti[10].setView(true);// 字段是否可见
			wrti[10].setEdit(true);// 字段是否可编辑
//			来文单位
			wrti[11] = new WorkflowRequestTableField();
			wrti[11].setFieldName(bb.getPropValue(companyRecFlow.toString(), "lwdw"));
			wrti[11].setFieldValue("1");
			wrti[11].setView(true);// 字段是否可见
			wrti[11].setEdit(true);// 字段是否可编辑
//			成文日期
			wrti[12] = new WorkflowRequestTableField();
			wrti[12].setFieldName(bb.getPropValue(companyRecFlow.toString(), "swrq"));
			wrti[12].setFieldValue(ob.getQianfariqi());
			wrti[12].setView(true);// 字段是否可见
			wrti[12].setEdit(true);// 字段是否可编辑
//			页数
			wrti[13] = new WorkflowRequestTableField();
			wrti[13].setFieldName(bb.getPropValue(companyRecFlow.toString(), "ys"));
			wrti[13].setFieldValue(ob.getYenum());
			wrti[13].setView(true);// 字段是否可见
			wrti[13].setEdit(true);// 字段是否可编辑

			return wrti;
		}

	},disciplineRecFlow{

		@Override
		public WorkflowRequestTableField[] getWrti(OfficialBean ob) {
			BaseBean bb = new BaseBean();
			WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[30]; // 字段信息
//			标题
			wrti[0] = new WorkflowRequestTableField();
			wrti[0].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "bt"));
			wrti[0].setFieldValue(ob.getTitle());
			wrti[0].setView(true);// 字段是否可见
			wrti[0].setEdit(true);// 字段是否可编辑
//			缓急
			wrti[1] = new WorkflowRequestTableField();
			wrti[1].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "hj"));
			wrti[1].setFieldValue(ob.getHuanji());
			wrti[1].setView(true);// 字段是否可见
			wrti[1].setEdit(true);// 字段是否可编辑
//			文号
			wrti[2] = new WorkflowRequestTableField();
			wrti[2].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "wh"));
			wrti[2].setFieldValue(ob.getWenhao());
			wrti[2].setView(true);// 字段是否可见
			wrti[2].setEdit(true);// 字段是否可编辑
//			文种
			if(!"".equals(Util.null2String(bb.getPropValue(disciplineRecFlow.toString(), "wz")))){
				wrti[3] = new WorkflowRequestTableField();
				wrti[3].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "wz"));
				wrti[3].setFieldValue(ob.getWenzhong());
				wrti[3].setView(true);// 字段是否可见
				wrti[3].setEdit(true);// 字段是否可编辑
			}
//			发文单位
			if(!"".equals(Util.null2String(bb.getPropValue(disciplineRecFlow.toString(), "fwdw")))){
				wrti[4] = new WorkflowRequestTableField();
				wrti[4].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "fwdw"));
				wrti[4].setFieldValue(ob.getFwdw());
				wrti[4].setView(true);// 字段是否可见
				wrti[4].setEdit(true);// 字段是否可编辑
			}
//			联系人
			if(!"".equals(Util.null2String(bb.getPropValue(disciplineRecFlow.toString(), "lxr")))){
				wrti[5] = new WorkflowRequestTableField();
				wrti[5].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "lxr"));
				wrti[5].setFieldValue(ob.getLxrname());
				wrti[5].setView(true);// 字段是否可见
				wrti[5].setEdit(true);// 字段是否可编辑
			}
//			联系电话
			if(!"".equals(Util.null2String(bb.getPropValue(disciplineRecFlow.toString(), "lxdh")))){
				wrti[6] = new WorkflowRequestTableField();
				wrti[6].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "lxdh"));
				wrti[6].setFieldValue(ob.getLxrphone());
				wrti[6].setView(true);// 字段是否可见
				wrti[6].setEdit(true);// 字段是否可编辑
			}
//			类别
			if(!"".equals(Util.null2String(bb.getPropValue(disciplineRecFlow.toString(), "lb")))){
				wrti[7] = new WorkflowRequestTableField();
				wrti[7].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "lb"));
				wrti[7].setFieldValue(ob.getFwtype());
				wrti[7].setView(true);// 字段是否可见
				wrti[7].setEdit(true);// 字段是否可编辑
			}
//			发送时间
			wrti[8] = new WorkflowRequestTableField();
			wrti[8].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "fssj"));
			wrti[8].setFieldValue(TimeUtil.getCurrentDateString());
			wrti[8].setView(true);// 字段是否可见
			wrti[8].setEdit(true);// 字段是否可编辑
//			正文
			wrti[9] = new WorkflowRequestTableField();
			wrti[9].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "zw"));
			wrti[9].setFieldValue(ob.getZwid());
			wrti[9].setView(true);// 字段是否可见
			wrti[9].setEdit(true);// 字段是否可编辑
//			附件
			wrti[10] = new WorkflowRequestTableField();
			wrti[10].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "fj"));
			wrti[10].setFieldValue(ob.getFjids());
			wrti[10].setView(true);// 字段是否可见
			wrti[10].setEdit(true);// 字段是否可编辑
//			文件接收者
			wrti[11] = new WorkflowRequestTableField();
			wrti[11].setFieldName("wjjsz");
			wrti[11].setFieldValue(bb.getPropValue(disciplineRecFlow.toString(), "wjjsz"));
			wrti[11].setView(true);// 字段是否可见
			wrti[11].setEdit(true);// 字段是否可编辑
//			成文日期
			wrti[12] = new WorkflowRequestTableField();
			wrti[12].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "swrq"));
			wrti[12].setFieldValue(ob.getQianfariqi());
			wrti[12].setView(true);// 字段是否可见
			wrti[12].setEdit(true);// 字段是否可编辑
//			页数
			wrti[13] = new WorkflowRequestTableField();
			wrti[13].setFieldName(bb.getPropValue(disciplineRecFlow.toString(), "ys"));
			wrti[13].setFieldValue(ob.getYenum());
			wrti[13].setView(true);// 字段是否可见
			wrti[13].setEdit(true);// 字段是否可编辑
			return wrti;
		}
		
	},partyRecFlow{

		@Override
		public WorkflowRequestTableField[] getWrti(OfficialBean ob) {
			BaseBean bb = new BaseBean();
			WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[30]; // 字段信息
//			标题
			wrti[0] = new WorkflowRequestTableField();
			wrti[0].setFieldName(bb.getPropValue(partyRecFlow.toString(), "bt"));
			wrti[0].setFieldValue(ob.getTitle());
			wrti[0].setView(true);// 字段是否可见
			wrti[0].setEdit(true);// 字段是否可编辑
//			缓急
			wrti[1] = new WorkflowRequestTableField();
			wrti[1].setFieldName(bb.getPropValue(partyRecFlow.toString(), "hj"));
			wrti[1].setFieldValue(ob.getHuanji());
			wrti[1].setView(true);// 字段是否可见
			wrti[1].setEdit(true);// 字段是否可编辑
//			文号
			wrti[2] = new WorkflowRequestTableField();
			wrti[2].setFieldName(bb.getPropValue(partyRecFlow.toString(), "wh"));
			wrti[2].setFieldValue(ob.getWenhao());
			wrti[2].setView(true);// 字段是否可见
			wrti[2].setEdit(true);// 字段是否可编辑
//			文种
			if(!"".equals(Util.null2String(bb.getPropValue(partyRecFlow.toString(), "wz")))){
				wrti[3] = new WorkflowRequestTableField();
				wrti[3].setFieldName(bb.getPropValue(partyRecFlow.toString(), "wz"));
				wrti[3].setFieldValue(ob.getWenzhong());
				wrti[3].setView(true);// 字段是否可见
				wrti[3].setEdit(true);// 字段是否可编辑
			}
//			发文单位
			if(!"".equals(Util.null2String(bb.getPropValue(partyRecFlow.toString(), "fwdw")))){
				wrti[4] = new WorkflowRequestTableField();
				wrti[4].setFieldName(bb.getPropValue(partyRecFlow.toString(), "fwdw"));
				wrti[4].setFieldValue(ob.getFwdw());
				wrti[4].setView(true);// 字段是否可见
				wrti[4].setEdit(true);// 字段是否可编辑
			}
//			联系人
			if(!"".equals(Util.null2String(bb.getPropValue(partyRecFlow.toString(), "lxr")))){
				wrti[5] = new WorkflowRequestTableField();
				wrti[5].setFieldName(bb.getPropValue(partyRecFlow.toString(), "lxr"));
				wrti[5].setFieldValue(ob.getLxrname());
				wrti[5].setView(true);// 字段是否可见
				wrti[5].setEdit(true);// 字段是否可编辑
			}
//			联系电话
			if(!"".equals(Util.null2String(bb.getPropValue(partyRecFlow.toString(), "lxdh")))){
				wrti[6] = new WorkflowRequestTableField();
				wrti[6].setFieldName(bb.getPropValue(partyRecFlow.toString(), "lxdh"));
				wrti[6].setFieldValue(ob.getLxrphone());
				wrti[6].setView(true);// 字段是否可见
				wrti[6].setEdit(true);// 字段是否可编辑
			}
//			类别
			if(!"".equals(Util.null2String(bb.getPropValue(partyRecFlow.toString(), "lb")))){
				wrti[7] = new WorkflowRequestTableField();
				wrti[7].setFieldName(bb.getPropValue(partyRecFlow.toString(), "lb"));
				wrti[7].setFieldValue(ob.getFwtype());
				wrti[7].setView(true);// 字段是否可见
				wrti[7].setEdit(true);// 字段是否可编辑
			}
//			发送时间
			wrti[8] = new WorkflowRequestTableField();
			wrti[8].setFieldName(bb.getPropValue(partyRecFlow.toString(), "fssj"));
			wrti[8].setFieldValue(TimeUtil.getCurrentDateString());
			wrti[8].setView(true);// 字段是否可见
			wrti[8].setEdit(true);// 字段是否可编辑
//			正文
			wrti[9] = new WorkflowRequestTableField();
			wrti[9].setFieldName(bb.getPropValue(partyRecFlow.toString(), "zw"));
			wrti[9].setFieldValue(ob.getZwid());
			wrti[9].setView(true);// 字段是否可见
			wrti[9].setEdit(true);// 字段是否可编辑
//			附件
			wrti[10] = new WorkflowRequestTableField();
			wrti[10].setFieldName(bb.getPropValue(partyRecFlow.toString(), "fj"));
			wrti[10].setFieldValue(ob.getFjids());
			wrti[10].setView(true);// 字段是否可见
			wrti[10].setEdit(true);// 字段是否可编辑
//			文件接收者		
			wrti[11] = new WorkflowRequestTableField();
			wrti[11].setFieldName("wjjsz");
			wrti[11].setFieldValue(bb.getPropValue(partyRecFlow.toString(), "wjjsz"));
			wrti[11].setView(true);// 字段是否可见
			wrti[11].setEdit(true);// 字段是否可编辑
//			成文日期
			wrti[12] = new WorkflowRequestTableField();
			wrti[12].setFieldName(bb.getPropValue(partyRecFlow.toString(), "swrq"));
			wrti[12].setFieldValue(ob.getQianfariqi());
			wrti[12].setView(true);// 字段是否可见
			wrti[12].setEdit(true);// 字段是否可编辑
//			页数
			wrti[13] = new WorkflowRequestTableField();
			wrti[13].setFieldName(bb.getPropValue(partyRecFlow.toString(), "ys"));
			wrti[13].setFieldValue(ob.getYenum());
			wrti[13].setView(true);// 字段是否可见
			wrti[13].setEdit(true);// 字段是否可编辑
			return wrti;
		}
		
	};
	public abstract WorkflowRequestTableField[] getWrti(OfficialBean ob);
	
	
	
}
