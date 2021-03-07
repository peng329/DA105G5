package com.dsrm_record.model;

import java.util.List;
import java.util.Map;

public class Dsrm_recordService {
	
	private Dsrm_recordDAO_interface dao;
	
	public Dsrm_recordService() {
		dao = new Dsrm_recordJDBCDAO();
	}
	
	public Dsrm_recordVO addDsrm_record(String ds_no, String mem_no, String rep_content, String rep_state) {
		
		Dsrm_recordVO dsrm_recordVO = new Dsrm_recordVO();
		dsrm_recordVO.setDs_no(ds_no);
		dsrm_recordVO.setMem_no(mem_no);
		dsrm_recordVO.setRep_content(rep_content);
		dsrm_recordVO.setRep_state(rep_state);
		dao.insert(dsrm_recordVO);
		
		return dsrm_recordVO;
	}
	
	public Dsrm_recordVO updateDsrm_record(String rdsr_no,String ds_no, String mem_no, String rep_content, String rep_state) {
		
		Dsrm_recordVO dsrm_recordVO = new Dsrm_recordVO();
		
		dsrm_recordVO.setRdsr_no(rdsr_no);
		dsrm_recordVO.setDs_no(ds_no);
		dsrm_recordVO.setMem_no(mem_no);
		dsrm_recordVO.setRep_content(rep_content);
		dsrm_recordVO.setRep_state(rep_state);
		dao.update(dsrm_recordVO);
		
		return dsrm_recordVO;
	}
	
	public void deleteDsrm_reocrd(String rdsr_no) {
		dao.delete(rdsr_no);
	}
	
	public Dsrm_recordVO getOneDsrm_record(String rdsr_no) {
		return dao.findByPrimaryKey(rdsr_no);
	}
	
	public List<Dsrm_recordVO> getDsrm_recordByDsno(String ds_no) {
		return dao.findByDiveshopNo(ds_no);
	}
	
	public List<Dsrm_recordVO> getDsrm_recordByMemno(String mem_no){
		return dao.findByByMemNo(mem_no);
	}
	
	public List<Dsrm_recordVO> getAll(){
		return dao.getAll();
	}
	
	public List<Dsrm_recordVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}
}
