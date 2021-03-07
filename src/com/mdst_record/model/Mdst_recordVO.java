package com.mdst_record.model;
import java.sql.Date;

import java.sql.Timestamp;

public class Mdst_recordVO implements java.io.Serializable{
	private String mem_no ;
	private String ds_no ;
	private Timestamp  trac_time;
	
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
	
	public Timestamp getTrac_time() {
		return trac_time;
	}
	public void setTrac_time(Timestamp trac_time) {
		this.trac_time = trac_time;
	}

}
