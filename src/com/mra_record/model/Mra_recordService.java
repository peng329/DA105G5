package com.mra_record.model;

import java.util.List;
import java.sql.Timestamp;

public class Mra_recordService {
    private Mra_recordDAO_interface dao;
    
    public Mra_recordService() {
    	dao = new Mra_recordDAO();
    }
    
    public Mra_recordVO addMra_record(String mem_no, String mpo_no,Timestamp rep_time,
    		String rep_content, String rep_state) {
    	Mra_recordVO mra_recordVO = new Mra_recordVO();
    	mra_recordVO.setMem_no(mem_no);
    	mra_recordVO.setMpo_no(mpo_no);
    	mra_recordVO.setRep_time(rep_time);
    	mra_recordVO.setRep_content(rep_content);
    	mra_recordVO.setRep_state(rep_state);
    	dao.insert(mra_recordVO);
    	
    	return mra_recordVO;
    }
    
    //預留給Struts 2 使用
    public void addMra_record(Mra_recordVO mra_recordVO) {
    	dao.insert(mra_recordVO);
    }
    
    public Mra_recordVO updateMra_record(String rar_no, String mem_no, String mpo_no,Timestamp rep_time,
    		String rep_content, String rep_state) {
    	Mra_recordVO mra_recordVO = new Mra_recordVO();
    	mra_recordVO.setRar_no(rar_no);
    	mra_recordVO.setMem_no(mem_no);
    	mra_recordVO.setMpo_no(mpo_no);
    	mra_recordVO.setRep_time(rep_time);
    	mra_recordVO.setRep_content(rep_content);
    	mra_recordVO.setRep_state(rep_state);
    	dao.update(mra_recordVO);
    	
    	return dao.findByPrimaryKey(rar_no);
    }
            
    //預留給Struts 2 使用
    public void updateMra_record(Mra_recordVO mra_recordVO) {
    	dao.update(mra_recordVO);
    }
    
    public void deleteMra_record(String rar_no) {
    	dao.delete(rar_no);
    }
    
    public Mra_recordVO getOneMra_record(String rar_no) {
    	return dao.findByPrimaryKey(rar_no);
    }
    
    public List<Mra_recordVO> getAll(){
    	return dao.getAll();
    }
    
    
}
