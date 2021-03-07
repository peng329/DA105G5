package com.mra_record.model;
import java.sql.Timestamp;

public class Mra_recordVO implements java.io.Serializable{
     private String rar_no;
     private String mem_no;
     private String mpo_no;
     private Timestamp rep_time;
     private String rep_content;
     private String rep_state;
	
     
     public String getRar_no() {
		return rar_no;
	}
	public void setRar_no(String rar_no) {
		this.rar_no = rar_no;
	}
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
