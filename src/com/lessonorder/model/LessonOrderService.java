package com.lessonorder.model;

import java.util.List;
import java.util.Set;

public class LessonOrderService {
	
	private LessonOrderDAO_interface dao;
	
	public LessonOrderService() {
		dao = new LessonOrderDAO();
	}
	
	public LessonOrderVO addLessonOrder(String mem_no, String les_no, String ds_no, String mem_name, String les_name,
			Integer cost, String coach, String lo_state) {
		
		LessonOrderVO lessonOrderVO = new LessonOrderVO();
		lessonOrderVO.setMem_no(mem_no);
		lessonOrderVO.setLes_no(les_no);
		lessonOrderVO.setDs_no(ds_no);
		lessonOrderVO.setMem_name(mem_name);
		lessonOrderVO.setLes_name(les_name);
		lessonOrderVO.setCost(cost);
		lessonOrderVO.setCoach(coach);
		lessonOrderVO.setLo_state(lo_state);
		dao.insert(lessonOrderVO);
		
		return lessonOrderVO;
	}
	
	public LessonOrderVO updateLessonOrder(String les_o_no,String mem_no, String les_no, String ds_no, String mem_name, String les_name,
			Integer cost, String coach, String lo_state) {
		
		LessonOrderVO lessonOrderVO = new LessonOrderVO();
		lessonOrderVO.setLes_o_no(les_o_no);
		lessonOrderVO.setMem_no(mem_no);
		lessonOrderVO.setLes_no(les_no);
		lessonOrderVO.setDs_no(ds_no);
		lessonOrderVO.setMem_name(mem_name);
		lessonOrderVO.setLes_name(les_name);
		lessonOrderVO.setCost(cost);
		lessonOrderVO.setCoach(coach);
		lessonOrderVO.setLo_state(lo_state);
		dao.update(lessonOrderVO);
		
		return lessonOrderVO;
	}
	
	public void deleteLessonOrder(String les_o_no) {
		dao.delete(les_o_no);
	}
	
	public LessonOrderVO getOneLessonOrder(String les_o_no) {
		return dao.findByPrimaryKey(les_o_no);
	}
	
	public List<LessonOrderVO> getLessonOrderByMemno(String mem_no) {
		return dao.findByMem_no(mem_no);
	}
	
	public List<LessonOrderVO> getLessonOrderByLesno(String les_no){
		return dao.findByLes_no(les_no);
	}
	
	public List<LessonOrderVO> getLessonOrderByDsno(String ds_no){
		return dao.findByDs_no(ds_no);
	}
	
	public List<String> getMem_noByLes_no(String les_no){
		return dao.findmem_noByLes_no(les_no);
	}
	
	public List<LessonOrderVO> getAll(){
		return dao.getAll();
	}
}
