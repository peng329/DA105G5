package com.act_list.model;

import java.sql.Date;

public class Act_List_VO implements java.io.Serializable{
	private String act_list_no;
	private String mem_no;
	private String dp_no;
	private Date start_date;
	private Date dual_date;
	private Date action_date;
	private String act_state;
	private String locale;
	private Integer act_max;
	private Integer act_min;
	private byte[] gp_pic;
	private String gp_info;
	private Integer gp_days;
	private String act_list_name;

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((act_list_no == null) ? 0 : act_list_no.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Act_List_VO other = (Act_List_VO) obj;
		if (act_list_no == null) {
			if (other.act_list_no != null)
				return false;
		} else if (!act_list_no.equals(other.act_list_no))
			return false;
		return true;
	}
	public String getAct_list_no() {
		return act_list_no;
	}
	public void setAct_list_no(String act_list_no) {
		this.act_list_no = act_list_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getDp_no() {
		return dp_no;
	}
	public void setDp_no(String dp_no) {
		this.dp_no = dp_no;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getDual_date() {
		return dual_date;
	}
	public void setDual_date(Date dual_date) {
		this.dual_date = dual_date;
	}
	public Date getAction_date() {
		return action_date;
	}
	public void setAction_date(Date action_date) {
		this.action_date = action_date;
	}
	public String getAct_state() {
		return act_state;
	}
	public void setAct_state(String act_state) {
		this.act_state = act_state;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public Integer getAct_max() {
		return act_max;
	}
	public void setAct_max(Integer act_max) {
		this.act_max = act_max;
	}
	public Integer getAct_min() {
		return act_min;
	}
	public void setAct_min(Integer act_min) {
		this.act_min = act_min;
	}
	public byte[] getGp_pic() {
		return gp_pic;
	}
	public void setGp_pic(byte[] gp_pic) {
		this.gp_pic = gp_pic;
	}
	public String getGp_info() {
		return gp_info;
	}
	public void setGp_info(String gp_info) {
		this.gp_info = gp_info;
	}
	public Integer getGp_days() {
		return gp_days;
	}
	public void setGp_days(Integer gp_days) {
		this.gp_days = gp_days;
	}
	
	public String getAct_list_name() {
		return act_list_name;
	}
	public void setAct_list_name(String act_list_name) {
		this.act_list_name = act_list_name;
	}
	
	
	

}
