package com.lespic.model;

import java.util.*;

public interface LespicDAO_interface {

	public void insert(LespicVO lespicVO);

	public void update(LespicVO lespicVO);

	public void delete(String lpic_seq);

	public LespicVO findByPrimaryKey(String lpic_seq);

	public List<LespicVO> findByLes_no(String les_no);

	public List<LespicVO> getAll();
	
	//同時新增課程與課程照片
	public void insert2 (LespicVO lespicVO, java.sql.Connection con);
}
