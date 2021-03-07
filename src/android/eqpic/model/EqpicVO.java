package android.eqpic.model;

import java.sql.Date;
import java.io.*;

public class EqpicVO implements java.io.Serializable{
	private String epic_seq;
	private String ds_no;
	private String ep_seq;
	private byte[] epic;
	
	public EqpicVO() {
		
	}
	public String getEpic_seq() {
		return epic_seq;
	}
	public void setEpic_seq(String epic_seq) {
		this.epic_seq = epic_seq;
	}
	public String getDs_no() {
		return ds_no;
	}
	public void setDs_no(String ds_no) {
		this.ds_no = ds_no;
	}
	public String getEp_seq() {
		return ep_seq;
	}
	public void setEp_seq(String ep_seq) {
		this.ep_seq = ep_seq;
	}
	public byte[] getEpic() {
		return epic;
	}
	public void setEpic(byte[] epic) {
		this.epic = epic;
	}
}