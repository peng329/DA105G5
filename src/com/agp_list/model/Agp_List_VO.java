package com.agp_list.model;

public class Agp_List_VO implements java.io.Serializable{
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
		Agp_List_VO other = (Agp_List_VO) obj;
		if (act_list_no == null) {
			if (other.act_list_no != null)
				return false;
		} else if (!act_list_no.equals(other.act_list_no))
			return false;
		return true;
	}
	private String act_list_no;
	private String mem_no;
	private String mem_lic;
	private Integer act_num;
	private byte[] mem_lic_pic;
	private String agp_state;
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
	public String getMem_lic() {
		return mem_lic;
	}
	public void setMem_lic(String mem_lic) {
		this.mem_lic = mem_lic;
	}
	public Integer getAct_num() {
		return act_num;
	}
	public void setAct_num(Integer act_num) {
		this.act_num = act_num;
	}
	public byte[] getMem_lic_pic() {
		return mem_lic_pic;
	}
	public void setMem_lic_pic(byte[] mem_lic_pic) {
		this.mem_lic_pic = mem_lic_pic;
	}
	public String getAgp_state() {
		return agp_state;
	}
	public void setAgp_state(String agp_state) {
		this.agp_state = agp_state;
	}
	
}
