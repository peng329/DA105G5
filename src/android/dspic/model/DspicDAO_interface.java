package android.dspic.model;

import java.util.*;

public interface DspicDAO_interface {
		
	public DspicVO findByPrimaryKey(String dpic_seq);
	
	public List<DspicVO> findByDs_no(String ds_no);
	
	public List<DspicVO> getAll();
	
	public byte[] getDpic(String ds_no,int position);
	
	public int getDpicCount(String ds_no);
}

