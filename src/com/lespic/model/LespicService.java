package com.lespic.model;

import java.util.List;
import java.util.Set;

public class LespicService {
	
	private LespicDAO_interface dao;
	
	public LespicService() {
		dao = new LespicDAO();
	}
	
	public LespicVO addLespic(String les_no, byte[] lpic) {
		
		LespicVO lespicVO = new LespicVO();
		
		lespicVO.setLes_no(les_no);
		lespicVO.setLpic(lpic);
		dao.insert(lespicVO);
		
		return lespicVO;
	}
	
	public LespicVO updateLespic(String lpic_seq,String les_no, byte[] lpic) {
		
		LespicVO lespicVO = new LespicVO();
		
		lespicVO.setLpic_seq(lpic_seq);
		lespicVO.setLes_no(les_no);
		lespicVO.setLpic(lpic);
		dao.update(lespicVO);
		
		return lespicVO;
	}
	
	public void deleteLespic(String lpic_seq) {
		dao.delete(lpic_seq);
	}
	
	public LespicVO getOneLespic(String lpic_seq) {
		return dao.findByPrimaryKey(lpic_seq);
	}
	
	public List<LespicVO> getAll(){
		return dao.getAll();
	}
	
	public List<LespicVO> getLespicByLes_no(String les_no){
		return dao.findByLes_no(les_no);
	}
	
	
}
