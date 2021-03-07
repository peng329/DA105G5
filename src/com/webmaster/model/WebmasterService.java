package com.webmaster.model;

import java.util.List;
import java.util.Set;

import com.authority_manage.model.Authority_manageService;
import com.authority_manage.model.Authority_manageVO;
import com.mem.model.MemVO;

public class WebmasterService {
	private WebmasterDAO_interface dao;

	public WebmasterService() {
	    dao = new WebmasterDAO();
	}

	public WebmasterVO addWebmaster(String wm_name, Integer wm_sex, String wm_id, 
			String wm_psw, String wm_mail, java.sql.Date ob_date, 
			java.sql.Date dd_date, java.sql.Timestamp reg_time) {
	    WebmasterVO webmasterVO = new WebmasterVO();

	    webmasterVO.setWm_name(wm_name);
	    webmasterVO.setWm_sex(wm_sex);
	    webmasterVO.setWm_id(wm_id);
	    webmasterVO.setWm_psw(wm_psw);
	    webmasterVO.setWm_mail(wm_mail);
	    webmasterVO.setOb_date(ob_date);
	    webmasterVO.setDd_date(dd_date);
	    webmasterVO.setReg_time(reg_time);
	    dao.insert(webmasterVO);

	    return webmasterVO;
	}
	
	//overload，新增員工的同時，新增該員工的權限，需要自增主鍵綁定，
	public WebmasterVO addWebmaster(String wm_name, Integer wm_sex, String wm_id, 
			String wm_psw, String wm_mail, java.sql.Date ob_date, 
			java.sql.Date dd_date, java.sql.Timestamp reg_time, Authority_manageService amSvc, String[] fc_noArray) {
	    WebmasterVO webmasterVO = new WebmasterVO();

	    webmasterVO.setWm_name(wm_name);
	    webmasterVO.setWm_sex(wm_sex);
	    webmasterVO.setWm_id(wm_id);
	    webmasterVO.setWm_psw(wm_psw);
	    webmasterVO.setWm_mail(wm_mail);
	    webmasterVO.setOb_date(ob_date);
	    webmasterVO.setDd_date(dd_date);
	    webmasterVO.setReg_time(reg_time);
	    String wm_no = dao.insert(webmasterVO);
	    
	    //新增該員工權限
	    if(fc_noArray != null) {
		    for(String fc_no :fc_noArray) {
				System.out.println(fc_no);
				if(fc_no != null) {
					amSvc.addAuthority_manage(wm_no, fc_no);
				}
			}
	    }
		
	    

	    return webmasterVO;
	}

	public WebmasterVO updateWebmaster(String wm_no, String wm_name, Integer wm_sex, 
			String wm_id, String wm_psw, String wm_mail, java.sql.Date ob_date, 
			java.sql.Date dd_date, java.sql.Timestamp reg_time) {
	    WebmasterVO webmasterVO = new WebmasterVO();

	    webmasterVO.setWm_no(wm_no);
	    webmasterVO.setWm_name(wm_name);
	    webmasterVO.setWm_sex(wm_sex);
	    webmasterVO.setWm_id(wm_id);
	    webmasterVO.setWm_psw(wm_psw);
	    webmasterVO.setWm_mail(wm_mail);
	    webmasterVO.setOb_date(ob_date);
	    webmasterVO.setDd_date(dd_date);
	    webmasterVO.setReg_time(reg_time);
	    dao.update(webmasterVO);

	    return webmasterVO;
	}

	public void deleteWebmaster(String wm_no) {
	    dao.delete(wm_no);
	}

	public WebmasterVO getOneWebmaster(String wm_no) {
	    return dao.findByPrimaryKey(wm_no);
	}
	
	public WebmasterVO getOneWmById(String wm_id) {
	    return dao.findByWm_id(wm_id);
	}
	
	public Set<Authority_manageVO> getAmsByWm_no(String wm_no) {
		return dao.getAmsByWm_no(wm_no);
	}

	public List<WebmasterVO> getAll(){
	    return dao.getAll();
	}


}
