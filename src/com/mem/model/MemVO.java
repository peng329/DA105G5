package com.mem.model;

import java.sql.*;

public class MemVO implements java.io.Serializable{
    private String mem_no;
    private String mem_id;
    private String mem_psw;
    private String mem_name;
    private Integer mem_sex;
    private Date mem_bd;
    private String mem_mail;
    private String mem_phone;
    private String mem_add;
    private byte[] mem_pic;
    private Timestamp reg_time;
    private Integer mem_rep_no;
    private String mem_state;
    
    public String getMem_no() {
        return mem_no;
    }
    public void setMem_no(String mem_no) {
        this.mem_no = mem_no;
    }
    public String getMem_id() {
        return mem_id;
    }
    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }
    public String getMem_psw() {
        return mem_psw;
    }
    public void setMem_psw(String mem_psw) {
        this.mem_psw = mem_psw;
    }
    public String getMem_name() {
        return mem_name;
    }
    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }
    public Integer getMem_sex() {
        return mem_sex;
    }
    public void setMem_sex(Integer mem_sex) {
        this.mem_sex = mem_sex;
    }
    public Date getMem_bd() {
        return mem_bd;
    }
    public void setMem_bd(Date mem_bd) {
        this.mem_bd = mem_bd;
    }
    public String getMem_mail() {
        return mem_mail;
    }
    public void setMem_mail(String mem_mail) {
        this.mem_mail = mem_mail;
    }
    public String getMem_phone() {
        return mem_phone;
    }
    public void setMem_phone(String mem_phone) {
        this.mem_phone = mem_phone;
    }
    public String getMem_add() {
        return mem_add;
    }
    public void setMem_add(String mem_add) {
        this.mem_add = mem_add;
    }
    public byte[] getMem_pic() {
        return mem_pic;
    }
    public void setMem_pic(byte[] mem_pic) {
        this.mem_pic = mem_pic;
    }
    public Timestamp getReg_time() {
        return reg_time;
    }
    public void setReg_time(Timestamp reg_time) {
        this.reg_time = reg_time;
    }
    public Integer getMem_rep_no() {
        return mem_rep_no;
    }
    public void setMem_rep_no(Integer mem_rep_no) {
        this.mem_rep_no = mem_rep_no;
    }
    public String getMem_state() {
        return mem_state;
    }
    public void setMem_state(String mem_state) {
        this.mem_state = mem_state;
    }
    
    
    

}
