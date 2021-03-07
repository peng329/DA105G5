package com.lessonorder.model;

import java.util.*;

public interface LessonOrderDAO_interface {
	//insert order
	public void insert(LessonOrderVO lessonOrderVO);
	//update order
	public void update(LessonOrderVO lessonOrderVO);
	//delete
	public void delete(String les_o_no);
	//select
	public LessonOrderVO findByPrimaryKey(String les_o_no);
	public List<LessonOrderVO> findByMem_no(String mem_no);
	public List<LessonOrderVO> findByLes_no(String les_no);
	public List<LessonOrderVO> findByDs_no(String ds_no);
	
	public List<String> findmem_noByLes_no(String les_no);
	
	public List<LessonOrderVO> getAll();
}
