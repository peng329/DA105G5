package com.rorder.model;

import java.util.*;
import com.rodetail.model.*;
public interface ROrderDAO_interface {
          public void insert(ROrderVO roVO);
          public void updateByDS(ROrderVO roVO);
          public void updateByMEM(String ro_no);
          public void delete(String ro_no);
          public ROrderVO findAMemRo(String mem_no,String ro_no);
          public List<ROrderVO> getAllRoByAMem(String mem_no);
          public List<ROrderVO> getAllDsRo(String ds_no);
          public Set<String> getRR_DATE_IS_NULL();
          public String insertWithRoDetail(ROrderVO rorderVO,List<RoDetailVO> list);
          
          
        //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EpcVO> getAll(Map<String, String[]> map); 
}
