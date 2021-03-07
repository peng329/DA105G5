package com.mrds_record.model;

import java.util.List;
import java.sql.Timestamp;

public class Mrds_recordService {
	private Mrds_recordDAO_interface dao;

	public Mrds_recordService() {
		dao = new Mrds_recordDAO();
	}

	public Mrds_recordVO addMrds_record(String mem_no, String ds_no, Timestamp rep_time,
			String rep_content, String rep_state) {

		Mrds_recordVO mrds_recordVO = new Mrds_recordVO();
		mrds_recordVO.setMem_no(mem_no);
		mrds_recordVO.setDs_no(ds_no);
		mrds_recordVO.setRep_time(rep_time);
		mrds_recordVO.setRep_content(rep_content);
		mrds_recordVO.setRep_state(rep_state);
		dao.insert(mrds_recordVO);

		return mrds_recordVO;
	}

	// 預留給Struts 2使用
	public void addMrds_record(Mrds_recordVO mrds_recordVO) {
		dao.insert(mrds_recordVO);
	}

	public Mrds_recordVO updateMrds_record(String mrds_no, String mem_no, String ds_no, Timestamp rep_time,
			String rep_content, String rep_state) {
		Mrds_recordVO mrds_recordVO = new Mrds_recordVO();
		mrds_recordVO.setMrds_no(mrds_no);
		mrds_recordVO.setMem_no(mem_no);
		mrds_recordVO.setDs_no(ds_no);
		mrds_recordVO.setRep_time(rep_time);
		mrds_recordVO.setRep_content(rep_content);
		mrds_recordVO.setRep_state(rep_state);
		dao.update(mrds_recordVO);

		return dao.findByPrimaryKey(mrds_no);
	}

	// 預留給Struts 2使用
    public void updateMrds_record(Mrds_recordVO mrds_recordVO) {
    	dao.update(mrds_recordVO);
    }
    
    public void deleteMrds_record(String mrds_no) {
    	dao.delete(mrds_no);
    }
    
    public Mrds_recordVO getOneMrds_record(String mrds_no) {
    	return dao.findByPrimaryKey(mrds_no);
    }
    
    public List<Mrds_recordVO> getAll(){
    	return dao.getAll();
    }
    
}
