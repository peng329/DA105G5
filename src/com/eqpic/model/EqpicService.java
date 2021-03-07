package com.eqpic.model;
import java.util.*;
public class EqpicService {
	
	private EqpicDAO_interface dao;
	
	public EqpicService() {
		dao = new EqpicDAO();
	}
	
	public EqpicVO addEqpic(String ds_no,String ep_seq ,byte[] epic) {
		EqpicVO eqpicVO = new EqpicVO();
		eqpicVO.setDs_no(ds_no);
		eqpicVO.setEp_seq(ep_seq);
		eqpicVO.setEpic(epic);
		dao.insert(eqpicVO);
		return eqpicVO;
	}
	
	public EqpicVO updateEqpic(String epic_seq,String ep_seq,byte[] epic) {
		EqpicVO eqpicVO = new EqpicVO();
		eqpicVO.setEpic_seq(epic_seq);
		eqpicVO.setEp_seq(ep_seq);
		eqpicVO.setEpic(epic);
		dao.update(eqpicVO);
		return eqpicVO;
	}
	
	public EqpicVO getOneEpic(String epic_seq) {
		return dao.findByPrimaryKey(epic_seq);
	}
	
	public void deleteEqpic(String epic_seq) {
		dao.delete(epic_seq);
	}

	public List<EqpicVO> findByDsAll(String ds_no){
		return dao.findByDsAll(ds_no);
	}
	
	public List<EqpicVO> findByEpAll(String ep_seq){
		return dao.findByAEp_seq_All_Epic(ep_seq);
	}
	
	public List<String> AEp_seq_All_Epic_seq(String ep_seq){
		return dao.AEp_seq_All_Epic_seq(ep_seq);
	}
	
}
