package android.eqpic.model;

import java.util.List;

public interface EqpicDAO_interface {
          public EqpicVO findByPrimaryKey(String epic_seq);
          public List<EqpicVO> findByDsAll(String ds_no);
          public List<EqpicVO> findByAEp_seq_All_Epic(String ep_seq);
          public byte[] getEqpic(String ds_no,String ep_seq);
}