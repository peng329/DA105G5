package com.webmaster.model;

import java.util.*;
import com.authority_manage.model.Authority_manageVO;
import com.mem.model.MemVO;

public interface WebmasterDAO_interface {
    public String insert(WebmasterVO webmasterVO);
    public void update(WebmasterVO webmasterVO);
    public void delete(String wm_no);
    public WebmasterVO findByPrimaryKey(String wm_no);
    public List<WebmasterVO> getAll();
    
    //查詢某管理員的所有權限（一對多）（回傳Set）
    public Set<Authority_manageVO> getAmsByWm_no(String wm_no);
    
    //用帳號查找一個員工
    public WebmasterVO findByWm_id(String wm_id);

}

