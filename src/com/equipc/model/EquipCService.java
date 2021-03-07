package com.equipc.model;
import java.util.*;
public class EquipCService {
	private EquipCDAO_interface dao;
	public EquipCService() {
	  dao = new EquipCDAO();
	}
	
	public EquipCVO addEquipC(String epc_no,String epc_name) {
		EquipCVO equipCVO = new EquipCVO();
		equipCVO.setEpc_no(epc_no);
		equipCVO.setEpc_name(epc_name);
		dao.insert(equipCVO);
		return equipCVO;
	}

	public EquipCVO updateEquipC(String epc_no,String epc_name) {
		EquipCVO equipCVO = new EquipCVO();
		equipCVO.setEpc_name(epc_name);
		equipCVO.setEpc_no(epc_no);
		dao.update(equipCVO);
		return equipCVO;
	}

	public void deleteEquipC(String epc_no) {
		dao.delete(epc_no);
	}

	public EquipCVO getByPK(String epc_no) {
		return dao.findByPrimaryKey(epc_no);
	}
	
	public List<EquipCVO> getAll(){
		return dao.getAll();
	}
	
}
