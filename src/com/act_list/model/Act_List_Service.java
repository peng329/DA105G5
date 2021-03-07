package com.act_list.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.gp_ept.model.Gp_Ept_VO;


public class Act_List_Service {
	private Act_List_DAO_interface dao;

	public Act_List_Service() {
//		dao = new Act_List_JDBCDAO();
		dao = new Act_List_DAO();
	}

	public Act_List_VO addACT_LIST( String mem_no, String dp_no, Date start_date, Date dual_date,
			Date action_date, String act_state, String locale, Integer act_max, Integer act_min, byte[] gp_pic,
			String gp_info, Integer gp_days,String act_list_name) {

		Act_List_VO act_list_vo = new Act_List_VO();

//		act_list_vo.setAct_list_no(act_list_no);
		act_list_vo.setMem_no(mem_no);
		act_list_vo.setDp_no(dp_no);
		act_list_vo.setStart_date(start_date);
		act_list_vo.setDual_date(dual_date);
		act_list_vo.setAction_date(action_date);
		act_list_vo.setAct_state(act_state);
		act_list_vo.setLocale(locale);
		act_list_vo.setAct_max(act_max);
		act_list_vo.setAct_min(act_min);
		act_list_vo.setGp_pic(gp_pic);
		act_list_vo.setGp_info(gp_info);
		act_list_vo.setGp_days(gp_days);
		act_list_vo.setAct_list_name(act_list_name);
		dao.insert(act_list_vo);

		return act_list_vo;
	}

	public Act_List_VO updateACT_LIST(String mem_no, String dp_no, Date start_date, Date dual_date, Date action_date,
			String act_state, String locale, Integer act_max, Integer act_min, byte[] gp_pic, String gp_info,
			Integer gp_days, String act_list_name, String act_list_no) {
		Act_List_VO act_list_vo = new Act_List_VO();

		act_list_vo.setMem_no(mem_no);
		act_list_vo.setDp_no(dp_no);
		act_list_vo.setStart_date(start_date);
		act_list_vo.setDual_date(dual_date);
		act_list_vo.setAction_date(action_date);
		act_list_vo.setAct_state(act_state);
		act_list_vo.setLocale(locale);
		act_list_vo.setAct_max(act_max);
		act_list_vo.setAct_min(act_min);
		act_list_vo.setGp_pic(gp_pic);
		act_list_vo.setGp_info(gp_info);
		act_list_vo.setGp_days(gp_days);
		act_list_vo.setAct_list_name(act_list_name);
		act_list_vo.setAct_list_no(act_list_no);
		dao.update(act_list_vo);

		return act_list_vo;
	}

	public void deleteACT_LIST(String act_list_no) {
		dao.delete(act_list_no);
	}

	public Act_List_VO getOneACT_LIST(String act_list_no) {
		return dao.findByPrimaryKey(act_list_no);
	}

	public List<Act_List_VO> getAll() {
		return dao.getAll();
	}

	public void insertWithGpEpt(Act_List_VO act_list_vo , List<Gp_Ept_VO> list) {
		
		dao.insertWithGpEpt(act_list_vo,list);
		
	}
	
    public Set<Gp_Ept_VO> getGpEptByActListNo(String act_list_no){
    	
    	return dao.getGpEptByActListNo(act_list_no);
    	
    }

    public Set<Act_List_VO> getFreeGroup(String mem_no, String act_state) {
		return dao.freegroup( mem_no,  act_state);
	}
    
    public Set<Act_List_VO> getGroup(String mem_no) {
    	return dao.group(mem_no);
    }
    
}
