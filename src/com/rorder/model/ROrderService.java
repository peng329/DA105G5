package com.rorder.model;
import java.util.*;

import com.rodetail.model.RoDetailVO;
public class ROrderService {
	private ROrderDAO_interface dao;
	public ROrderService() {
		dao = new ROrderDAO();
	}
	
	public ROrderVO addROrder(String ds_no,String mem_no,Integer tepc,Integer tpriz,String op_state,java.sql.Date rs_date,java.sql.Date rd_date,java.sql.Date rr_date,Integer ffine,String o_state,String o_note) {
		ROrderVO roVO = new ROrderVO();

		roVO.setDs_no(ds_no);
		roVO.setMem_no(mem_no);
		roVO.setTepc(tepc);
		roVO.setTpriz(tpriz);
		roVO.setRs_date(rs_date);
		roVO.setRd_date(rd_date);
		roVO.setRr_date(rr_date);
		roVO.setFfine(ffine);
		roVO.setO_state(o_state);
		roVO.setO_note(o_note);
		
		dao.insert(roVO);
		return roVO;
	}
	
	public ROrderVO updateByDS(String ro_no,String op_state,java.sql.Date rr_date,Integer ffine,String o_state,String o_note) {
		ROrderVO roVO = new ROrderVO();
		roVO.setRo_no(ro_no);
		roVO.setOp_state(op_state);
		roVO.setRr_date(rr_date);
		roVO.setFfine(ffine);
		roVO.setO_state(o_state);
		roVO.setO_note(o_note);
		dao.updateByDS(roVO);
		return roVO;
	} 
	
	public void updateByMEM(String ro_no) {
		dao.updateByMEM(ro_no);
	} 
	
	public void deleteROrder(String ro_no) {
		dao.delete(ro_no);
	}
	
	public ROrderVO getAMenRO(String mem_no,String ro_no) {
		return dao.findAMemRo(mem_no, ro_no);
	}
	
	public List<ROrderVO> getAMenAllRo(String mem_no){
		return dao.getAllRoByAMem(mem_no);
	}
	
	public List<ROrderVO> getDsAllRo(String ds_no){
		return dao.getAllDsRo(ds_no);
	}
	
    public Set<String> getRR_DATE_IS_NULL(){
    	return dao.getRR_DATE_IS_NULL();
    };
	
	
    public String insertWithRoDetail(ROrderVO rorderVO,List<RoDetailVO> list) {
    	return dao.insertWithRoDetail(rorderVO, list);
    }
}
