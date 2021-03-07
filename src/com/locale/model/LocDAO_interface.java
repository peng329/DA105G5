package com.locale.model;

import java.util.List;
import java.util.Set;

import com.dive_point.model.DpVO;

public interface LocDAO_interface {

	public void insert(LocVO locVO);

	public void update(LocVO locVO);

	public void delete(String loc_no);

	public LocVO findByPrimaryKey(String loc_no);
    //查詢全部地區
	public List<LocVO> getAll();
    //查詢地區下的潛點
	public Set<DpVO> getDpsByLocno(String loc_no);

}
