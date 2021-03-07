package com.mrds_record.model;

import java.sql.Timestamp;

public class Mrds_recordVO implements java.io.Serializable {
     private String mrds_no;
     private String mem_no;
     private String ds_no;
     private Timestamp rep_time;
     private String rep_content;
     private String rep_state;
	public String getMrds_no() {
		return mrds_no;
	}
	public void setMrds_no(String mrds_no) {
		this.mrds_no = mrds_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getDs_no() {
		return ds_no;
	}
	public void setDs_no(String ds_no) {
		this.ds_no = ds_no;
	}
	public Timestamp getRep_time() {
		return rep_time;
	}
	public void setRep_time(Timestamp rep_time) {
		this.rep_time = rep_time;
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
