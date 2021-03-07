package com.mem.model;

import java.util.List;


public class MemService {
	private MemDAO_interface dao;

	public MemService() {
	    dao = new MemDAO();
	}

	public MemVO addMem(String mem_id, String mem_psw, String mem_name, Integer mem_sex, java.sql.Date mem_bd, String mem_mail, String mem_phone, String mem_add, byte[] mem_pic, java.sql.Timestamp reg_time, Integer mem_rep_no, String mem_state) {
	    MemVO memVO = new MemVO();

	    memVO.setMem_id(mem_id);
	    memVO.setMem_psw(mem_psw);
	    memVO.setMem_name(mem_name);
	    memVO.setMem_sex(mem_sex);
	    memVO.setMem_bd(mem_bd);
	    memVO.setMem_mail(mem_mail);
	    memVO.setMem_phone(mem_phone);
	    memVO.setMem_add(mem_add);
	    memVO.setMem_pic(mem_pic);
	    memVO.setReg_time(reg_time);
	    memVO.setMem_rep_no(mem_rep_no);
	    memVO.setMem_state(mem_state);
	    dao.insert(memVO);

	    return memVO;
	}

	public MemVO updateMem(String mem_no, String mem_id, String mem_psw, String mem_name, Integer mem_sex, java.sql.Date mem_bd, String mem_mail, String mem_phone, String mem_add, byte[] mem_pic, java.sql.Timestamp reg_time, Integer mem_rep_no, String mem_state) {
	    MemVO memVO = new MemVO();

	    memVO.setMem_no(mem_no);
	    memVO.setMem_id(mem_id);
	    memVO.setMem_psw(mem_psw);
	    memVO.setMem_name(mem_name);
	    memVO.setMem_sex(mem_sex);
	    memVO.setMem_bd(mem_bd);
	    memVO.setMem_mail(mem_mail);
	    memVO.setMem_phone(mem_phone);
	    memVO.setMem_add(mem_add);
	    memVO.setMem_pic(mem_pic);
	    memVO.setReg_time(reg_time);
	    memVO.setMem_rep_no(mem_rep_no);
	    memVO.setMem_state(mem_state);
	    dao.update(memVO);

	    return memVO;
	}

	public void deleteMem(String mem_no) {
	    dao.delete(mem_no);
	}

	public MemVO getOneMem(String mem_no) {
	    return dao.findByPrimaryKey(mem_no);
	}
	
	public MemVO getOneMemById(String mem_id) {
	    return dao.findByMem_id(mem_id);
	}

	public List<MemVO> getAll(){
	    return dao.getAll();
	}
	
	public void updateMemState(String mem_id, String stateNum) {
	    dao.updateMemState(mem_id, stateNum);
	}

}


