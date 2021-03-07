package com.equip.model;
import java.sql.Date;

public class EquipVO implements java.io.Serializable{
	private String ep_seq;
	private String epc_no;
	private String ds_no;
	private String ds_name;
	private String ep_name;
	private String ep_no;
	private String ep_size;
	private Integer ep_priz;
	private Integer ep_rp;
	private String ep_state;
	private String epr_state;
	private String eps_state;
	
	public EquipVO(){
		ds_no = "";
		ep_seq = "";
		ep_name = "";
		ep_size = "";
		ep_rp = 0;
		ep_state = "";
		epr_state = "";
		eps_state = "";
	}
	
	
	public String getEp_seq() {
		return ep_seq;
	}
	public void setEp_seq(String ep_seq) {
		this.ep_seq = ep_seq;
	}
	public String getEpc_no() {
		return epc_no;
	}
	public void setEpc_no(String epc_no) {
		this.epc_no = epc_no;
	}
	public String getDs_no() {
		return ds_no;
	}
	public void setDs_no(String ds_no) {
		this.ds_no = ds_no;
	}
	public String getDs_name() {
		return ds_name;
	}
	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}
	public String getEp_name() {
		return ep_name;
	}
	public void setEp_name(String ep_name) {
		this.ep_name = ep_name;
	}
	public String getEp_no() {
		return ep_no;
	}
	public void setEp_no(String ep_no) {
		this.ep_no = ep_no;
	}
	public String getEp_size() {
		return ep_size;
	}
	public void setEp_size(String ep_size) {
		this.ep_size = ep_size;
	}
	public Integer getEp_priz() {
		return ep_priz;
	}
	public void setEp_priz(Integer ep_priz) {
		this.ep_priz = ep_priz;
	}
	public Integer getEp_rp() {
		return ep_rp;
	}
	public void setEp_rp(Integer ep_rp) {
		this.ep_rp = ep_rp;
	}
	public String getEp_state() {
		return ep_state;
	}
	public void setEp_state(String ep_state) {
		this.ep_state = ep_state;
	}
	public String getEpr_state() {
		return epr_state;
	}
	public void setEpr_state(String epr_state) {
		this.epr_state = epr_state;
	}
	public String getEps_state() {
		return eps_state;
	}
	public void setEps_state(String eps_state) {
		this.eps_state = eps_state;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ds_no == null) ? 0 : ds_no.hashCode());
		result = prime * result + ((ep_name == null) ? 0 : ep_name.hashCode());
		result = prime * result + ((ep_size == null) ? 0 : ep_size.hashCode());
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
		EquipVO other = (EquipVO) obj;
		if (ds_no == null) {
			if (other.ds_no != null)
				return false;
		} else if (!ds_no.equals(other.ds_no))
			return false;
		if (ep_name == null) {
			if (other.ep_name != null)
				return false;
		} else if (!ep_name.equals(other.ep_name))
			return false;
		if (ep_size == null) {
			if (other.ep_size != null)
				return false;
		} else if (!ep_size.equals(other.ep_size))
			return false;
		return true;
	}
	
	
}
