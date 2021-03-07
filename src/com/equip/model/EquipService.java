package com.equip.model;
import java.util.*;

import com.eqpic.model.EqpicVO;

public class EquipService {
	
	private EquipDAO_interface dao;
	
	public EquipService() {
		dao = new EquipDAO();
	}
	
	public EquipVO updateEquip(String ep_seq,String epc_no,String ds_no,String ds_name,String ep_name,String ep_no,String ep_size,Integer ep_priz,Integer ep_rp,String ep_state,String epr_state,String eps_state) {
		EquipVO equipVO = new EquipVO();
		equipVO.setEpc_no(epc_no);
		equipVO.setDs_no(ds_no);
		equipVO.setDs_name(ds_name);
		equipVO.setEp_name(ep_name);
		equipVO.setEp_no(ep_no);
		equipVO.setEp_size(ep_size);
		equipVO.setEp_priz(ep_priz);
		equipVO.setEp_rp(ep_rp);
		equipVO.setEp_state(ep_state);
		equipVO.setEps_state(eps_state);
		equipVO.setEpr_state(epr_state);
		equipVO.setEp_seq(ep_seq);
		dao.update(equipVO);
		
		return equipVO;
	}
	
	public void deleteEquip(String ep_seq) {
		dao.delete(ep_seq);
	}
	
	public EquipVO getOneEq(String ep_seq) {
		return dao.findByPrimaryKey(ep_seq);
	}
	
	public EquipVO getOneDsEp(String ds_no,String ep_no) {
		return dao.findByDsEpNo(ds_no, ep_no);
	}
	
	public List<EquipVO> getDSAll(String ds_no){
		return dao.getDSAll(ds_no);
	}
	public List<EquipVO> getDSAllFORSHOP(String ds_no){
		return dao.getDSAllFORSHOP(ds_no);
	}
		
	public List<EquipVO> getAll(){
		return dao.getAll();
	}
	public List<EquipVO> getAll(Map<String,String[]>map){
		return dao.getAll(map);
	}
	
	public List<String> getAllGroupBy(){
		return dao.getAllGroupBy();
	}
	public List<String> getAllByDA_NO$EP_NAME(String ds_no,String ep_name,String ep_size){
		return dao.getAllByDA_NO$EP_NAME(ds_no,ep_name,ep_size);
	}
	
	
	public void addBEquip(EquipVO equipVO,List<EqpicVO> list) {		
		dao.insertWithEpic(equipVO, list);

	}
	
}
