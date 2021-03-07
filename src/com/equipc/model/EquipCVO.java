package com.equipc.model;

public class EquipCVO implements java.io.Serializable{
	private String epc_no;
	private String epc_name;
	
	public EquipCVO() {
		
	}
	
	public String getEpc_no() {
		return epc_no;
	}
	public void setEpc_no(String epc_no) {
		this.epc_no = epc_no;
	}
	public String getEpc_name() {
		return epc_name;
	}
	public void setEpc_name(String epc_name) {
		this.epc_name = epc_name;
	}
}
