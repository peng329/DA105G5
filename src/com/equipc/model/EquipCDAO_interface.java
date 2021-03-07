package com.equipc.model;

import java.util.*;

public interface EquipCDAO_interface {
          public void insert(EquipCVO equipCVO);
          public void update(EquipCVO equipCVO);
          public void delete(String epc_no);
          public EquipCVO findByPrimaryKey(String epc_no);
          public List<EquipCVO> getAll();
        //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EpcVO> getAll(Map<String, String[]> map); 
}
