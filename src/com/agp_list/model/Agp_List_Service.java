package com.agp_list.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.act_list.model.Act_List_VO;



public class Agp_List_Service {
	private Agp_List_DAO_interface dao;

	public Agp_List_Service() {
		dao = new Agp_List_DAO();
	}

	public Agp_List_VO addAGP_LIST(String act_list_no, String mem_no, String mem_lic, Integer act_num,
			byte[] mem_lic_pic, String agp_state) {

		Agp_List_VO agp_list_vo = new Agp_List_VO();
		agp_list_vo.setAct_list_no(act_list_no);
		agp_list_vo.setMem_no(mem_no);
		agp_list_vo.setMem_lic(mem_lic);
		agp_list_vo.setAct_num(act_num);
		agp_list_vo.setMem_lic_pic(mem_lic_pic);
		agp_list_vo.setAgp_state(agp_state);
		dao.insert(agp_list_vo);

		return agp_list_vo;
	}

	public Agp_List_VO updateAGP_LIST(String mem_lic, Integer act_num, byte[] mem_lic_pic, String agp_state,
			String act_list_no, String mem_no) {

		Agp_List_VO agp_list_vo = new Agp_List_VO();

		agp_list_vo.setMem_lic(mem_lic);
		agp_list_vo.setAct_num(act_num);
		agp_list_vo.setMem_lic_pic(mem_lic_pic);
		agp_list_vo.setAgp_state(agp_state);
		agp_list_vo.setAct_list_no(act_list_no);
		agp_list_vo.setMem_no(mem_no);
		dao.update(agp_list_vo);

		return agp_list_vo;
	}

	public void deleteAGP_LIST(String act_list_no, String mem_no) {
		dao.delete(act_list_no, mem_no);
	}

	public Agp_List_VO getOneAGP_LIST(String act_list_no, String mem_no) {
		return dao.findByPrimaryKey(act_list_no, mem_no);
	}
	

	
	public List<Agp_List_VO> getAll() {
		return dao.getAll();
	}
	
	public Set<Agp_List_VO> getJoinGroup(String mem_no, String agp_state) {
		return dao.joingroup( mem_no,  agp_state);
	}

	public Set<Agp_List_VO> getMem_State(String agp_state) {
		return dao.findMemState(agp_state);
	}
	
}
