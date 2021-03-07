package com.mem.model;

import java.util.List;

import com.friend.model.FriendVO;

public interface MemDAO_interface {
    public void insert(MemVO memVO);
    public void update(MemVO memVO);
    public void delete(String mem_no);
    public MemVO findByPrimaryKey(String mem_no);
    public List<MemVO> getAll();
    
    //用帳號查找一個會員
    public MemVO findByMem_id(String mem_id);
    
    //會員狀態更改
    public void updateMemState(String mem_id, String stateNum);
    
    //找出一個會員的好友，回傳list的<MemVO>，並因應好友狀態會員
//    public List<MemVO> getFriendsByMem_no(String mem_no, String fri_state);
    
}
