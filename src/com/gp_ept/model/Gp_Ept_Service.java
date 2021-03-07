package com.gp_ept.model;

import java.sql.Date;
import java.util.List;


public class Gp_Ept_Service {
	private Gp_Ept_DAO_interface dao;

	public Gp_Ept_Service() {
		dao = new Gp_Ept_DAO();
	}

	public Gp_Ept_VO addGP_EPT( String act_list_no, String mem_no, String epc_no, Integer gp_t_num,String gp_t_size) {
		Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();
		gp_ept_vo = new Gp_Ept_VO();
//		gp_ept_vo.setGp_ept_no(gp_ept_no);
		gp_ept_vo.setAct_list_no(act_list_no);
		gp_ept_vo.setMem_no(mem_no);
		gp_ept_vo.setEpc_no(epc_no);
		gp_ept_vo.setGp_t_num(gp_t_num);
		gp_ept_vo.setGp_t_size(gp_t_size);

		dao.insert(gp_ept_vo);
		return gp_ept_vo;
	}

	public Gp_Ept_VO updateGP_EPT(String act_list_no, String mem_no, String epc_no, Integer gp_t_num,String gp_t_size,
			String gp_ept_no) {
		Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();
		gp_ept_vo = new Gp_Ept_VO();
		gp_ept_vo.setAct_list_no(act_list_no);
		gp_ept_vo.setMem_no(mem_no);
		gp_ept_vo.setEpc_no(epc_no);
		gp_ept_vo.setGp_t_num(gp_t_num);
		gp_ept_vo.setGp_t_size(gp_t_size);
		gp_ept_vo.setGp_ept_no(gp_ept_no);

		dao.update(gp_ept_vo);
		return gp_ept_vo;
	}

	public void deleteGP_EPT(String gp_ept_no) {
		dao.delete(gp_ept_no);
	}

	public Gp_Ept_VO getOneGP_EPT(String gp_ept_no) {
		return dao.findByPrimaryKey(gp_ept_no);
	}

	public List<Gp_Ept_VO> getAll() {
		return dao.getAll();
	}
	
    public void insert2 (Gp_Ept_VO gp_ept_vo , java.sql.Connection con) {
    	
		dao.insert2(gp_ept_vo,con);
    	
    }
    
 public void insert3 (List<Gp_Ept_VO> list) {
	
	 Gp_Ept_Service gp_ept_vo = new Gp_Ept_Service();
	 
		for(Gp_Ept_VO gp_list: list) {
			gp_ept_vo.addGP_EPT(gp_list.getAct_list_no(),gp_list.getMem_no(),gp_list.getEpc_no(),gp_list.getGp_t_num(),gp_list.getGp_t_size() );
		
		}
		
	
    }
 public Gp_Ept_VO getFindByMe(String mem_no) {
		return dao.findByMe(mem_no);
	}
	
}
