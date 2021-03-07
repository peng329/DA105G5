package com.lespic.model;

public class LespicVO implements java.io.Serializable{
	private String lpic_seq;
	private String les_no;
	private byte[] lpic;
	private String lpic_name;
	
	public String getLpic_seq() {
		return lpic_seq;
	}
	public void setLpic_seq(String lpic_seq) {
		this.lpic_seq = lpic_seq;
	}
	public String getLes_no() {
		return les_no;
	}
	public void setLes_no(String les_no) {
		this.les_no = les_no;
	}
	public byte[] getLpic() {
		return lpic;
	}
	public void setLpic(byte[] lpic) {
		this.lpic = lpic;
	}
	public String getLpic_name() {
		return lpic_name;
	}
	public void setLpic_name(String lpic_name) {
		this.lpic_name = lpic_name;
	}
}
