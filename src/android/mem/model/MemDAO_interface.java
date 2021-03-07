package android.mem.model;

import java.util.*;
import java.util.List;

public interface MemDAO_interface {
	boolean isMember(String mem_id, String mem_psw);
	boolean isMemIdExist(String mem_id);
	boolean add(MemVO memVO);
	boolean update(MemVO memVO);
	boolean delete(String mem_id);
	String findPkById(String mem_id);
	String findNameByPk(String mem_no);
	List<MemVO> getAll();
	MemVO findOneByIdPsw(String mem_id, String mem_psw);
	byte[] findOnePicByPk(String mem_no);
	boolean updatePic(String mem_no,byte[] mem_pic);
}
