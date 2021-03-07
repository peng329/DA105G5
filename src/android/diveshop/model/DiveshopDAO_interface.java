package android.diveshop.model;

import java.util.List;
import java.util.Set;

import android.dspic.model.DspicVO;

public interface DiveshopDAO_interface {
	
	//新增潛店
//	public void insert(DiveshopVO diveshopVO);
	
	//修改潛店資料
//	public void update(DiveshopVO diveshopVO);
	
	//刪除某間潛店
//	public void delete(String ds_no);
	
	//查詢某間潛店
	public DiveshopVO findByPrimaryKey(String ds_no);
	
	public DiveshopVO findById(String dsaccount);
	
	//查詢所有潛店
	public List<DiveshopVO> getaAll();
	
	//查詢地區所有的潛店
	public Set<DiveshopVO> getShopByAddress(String address);
	
	public boolean isDiveshopMem(String dsaccount,String dspaw);
	//同時新增潛店與潛店照片
//	public void insertWithDspic(DiveshopVO diveshopVO , List<DspicVO> list);
	
	//透過潛店編號取得全部潛店圖片
//	public List<String> getDpic_seqByDs_no(String ds_no);
}

