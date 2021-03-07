package com.dspic.model;

import java.util.List;
import java.util.Set;

public class DspicService {

	private DspicDAO_interface dao;
	
	public DspicService() {
		dao = new DspicJDBCDAO();
	}
	
	public DspicVO addDspic (String ds_no, byte[] dpic) {
		
		DspicVO dspicVO = new DspicVO();
		
		dspicVO.setDs_no(ds_no);
		dspicVO.setDpic(dpic);
		dao.insert(dspicVO);
		
		return dspicVO;
	}
	
	public DspicVO updatedspic(String ds_no, byte[] dpic,String dpic_seq) {
		
		DspicVO dspicVO = new DspicVO();
		
		dspicVO.setDpic_seq(dpic_seq);
		dspicVO.setDs_no(ds_no);
		dspicVO.setDpic(dpic);
		dao.update(dspicVO);
		
		return dspicVO;
	}
	
	public void deleteDspic(String dpic_seq) {
		dao.delete(dpic_seq);
	}
	
	public DspicVO getOneDspic(String dpic_seq) {
		return dao.findByPrimaryKey(dpic_seq);
	}
	
	public List<DspicVO> getDspicByDsno(String ds_no) {
		return dao.findByDs_no(ds_no);
	}
	
	public List<DspicVO> getAll(){
		return dao.getAll();
	}
	
	
}
