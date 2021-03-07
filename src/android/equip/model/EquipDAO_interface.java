package android.equip.model;



import java.util.*;



public interface EquipDAO_interface {
       public EquipVO findByPrimaryKey(String ep_seq);
       public EquipVO findByDsEpNo(String ds_no,String ep_no);
          
     
}



