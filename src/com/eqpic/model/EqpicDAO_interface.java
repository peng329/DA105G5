package com.eqpic.model;

import java.util.*;

public interface EqpicDAO_interface {
          public void insert(EqpicVO eqpicVO);
          public void update(EqpicVO eqpicVO);
          public void delete(String epic_seq);
          public EqpicVO findByPrimaryKey(String epic_seq);
          public void insertByEquip(EqpicVO eqpicVO,java.sql.Connection con);
          public List<EqpicVO> findByDsAll(String ds_no);
          public List<EqpicVO> findByAEp_seq_All_Epic(String ep_seq);
          public List<String> AEp_seq_All_Epic_seq(String ep_seq);
//public List<String> find_EP_SEQ_ByDS_NO(String ds_no);
/*SELECT DISTINCT EP_SEQ FROM EQPIC WHERE DS_NO ='DS0001' ORDER BY EP_SEQ; */

        
//萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EpcVO> getAll(Map<String, String[]> map); 
}
