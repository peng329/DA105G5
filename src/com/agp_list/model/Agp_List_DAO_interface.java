package com.agp_list.model;

import java.util.List;
import java.util.Set;

import com.act_list.model.Act_List_VO;

public interface Agp_List_DAO_interface {
	public void insert(Agp_List_VO agp_list_vo);
    public void update(Agp_List_VO  agp_list_vo);
    public void delete(String agp_list_no,String mem_no);
    public Agp_List_VO  findByPrimaryKey(String agp_list_no,String mem_no);
    public List<Agp_List_VO > getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<AGP_LIST_VO > getAll(Map<String, String[]> map); 
    
    public Set<Agp_List_VO> joingroup(String mem_no,String agp_state);
	public Set<Agp_List_VO> findMemState(String agp_state);

}
