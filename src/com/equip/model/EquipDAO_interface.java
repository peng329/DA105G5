package com.equip.model;

import java.util.*;

import com.eqpic.model.EqpicVO;

public interface EquipDAO_interface {
          public void insertWithEpic(EquipVO equipVO,List<EqpicVO> list);
          public void update(EquipVO equipVO);
          public void updateByOrder(String ep_seq);
          public void updateByReturn(String ep_seq);
          public void delete(String ep_seq);
          
          public EquipVO findByPrimaryKey(String ep_seq);
          public EquipVO findByDsEpNo(String ds_no,String ep_no);
          
          public List<EquipVO> getAll();
          public List<String> getAllGroupBy();
          public List<String> getAllByDA_NO$EP_NAME(String ds_no,String ep_name,String ep_size);
          public List<EquipVO> getDSAll(String ds_no);
          public List<EquipVO> getDSAllFORSHOP(String ds_no);
          public List<EquipVO> getAll(Map<String, String[]> map); 
}


