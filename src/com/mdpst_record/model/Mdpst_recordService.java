package com.mdpst_record.model;

import java.util.List;

import com.mdst_record.model.Mdst_recordVO;

import java.sql.Timestamp;

public class Mdpst_recordService {
    private Mdpst_recordDAO_interface dao ;
    
    public Mdpst_recordService() {
    	dao = new Mdpst_recordDAO();
    }
    
    public Mdpst_recordVO addMdpst_record(String mem_no,String dp_no,Timestamp trac_time) {
    	Mdpst_recordVO mdpst_recordVO = new Mdpst_recordVO();
    	mdpst_recordVO.setMem_no(mem_no);
    	mdpst_recordVO.setDp_no(dp_no);
    	mdpst_recordVO.setTrac_time(trac_time);
    	dao.insert(mdpst_recordVO);
    	
    	return mdpst_recordVO;
    }
    
    public void addMdpst_record(Mdpst_recordVO mdpst_recordVO) {
    	dao.insert(mdpst_recordVO);
    }
    
    public Mdpst_recordVO updateMdpst_record(String mem_no,String dp_no,Timestamp trac_time) {
    	Mdpst_recordVO mdpst_recordVO = new Mdpst_recordVO();
    	mdpst_recordVO.setMem_no(mem_no);
    	mdpst_recordVO.setDp_no(dp_no);
    	mdpst_recordVO.setTrac_time(trac_time);
    	dao.update(mdpst_recordVO);
    	
    	return dao.findByPrimaryKey(mem_no, dp_no);
    }
    
    public void updateMdpst_record(Mdpst_recordVO mdpst_recordVO) {
    	dao.update(mdpst_recordVO);
    }
    
    public void deleteMdpst_record(String mem_no, String dp_no) {
    	dao.delete(mem_no, dp_no);
    }
    
    public Mdpst_recordVO getOneMdpst_record(String mem_no,String dp_no) {
    	return dao.findByPrimaryKey(mem_no, dp_no);
    }
    
    public List<Mdpst_recordVO> getAll(){
    	return dao.getAll();
    }
    
	public List<Mdpst_recordVO> getMdpstrsByMem_no(String mem_no){
		return dao.getMdpstrsByMem_no(mem_no);
	}
    
    
}
