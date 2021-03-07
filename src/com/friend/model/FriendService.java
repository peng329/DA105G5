package com.friend.model;

import java.util.*;

public class FriendService {
	private FriendDAO_interface dao;

	public FriendService() {
	    dao = new FriendDAO();
	}

	public FriendVO addFriend(String mem_no_a, String mem_no_b, String fri_state) {
	    FriendVO friendVO = new FriendVO();
	    
	    friendVO.setMem_no_a(mem_no_a);
	    friendVO.setMem_no_b(mem_no_b);
	    friendVO.setFri_state(fri_state);
	    dao.insert(friendVO);

	    return friendVO;
	}

	public FriendVO updateFriend(String mem_no_a, String mem_no_b, String fri_state) {
	    FriendVO friendVO = new FriendVO();

	    friendVO.setMem_no_a(mem_no_a);
	    friendVO.setMem_no_b(mem_no_b);
	    friendVO.setFri_state(fri_state);
	    dao.update(friendVO);

	    return friendVO;
	}

	public void deleteFriend(String mem_no_a, String mem_no_b) {
	    dao.delete(mem_no_a, mem_no_b);
	}

	public FriendVO getOneFriend(String mem_no_a, String mem_no_b) {
	    return dao.findByPrimaryKey(mem_no_a, mem_no_b);
	}
	
	
	public List<FriendVO> getFriendsByMem_no(String mem_no_a){
	    return dao.getFriendsByMem_no(mem_no_a);
	}

	public List<FriendVO> getAll(){
	    return dao.getAll();
	}
	
	//找出一位會員的所有好友編號，好友狀態關係通過的
	public List<String> getFriendsMem_NoByMem_no(String mem_no){
		List<FriendVO> list = dao.getFriendsByMem_no(mem_no);
		
		List<String> friendMem_no = new ArrayList<String>();
		
		for(FriendVO friendVO : list) {
			if(mem_no.equals(friendVO.getMem_no_a())) {
				friendMem_no.add(friendVO.getMem_no_b());
			}else {
				friendMem_no.add(friendVO.getMem_no_a());
			}
		}
		
		return friendMem_no;
	}
	
	
	//查詢一位會員所有被加好友(自己是會員B時)，狀態還不是通過的好友表格VO
	public List<FriendVO> getMem_no_bUnconfirm(String mem_no_b){
		return dao.getMem_no_bUnOk(mem_no_b);				
	}
}
