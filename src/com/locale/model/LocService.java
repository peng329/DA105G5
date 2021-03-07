package com.locale.model;

import java.util.List;
import java.util.Set;

import com.dive_point.model.DpVO;

public class LocService {

	private LocDAO_interface dao;

	public LocService() {
		dao = new LocDAO();
	}

	public LocVO addLoc(String ctry, String sub_region, String weather) {

		LocVO locVO = new LocVO();

		locVO.setCtry(ctry);
		locVO.setSub_region(sub_region);
		locVO.setWeather(weather);
		dao.insert(locVO);

		return locVO;
	}

	public LocVO updateLoc(String loc_no, String ctry, String sub_region, String weather) {

		LocVO locVO = new LocVO();

		locVO.setLoc_no(loc_no);
		locVO.setCtry(ctry);
		locVO.setSub_region(sub_region);
		locVO.setWeather(weather);
		dao.update(locVO);

		return locVO;
	}

	public void deleteLoc(String loc_no) {
		dao.delete(loc_no);
	}

	public LocVO getOneLoc(String loc_no) {
		return dao.findByPrimaryKey(loc_no);
	}

	public List<LocVO> getAll() {
		return dao.getAll();
	}

	public Set<DpVO> getDpsByLocno(String loc_no) {
		return dao.getDpsByLocno(loc_no);
	}
}
