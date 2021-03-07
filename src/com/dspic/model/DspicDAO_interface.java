package com.dspic.model;

import java.util.*;

public interface DspicDAO_interface {
		
	public void insert(DspicVO dspicVO);
	
	public void update(DspicVO dspicVO);
	
	public void delete(String dpic_seq);
	
	public DspicVO findByPrimaryKey(String dpic_seq);
	
	public List<DspicVO> findByDs_no(String ds_no);
	
	public List<DspicVO> getAll();
	
	//同時新增潛店與潛店照片
	public void inert2 (DspicVO dspicVO, java.sql.Connection con);
}
