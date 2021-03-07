package com.gp_ept.model;

import java.util.List;



public interface Gp_Ept_DAO_interface {

	public void insert(Gp_Ept_VO gp_ept_vo);
    public void update(Gp_Ept_VO gp_ept_vo);
    public void delete(String gp_ept_no);
    public Gp_Ept_VO  findByPrimaryKey(String gp_ept_no);
    public List<Gp_Ept_VO > getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<GP_EPT_VO > getAll(Map<String, String[]> map);
	
    //同時新增部門與員工 (實務上並不常用, 但,可用在訂單主檔與明細檔一次新增成功)
    public void insert2 (Gp_Ept_VO gp_ept_vo , java.sql.Connection con);
    
	public void insert3(List<Gp_Ept_VO> list);
    
    public Gp_Ept_VO  findByMe(String mem_no);

}
