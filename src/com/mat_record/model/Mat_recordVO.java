package com.mat_record.model;

import java.sql.Timestamp;

public class Mat_recordVO implements java.io.Serializable{
    private String mem_no;
    private String mpo_no;
    private Timestamp trac_time;
	
    public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMpo_no() {
		return mpo_no;
	}
	public void setMpo_no(String mpo_no) {
		this.mpo_no = mpo_no;
	}
	public Timestamp getTrac_time() {
		return trac_time;
	}
	public void setTrac_time(Timestamp trac_time) {
		this.trac_time = trac_time;
	}
    
    
}
