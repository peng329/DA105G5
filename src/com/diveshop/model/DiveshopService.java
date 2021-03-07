package com.diveshop.model;

import java.util.List;
import java.util.Set;

import com.dspic.model.DspicVO;

public class DiveshopService {

	private DiveshopDAO_interface dao;
	
	public DiveshopService() {
		dao = new DiveshopDAO();
	}
	
	public void addDiveshopWithPic(DiveshopVO diveshopVO, List<DspicVO> dspicVO) {
			dao.insertWithDspic(diveshopVO, dspicVO);
		}
	
	public DiveshopVO addDiveshop(String ds_name, Integer brcid, String tel, String address, String dsaccount,
			String dspaw, String dsmail, String dsinfo, String ds_latlng, String ds_state, 
			Integer ds_ascore, Integer ds_rep_no) {
		
		DiveshopVO diveshopVO = new DiveshopVO();
		
		diveshopVO.setDs_name(ds_name);
		diveshopVO.setBrcid(brcid);
		diveshopVO.setTel(tel);
		diveshopVO.setAddress(address);
		diveshopVO.setDsaccount(dsaccount);
		diveshopVO.setDspaw(dspaw);
		diveshopVO.setDsmail(dsmail);
		diveshopVO.setDsinfo(dsinfo);
		diveshopVO.setDs_latlng(ds_latlng);
		diveshopVO.setDs_state(ds_state);
		diveshopVO.setDs_ascore(ds_ascore);
		diveshopVO.setDs_rep_no(ds_rep_no);
		dao.insert(diveshopVO);
		
		return diveshopVO;
	}
	
	public DiveshopVO updateDiveshop(String ds_no,String ds_name, Integer brcid, String tel, String address, String dsaccount,
			String dspaw, String dsmail, String dsinfo, String ds_latlng, String ds_state, 
			Integer ds_ascore, Integer ds_rep_no) {
		
		DiveshopVO diveshopVO = new DiveshopVO();
		
		diveshopVO.setDs_no(ds_no);
		diveshopVO.setDs_name(ds_name);
		diveshopVO.setBrcid(brcid);
		diveshopVO.setTel(tel);
		diveshopVO.setAddress(address);
		diveshopVO.setDsaccount(dsaccount);
		diveshopVO.setDspaw(dspaw);
		diveshopVO.setDsmail(dsmail);
		diveshopVO.setDsinfo(dsinfo);
		diveshopVO.setDs_latlng(ds_latlng);
		diveshopVO.setDs_state(ds_state);
		diveshopVO.setDs_ascore(ds_ascore);
		diveshopVO.setDs_rep_no(ds_rep_no);
		
		dao.update(diveshopVO);
		
		return diveshopVO;
	}
	
	public void deleteDiveshop(String ds_no) {
		dao.delete(ds_no);
	}
	
	public DiveshopVO getOneDiveshop(String ds_no) {
		return dao.findByPrimaryKey(ds_no);
	}
	
	public List<DiveshopVO> getAll(){	
		return dao.getAll();
	}
	
	public Set<DiveshopVO> getDiveshopByAddress(String address){
		return dao.getShopByAddress(address);
	}
	
    //用帳號查找一間潛店
    public DiveshopVO findByDsaccount(String dsaccount) {
    	return dao.findByDsaccount(dsaccount);
    }
	

	
	public List<String> getDpic_seqByDs_no(String ds_no){
		return dao.getDpic_seqByDs_no(ds_no);
	}
}
