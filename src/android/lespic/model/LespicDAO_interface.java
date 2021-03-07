package android.lespic.model;



import java.util.*;

public interface LespicDAO_interface {

	public LespicVO findByPrimaryKey(String lpic_seq);

	public List<LespicVO> findByLes_no(String les_no);

	public List<LespicVO> getAll();
	
	public byte[] getLespic(String les_no);
}

