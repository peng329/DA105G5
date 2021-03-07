package com.mat_record.model;

import java.util.List;
import java.sql.Timestamp;

public class Mat_recordService {
    private Mat_recordDAO_interface dao;
    
    public Mat_recordService() {
    	dao = new Mat_recordDAO();
    }
	
	public Mat_recordVO addMat_record(String mem_no, String mpo_no, Timestamp trac_time) {
		Mat_recordVO mat_recordVO = new Mat_recordVO();
		mat_recordVO.setMem_no(mem_no);
		mat_recordVO.setMpo_no(mpo_no);
		mat_recordVO.setTrac_time(trac_time);
		dao.insert(mat_recordVO);
		
		return mat_recordVO;
	}
    
	public void addMat_record(Mat_recordVO mat_recordVO) {
		dao.insert(mat_recordVO);
	}
	
	public Mat_recordVO  updateMat_record(String mem_no, String mpo_no, Timestamp trac_time) {
		Mat_recordVO mat_recordVO = new Mat_recordVO();
		mat_recordVO.setMem_no(mem_no);
		mat_recordVO.setMpo_no(mpo_no);
		mat_recordVO.setTrac_time(trac_time);
		dao.update(mat_recordVO);
		
		return dao.findByPrimaryKey(mem_no, mpo_no);
	}
	
	public void  updateMat_record(Mat_recordVO mat_recordVO) {
		dao.update(mat_recordVO);
	}
	
	public void deleteMat_record(String mem_no, String mpo_no) {
		dao.delete(mem_no, mpo_no);
	}
	
	public Mat_recordVO getOneMat_record(String mem_no, String mpo_no) {
		return dao.findByPrimaryKey(mem_no, mpo_no);
	}
	
	public List<Mat_recordVO> getAll(){
		return dao.getAll();
	}
	
	
	public List<Mat_recordVO> getMatrsByMem_no(String mem_no){
		return dao.getMatrsByMem_no(mem_no);
	}
	
	
	
}
