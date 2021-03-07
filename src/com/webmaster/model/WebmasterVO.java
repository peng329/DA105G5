package com.webmaster.model;

import java.sql.*;

public final class WebmasterVO implements java.io.Serializable{
    private String wm_no;
    private String wm_name;
    private Integer wm_sex;
    private String wm_id;
    private String wm_psw;
    private String wm_mail;
    private Date ob_date;
    private Date dd_date;
    private Timestamp reg_time;
    
    
    public String getWm_no() {
        return wm_no;
    }
    public void setWm_no(String wm_no) {
        this.wm_no = wm_no;
    }
    public String getWm_name() {
        return wm_name;
    }
    public void setWm_name(String wm_name) {
        this.wm_name = wm_name;
    }
    public Integer getWm_sex() {
        return wm_sex;
    }
    public void setWm_sex(Integer wm_sex) {
        this.wm_sex = wm_sex;
    }
    public String getWm_id() {
        return wm_id;
    }
    public void setWm_id(String wm_id) {
        this.wm_id = wm_id;
    }
    public String getWm_psw() {
        return wm_psw;
    }
    public void setWm_psw(String wm_psw) {
        this.wm_psw = wm_psw;
    }
    public String getWm_mail() {
        return wm_mail;
    }
    public void setWm_mail(String wm_mail) {
        this.wm_mail = wm_mail;
    }
    public Date getOb_date() {
        return ob_date;
    }
    public void setOb_date(Date ob_date) {
        this.ob_date = ob_date;
    }
    public Date getDd_date() {
        return dd_date;
    }
    public void setDd_date(Date dd_date) {
        this.dd_date = dd_date;
    }
    public Timestamp getReg_time() {
        return reg_time;
    }
    public void setReg_time(Timestamp reg_time) {
        this.reg_time = reg_time;
    }
    
    

}
