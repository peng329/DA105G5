package android.rorder.model;

import java.util.*;



public interface ROrderDAO_interface {
	      public boolean update(ROrderVO rorderVO);
          public boolean delete(String ro_no);
          public ROrderVO findAMemRo(String mem_no,String ro_no);
          public List<ROrderVO> getAllRoByAMem(String mem_no);
          public List<ROrderVO> getAllDsRo(String ds_no);
          public ROrderVO findOneRo(String ro_no);
}