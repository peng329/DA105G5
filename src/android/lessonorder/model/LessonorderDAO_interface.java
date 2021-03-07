package android.lessonorder.model;

import java.util.List;





public interface LessonorderDAO_interface {
	public boolean isMemOrdered(String les_no,String mem_no);
	public LessonorderVO findByPrimaryKey(String les_o_no);
	public List<LessonorderVO> findByMem_no(String mem_no);
	public List<LessonorderVO> findByLes_no(String les_no);
	public List<LessonorderVO> getAll();
	public boolean insert(LessonorderVO lessonOrderVO);
}
