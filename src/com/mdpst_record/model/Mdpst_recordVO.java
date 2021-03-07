package com.mdpst_record.model;

import java.sql.Timestamp;

public class Mdpst_recordVO implements java.io.Serializable{
     private String mem_no;
     private String dp_no;
     private Timestamp trac_time;
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
	public Timestamp getTrac_time() {
		return trac_time;
	}
	public void setTrac_time(Timestamp trac_time) {
		this.trac_time = trac_time;
	}
     
     
}
