package android.rodetail.model;

import java.util.List;

public interface RoDetailDAO_interface {
	 
     public RoDetailVO findByPrimaryKey(String ro_no,String ep_seq);
     public List<RoDetailVO> getSameRoRdAll(String ro_no);

     
}
