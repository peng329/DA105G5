package com.friend.model;

import java.util.List;


public interface FriendDAO_interface {
    public void insert(FriendVO friendVO);
    public void update(FriendVO friendVO);
    public void delete(String mem_no_a, String mem_no_b);
    
    //查詢某筆資料
    public FriendVO findByPrimaryKey(String mem_no_a, String mem_no_b);
    
    //查詢一位會員所有好友表格VO,狀態已經通過
    public List<FriendVO> getFriendsByMem_no(String mem_no_a);
    
    //查詢一位會員所有被加好友(自己是會員B時)，狀態還不是通過的好友表格VO
    public List<FriendVO> getMem_no_bUnOk(String mem_no_b);
    
    public List<FriendVO> getAll();

}
