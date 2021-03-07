package android.lesson.model;

import java.util.List;
import java.util.Set;

public interface LessonDAO_interface {
	
    //查詢課程
    public LessonVO findByPrimaryKey(String les_no);
    
    //查詢某教練的課程
    public List<LessonVO> findByCoach(String coach);
    
    //查詢某潛店的課程
    public List<LessonVO> findByShop(String ds_no);
    
    //查詢某課程所在的潛店
    public List<LessonVO> findByLessonName(String les_name);
    
    //所有課程
    public List<LessonVO> getAll();
    
    
}