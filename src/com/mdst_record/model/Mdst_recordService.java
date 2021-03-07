package com.mdst_record.model;

import java.util.List;

import com.mat_record.model.Mat_recordVO;

import java.sql.Timestamp;

public class Mdst_recordService {
    private Mdst_recordDAO_interface dao;
    
    public Mdst_recordService() {
    	dao = new Mdst_recordDAO();
    }
    
    public Mdst_recordVO addMdst_record(String mem_no, String ds_no,Timestamp trac_time) {
    	Mdst_recordVO mdst_recordVO = new Mdst_recordVO();
    	mdst_recordVO.setMem_no(mem_no);
    	mdst_recordVO.setDs_no(ds_no);
    	mdst_recordVO.setTrac_time(trac_time);
    	dao.insert(mdst_recordVO);
    	
    	return mdst_recordVO;
    }
    
    public void addMdst_record(Mdst_recordVO mdst_recordVO) {
    	dao.insert(mdst_recordVO);
    }
    
    public Mdst_recordVO updateMdst_record(String mem_no, String ds_no,Timestamp trac_time) {
    	Mdst_recordVO mdst_recordVO = new Mdst_recordVO();
    	mdst_recordVO.setMem_no(mem_no);
    	mdst_recordVO.setDs_no(ds_no);
    	mdst_recordVO.setTrac_time(trac_time);
    	dao.update(mdst_recordVO);
    	
    	return dao.findByPrimaryKey(mem_no, ds_no);
    }
    
    public void updateMdst_record(Mdst_recordVO mdst_recordVO) {
    	dao.update(mdst_recordVO);
    }
    
    public void deleteMdst_record(String mem_no, String ds_no) {
    	dao.delete(mem_no, ds_no);
    }
    
    public Mdst_recordVO getOneMdst_record(String mem_no, String ds_no) {
    	return dao.findByPrimaryKey(mem_no, ds_no);
    }
    
    public List<Mdst_recordVO> getAll(){
    	return dao.getAll();
    }
    
	public List<Mdst_recordVO> getMdstrsByMem_no(String mem_no){
		return dao.getMdstrsByMem_no(mem_no);
	}
}
