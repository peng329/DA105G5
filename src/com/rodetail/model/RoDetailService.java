package com.rodetail.model;
import java.util.*;
public class RoDetailService {
	private RoDetailDAO_interface dao;
	
	public RoDetailService() {
		dao = new RoDetailDAO();
	}
	
	public RoDetailVO addRoDetail(String ro_no,String ep_seq,Integer ep_crp) {
		RoDetailVO rdVO = new RoDetailVO();
		
		rdVO.setRo_no(ro_no);
		rdVO.setEp_seq(ep_seq);
		rdVO.setEp_crp(ep_crp);
		
		dao.insert(rdVO);
		return rdVO;
		
	}
	
	public RoDetailVO updateRoDetail(String ro_no,String ep_seq,Integer ep_crp) {
		RoDetailVO rdVO = new RoDetailVO();
		rdVO.setRo_no(ro_no);
		rdVO.setEp_seq(ep_seq);
		rdVO.setEp_crp(ep_crp);
		
		dao.update(rdVO);
		return rdVO;
	}
	
	public void deleteRoDetail(String ro_no,String ep_seq) {
		dao.delete(ro_no, ep_seq);
	}

	public RoDetailVO getOneRd(String ro_no,String ep_seq) {
		return dao.findByPrimaryKey(ro_no, ep_seq);
	}
	
	
	public List<RoDetailVO> getgetSameRoRdAll(String ro_no){
		return dao.getSameRoRdAll(ro_no);
	}
	
//	public List<RoDetailVO> getAll(){
//		return dao.getAll();
//	}
	
}
