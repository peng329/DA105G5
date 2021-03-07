package com.authority_manage.model;

import java.util.*;

import com.func.model.*;

public class Authority_manageService {
	
	private Authority_manageDAO_interface dao;

	public Authority_manageService() {
	    dao = new Authority_manageDAO();
	}

	public Authority_manageVO addAuthority_manage(String wm_no, String fc_no) {
	    Authority_manageVO authority_manageVO = new Authority_manageVO();
	    
	    authority_manageVO.setWm_no(wm_no);
	    authority_manageVO.setFc_no(fc_no);
	    dao.insert(authority_manageVO);

	    return authority_manageVO;
	}


	public void deleteAuthority_manage(String wm_no, String fc_no) {
	    dao.delete(wm_no, fc_no);
	}

	public Authority_manageVO getOneAuthority_manage(String wm_no, String fc_no) {
	    return dao.findByPrimaryKey(wm_no, fc_no);
	}

	public List<Authority_manageVO> getAll(){
	    return dao.getAll();
	}
	
	//查詢一位員工所有權限表格VO
    public List<Authority_manageVO> getAuthority_manageVOsByWm_no(String wm_no){
    	return dao.getAuthority_manageVOsByWm_no(wm_no);
    }
    
    //員工的所有權限表，和權限的所有權限對照，形成一個列表，員工沒有的放null
//    public List<Authority_manageVO> getAuthority_manageVOsByContrast(String wm_no){
//    	List<Authority_manageVO> contrastList = new ArrayList<Authority_manageVO>();
//    	List<Authority_manageVO> listAllWm = dao.getAuthority_manageVOsByWm_no(wm_no);
//    	
//    	FuncService funcSvc = new FuncService();
//    	List<FuncVO> listAll = funcSvc.getAll();
//    	
//    	for(FuncVO funcVO : listAll) {
//    		int checki = 0;
//    		for(Authority_manageVO authority_manageVO : listAllWm) {
//    			
//    			if(authority_manageVO.getFc_no().equals(funcVO.getFc_no())) {
//    				contrastList.add(authority_manageVO);
//    				checki = 1;
//    				break;
//    			}
//    		}
//    		if(checki == 0) {
//    			contrastList.add(null);
//    		}
//    	}
//    	return contrastList;
//    }
    
  //員工的所有權限表，和權限的所有權限對照，形成一個列表，用陣列存，陣列第一欄存0或1，第2欄存fc_np，第三欄存fc_name
    public List<String[]> getAuthority_manageVOsByContrast(String wm_no){
    	List<String[]> contrastList = new ArrayList<String[]>();
    	List<Authority_manageVO> listAllWm = dao.getAuthority_manageVOsByWm_no(wm_no);
    	
    	FuncService funcSvc = new FuncService();
    	List<FuncVO> listAll = funcSvc.getAll();
    	
    	//String[] stateIfo = new String[3];
    	
    	for(int i = 0; i < listAll.size();i++) {
    		int checki = 0;
    		String[] stateIfo = new String[3];
    		for(Authority_manageVO authority_manageVO : listAllWm) {
    			
    			if(authority_manageVO.getFc_no().equals(listAll.get(i).getFc_no())) {
    				
    				stateIfo[0] = "1";
    				stateIfo[1] = listAll.get(i).getFc_no();
    				stateIfo[2] = listAll.get(i).getFc_name();
    				
    				contrastList.add(stateIfo);
    			
    				checki = 1;
    				break;
    			}
    		}
    		if(checki == 0) {
    			
				stateIfo[0] = "0";
				stateIfo[1] = listAll.get(i).getFc_no();
				stateIfo[2] = listAll.get(i).getFc_name();
				
    			contrastList.add(stateIfo);
    		}
    	}
    	return contrastList;
    }
    
    //員工的所有權限表的所有權限編號
    public List<String> getFc_noByWm(String wm_no){
    	Authority_manageService amSvc = new Authority_manageService();
    	List<Authority_manageVO> amVOList = amSvc.getAuthority_manageVOsByWm_no(wm_no);
    	List<String> fc_noList = new ArrayList();
    	
    	for (Authority_manageVO authority_manageVO : amVOList) {
    		fc_noList.add(authority_manageVO.getFc_no());
		}
    	return fc_noList;
    }
    
    
    public static void main(String[] args) {
    	Authority_manageService authority_manageService = new Authority_manageService();
    	List<String[]> list = authority_manageService.getAuthority_manageVOsByContrast("A01");
    	for(String[] s:list) {
    		System.out.print(s[0] + ",");
    		System.out.print(s[1] + ",");
    		System.out.print(s[2]);
    		System.out.println();
    	}
    	
	}
}
