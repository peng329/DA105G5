package com.authority_manage.model;

import java.util.List;

import com.friend.model.FriendVO;


public interface Authority_manageDAO_interface {
    public void insert(Authority_manageVO authority_manageVO);
    public void delete(String wm_no, String fc_no);
    
    //沒有更新方法，因為新增與移除來對管理員做權限的修改
    
    public Authority_manageVO findByPrimaryKey(String wm_no, String fc_no);
    public List<Authority_manageVO> getAll();
    
    //查詢一位員工所有權限表格VO
    public List<Authority_manageVO> getAuthority_manageVOsByWm_no(String wm_no);
}
