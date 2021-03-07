package android.divepoint.model;

import java.util.List;
import java.util.Set;



public interface DivepointDAO_interface {
    public void insert(DivepointVO divepointVO);
    public void update(DivepointVO divepointVO);
    public void delete(String dp_no);
    public DivepointVO findByPrimaryKey(String dp_no);
    public List<DivepointVO> getAll();
    public byte[] getOnePic(String dp_no);
    public List<byte[]> getAllPic(String dp_no);
    
    
    //查詢某地區的潛點(一對多)(回傳 Set)

}
