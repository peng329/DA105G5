package com.lesson.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lespic.model.LespicVO;

public class LessonService {
	
	private LessonDAO_interface dao;
	
	public LessonService() {
		dao = new LessonDAO();
	}
	
	public void addLessonWithPic(LessonVO lessonVO, List<LespicVO> lespicVO) {
		dao.insertWithLespic(lessonVO, lespicVO);
	}
	
	public LessonVO addLesson(String ds_no, String ds_name, String les_name, String les_info, String coach,
			Integer cost, Integer days, java.sql.Date signup_startdate, java.sql.Date signup_enddate,
			String les_state, Integer les_max, Integer les_limit, java.sql.Date les_startdate, java.sql.Date les_enddate,
			String less_state) {
		
		LessonVO lessonVO = new LessonVO();
		lessonVO.setDs_no(ds_no);
		lessonVO.setDs_name(ds_name);
		lessonVO.setLes_name(les_name);
		lessonVO.setLes_info(les_info);
		lessonVO.setCoach(coach);
		lessonVO.setCost(cost);
		lessonVO.setDays(days);
		lessonVO.setSignup_startdate(signup_startdate);
		lessonVO.setSignup_enddate(signup_enddate);
		lessonVO.setLes_state(les_state);
		lessonVO.setLes_max(les_max);
		lessonVO.setLes_limit(les_limit);
		lessonVO.setLes_startdate(les_startdate);
		lessonVO.setLes_enddate(les_enddate);
		lessonVO.setLess_state(less_state);
		dao.insert(lessonVO);
		
		return lessonVO;
	}
	
	public LessonVO updateLesson(String les_no,String ds_no, String ds_name, String les_name, String les_info, String coach,
			Integer cost, Integer days, java.sql.Date signup_startdate, java.sql.Date signup_enddate,
			String les_state, Integer les_max, Integer les_limit, java.sql.Date les_startdate, java.sql.Date les_enddate,
			String less_state) {
		
		LessonVO lessonVO = new LessonVO();
		lessonVO.setLes_name(les_name);
		lessonVO.setLes_info(les_info);
		lessonVO.setCoach(coach);
		lessonVO.setCost(cost);
		lessonVO.setDays(days);
		lessonVO.setSignup_startdate(signup_startdate);
		lessonVO.setSignup_enddate(signup_enddate);
		lessonVO.setLes_state(les_state);
		lessonVO.setLes_max(les_max);
		lessonVO.setLes_limit(les_limit);
		lessonVO.setLes_startdate(les_startdate);
		lessonVO.setLes_enddate(les_enddate);
		lessonVO.setLess_state(less_state);
		lessonVO.setLes_no(les_no);
		lessonVO.setDs_name(ds_name);
		lessonVO.setDs_no(ds_no);
		dao.update(lessonVO);
		return lessonVO;
	}
	
	public void deleteLesson(String les_no) {
		dao.delete(les_no);
	}
	
	public LessonVO getOneLesson(String les_no) {
		return dao.findByPrimaryKey(les_no);
	}
	
	public Set<LessonVO> getLessonByCoach(String coach){
		return dao.findByCoach(coach);
	}
	
	public List<LessonVO> getLessonByDs_no(String ds_no){
		return dao.findByShop(ds_no);
	}
	
	public Set<LessonVO> getLessonByLessonName(String les_name){
		return dao.findByLessonName(les_name);
	}
	
	public List<LessonVO> getAll(){
		return dao.getAll();
	}
	
	public List<LessonVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<String> getlespic_seqByLess_no (String les_no){
		
		return dao.getlespic_seqByLess_no(les_no);
	}
}
