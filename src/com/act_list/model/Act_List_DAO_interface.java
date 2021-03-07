package com.act_list.model;

import java.util.List;
import java.util.Set;

import com.act_list.model.Act_List_VO;
import com.agp_list.model.Agp_List_VO;
import com.gp_ept.model.Gp_Ept_VO;



public interface Act_List_DAO_interface {
	public void insert(Act_List_VO act_list_vo);
    public void update(Act_List_VO act_list_vo);
    public void delete(String act_list_no);
    public Act_List_VO findByPrimaryKey(String act_list_no);
    
    public List<Act_List_VO> getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//    public List<Act_List_VO> getAll(Map<String, String[]> map); 
     
    //查詢某部門的員工(一對多)(回傳 Set)
    public Set<Gp_Ept_VO> getGpEptByActListNo(String act_list_no);
    
    //同時新增部門與員工 (實務上並不常用, 但,可用在訂單主檔與明細檔一次新增成功)
    public void insertWithGpEpt(Act_List_VO act_list_vo , List<Gp_Ept_VO> list);
    
    
    public Set<Act_List_VO> freegroup(String mem_no,String act_state);

    public Set<Act_List_VO> group(String mem_no);
    
}
