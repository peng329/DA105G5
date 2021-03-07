package com.dsrm_record.model;

import java.sql.Date;

public class Dsrm_recordVO implements java.io.Serializable{
	private String rdsr_no;
	private String ds_no;
	private String mem_no;
	private String rep_content;
	private String rep_state;
	
	public String getRdsr_no() {
		return rdsr_no;
	}
	public void setRdsr_no(String rdsr_no) {
		this.rdsr_no = rdsr_no;
	}
	public String getDs_no() {
		return ds_no;
	}
	public void setDs_no(String ds_no) {
		this.ds_no = ds_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getRep_content() {
		return rep_content;
	}
	public void setRep_content(String rep_content) {
		this.rep_content = rep_content;
	}
	public String getRep_state() {
		return rep_state;
	}
	public void setRep_state(String rep_state) {
		this.rep_state = rep_state;
	}
	
	
}
