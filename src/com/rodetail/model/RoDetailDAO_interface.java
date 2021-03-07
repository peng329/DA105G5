package com.rodetail.model;
import java.util.*;
import com.rorder.model.*;
import com.equip.model.*;
public interface RoDetailDAO_interface {
          public void insert(RoDetailVO rdVO);
          public void update(RoDetailVO rdVO);
          public void delete(String ro_no,String ep_seq);
          public RoDetailVO findByPrimaryKey(String ro_no,String ep_seq);
          public List<RoDetailVO> getSameRoRdAll(String ro_no);

          public void insertByROrder(RoDetailVO rodetailVO,java.sql.Connection con);
          
        //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EpcVO> getAll(Map<String, String[]> map); 
}
