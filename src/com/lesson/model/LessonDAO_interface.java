package com.lesson.model;

import java.util.*;


import com.lespic.model.LespicVO;

public interface LessonDAO_interface {
	//新增課程
	public void insert(LessonVO lessonVO);
	
	//更新課程資訊
    public void update(LessonVO lessonVO);
    
    //刪除課程by課程編號
    public void delete(String les_no);
    
    //刪除潛店的所有課程
    //public void deleteByDs_no(String ds_no);
    
    //查詢課程
    public LessonVO findByPrimaryKey(String les_no);
    
    //查詢某教練的課程
    public Set<LessonVO> findByCoach(String coach);
    
    //查詢某潛店的課程
    public List<LessonVO> findByShop(String ds_no);
    
    //查詢某課程所在的潛店
    public Set<LessonVO> findByLessonName(String les_name);
    
    //所有課程
    public List<LessonVO> getAll();
    
    //同時新增課程與課程照片
    public void insertWithLespic(LessonVO lessonVO , List<LespicVO> list);
    
    //透過課程取得全部課程照片
    public List<String> getlespic_seqByLess_no(String les_no);
    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<LessonVO> getAll(Map<String, String[]> map); 
    
}
