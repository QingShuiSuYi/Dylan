package com.weavernorth.createFlow.webservice;

import java.io.Serializable;

public class OfficialBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//记录唯一标识
	private String instanceid = "";
	//标题
	private String title = "";
	//缓急
	private String huanji = "";
	//文号
	private String wenhao = "";	
	//文种
	private String wenzhong = "";
	//发文单位
	private String fwdw = "";
	//联系人
	private String lxrname = "";
	//联系电话
	private String lxrphone = "";
	//类别
	private String fwtype = "";
	//发送时间
	private String senttime = "";
	//正文名称
	private String zwname = "";
	//正文类型后缀名
	private String zwtype = "";
	//附件内容(BASE64字符串)
	private String zwcontent = "";
	//正文id
	private String zwid = "";
	//附件id
	private String fjids = "";
	//页数
	private String yenum = "";
	//成文日期
	private String qianfariqi = "";
	
	
	
	public OfficialBean(String instanceid, String title, String huanji,
			String wenhao, String wenzhong, String fwdw, String lxrname,
			String lxrphone, String fwtype, String senttime, String zwname,
			String zwtype, String zwcontent, String zwid, String fjids,
			String yenum, String qianfariqi) {
		//super();
		this.instanceid = instanceid;
		this.title = title;
		this.huanji = huanji;
		this.wenhao = wenhao;
		this.wenzhong = wenzhong;
		this.fwdw = fwdw;
		this.lxrname = lxrname;
		this.lxrphone = lxrphone;
		this.fwtype = fwtype;
		this.senttime = senttime;
		this.zwname = zwname;
		this.zwtype = zwtype;
		this.zwcontent = zwcontent;
		this.zwid = zwid;
		this.fjids = fjids;
		this.yenum = yenum;
		this.qianfariqi = qianfariqi;
	}
	public OfficialBean(){
		
	}
	public String getYenum() {
		return yenum;
	}
	@Override
	public String toString() {
		return "OfficialBean [instanceid=" + instanceid + ", title=" + title
				+ ", huanji=" + huanji + ", wenhao=" + wenhao + ", wenzhong="
				+ wenzhong + ", fwdw=" + fwdw + ", lxrname=" + lxrname
				+ ", lxrphone=" + lxrphone + ", fwtype=" + fwtype
				+ ", senttime=" + senttime + ", zwname=" + zwname + ", zwtype="
				+ zwtype + ", zwcontent=" + zwcontent + ", zwid=" + zwid
				+ ", fjids=" + fjids + ", yenum=" + yenum + ", qianfariqi="
				+ qianfariqi + "]";
	}
	public void setYenum(String yenum) {
		this.yenum = yenum;
	}
	public String getQianfariqi() {
		return qianfariqi;
	}
	public void setQianfariqi(String qianfariqi) {
		this.qianfariqi = qianfariqi;
	}
	public String getZwid() {
		return zwid;
	}
	public void setZwid(String zwid) {
		this.zwid = zwid;
	}
	public String getFjids() {
		return fjids;
	}
	public void setFjids(String fjids) {
		this.fjids = fjids;
	}
	public String getInstanceid() {
		return instanceid;
	}
	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHuanji() {
		return huanji;
	}
	public void setHuanji(String huanji) {
		this.huanji = huanji;
	}
	public String getWenhao() {
		return wenhao;
	}
	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}
	public String getWenzhong() {
		return wenzhong;
	}
	public void setWenzhong(String wenzhong) {
		this.wenzhong = wenzhong;
	}
	public String getFwdw() {
		return fwdw;
	}
	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}
	public String getLxrname() {
		return lxrname;
	}
	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
	public String getLxrphone() {
		return lxrphone;
	}
	public void setLxrphone(String lxrphone) {
		this.lxrphone = lxrphone;
	}
	public String getFwtype() {
		return fwtype;
	}
	public void setFwtype(String fwtype) {
		this.fwtype = fwtype;
	}
	public String getSenttime() {
		return senttime;
	}
	public void setSenttime(String senttime) {
		this.senttime = senttime;
	}
	public String getZwname() {
		return zwname;
	}
	public void setZwname(String zwname) {
		this.zwname = zwname;
	}
	public String getZwtype() {
		return zwtype;
	}
	public void setZwtype(String zwtype) {
		this.zwtype = zwtype;
	}
	public String getZwcontent() {
		return zwcontent;
	}
	public void setZwcontent(String zwcontent) {
		this.zwcontent = zwcontent;
	}
}
