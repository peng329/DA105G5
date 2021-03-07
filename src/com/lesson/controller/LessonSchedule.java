package com.lesson.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.lesson.model.LessonJDBCDAO;
import com.lesson.model.LessonService;
import com.lesson.model.LessonVO;

public class LessonSchedule {

	
public static void load() {
		
		LessonJDBCDAO dao = new LessonJDBCDAO();
		LessonService lessonSvc = new LessonService();
		List<LessonVO> lessonlist = null;
		Date date = new Date(System.currentTimeMillis());
		long time = date.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = formatter.format(time);
		java.sql.Date now = Date.valueOf(dateTime);
		lessonlist = lessonSvc.getAll();
		for(LessonVO alesson : lessonlist) {
			if(!alesson.getSignup_enddate().after(now)) {
				alesson.setLes_state("結束報名");
			System.out.print(alesson.getLes_no()+",");
			System.out.print(alesson.getSignup_enddate()+",");
			System.out.println(alesson.getLes_state());
				dao.update(alesson);
			}
			if(!alesson.getLes_enddate().after(now)) {
				alesson.setLess_state("下架");
				System.out.print(alesson.getLes_enddate()+",");
				System.out.println(alesson.getLess_state());
				dao.update(alesson);
			}
		}
		
		
				  
				  
//				  
				  
	}

}
