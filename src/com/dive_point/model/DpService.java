package com.dive_point.model;

import java.util.List;

public class DpService {

	private DpDAO_interface dao;

	public DpService() {
		dao = new DpDAO();
	}

	public DpVO addDp(String loc_no, String dp_name, Double dp_lat, Double dp_lng, String dp_info, byte[] dp_pic1,
			byte[] dp_pic2, byte[] dp_pic3, byte[] dp_pic4) {

		DpVO dpVO = new DpVO();
		dpVO.setLoc_no(loc_no);
		dpVO.setDp_name(dp_name);
		dpVO.setDp_lat(dp_lat);
		dpVO.setDp_lng(dp_lng);
		dpVO.setDp_info(dp_info);
		dpVO.setDp_pic1(dp_pic1);
		dpVO.setDp_pic2(dp_pic2);
		dpVO.setDp_pic3(dp_pic3);
		dpVO.setDp_pic4(dp_pic4);

		dao.insert(dpVO);

		return dpVO;
	}

	public DpVO updateDp(String dp_no, String loc_no, String dp_name, Double dp_lat, Double dp_lng, String dp_info,
			byte[] dp_pic1, byte[] dp_pic2, byte[] dp_pic3, byte[] dp_pic4) {

		DpVO dpVO = new DpVO();

		dpVO.setDp_no(dp_no);
		dpVO.setLoc_no(loc_no);
		dpVO.setDp_name(dp_name);
		dpVO.setDp_lat(dp_lat);
		dpVO.setDp_lng(dp_lng);
		dpVO.setDp_info(dp_info);
		dpVO.setDp_pic1(dp_pic1);
		dpVO.setDp_pic2(dp_pic2);
		dpVO.setDp_pic3(dp_pic3);
		dpVO.setDp_pic4(dp_pic4);
		dao.update(dpVO);

		return dpVO;
	}

	public void deleteDp(String dp_no) {
		dao.delete(dp_no);
	}

	public DpVO getOneDp(String dp_no) {
		return dao.findByPrimaryKey(dp_no);
	}

	public List<DpVO> getAll() {
		return dao.getAll();
	}
}
