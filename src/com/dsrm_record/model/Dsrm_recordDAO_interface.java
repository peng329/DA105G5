package com.dsrm_record.model;

import java.util.*;

public interface Dsrm_recordDAO_interface {
	
	public void insert(Dsrm_recordVO dsrm_recordVO);
	
	public void update(Dsrm_recordVO dsrm_recordVO);
	
	public void delete(String rdsr_no);
	
	public Dsrm_recordVO findByPrimaryKey(String rdsr_no);
	
	public List<Dsrm_recordVO> findByDiveshopNo(String ds_no);
	
	public List<Dsrm_recordVO> findByByMemNo(String mem_no);
	
	public List<Dsrm_recordVO> getAll();
	
	public List<Dsrm_recordVO> getAll(Map<String, String[]> map); 
}
