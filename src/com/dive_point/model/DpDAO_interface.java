package com.dive_point.model;

import java.util.List;
import java.util.Set;



public interface DpDAO_interface {
    public void insert(DpVO dpVO);
    public void update(DpVO dpVO);
    public void delete(String dp_no);
    public DpVO findByPrimaryKey(String dp_no);
    public List<DpVO> getAll();
    //查詢某地區的潛點(一對多)(回傳 Set)

}
