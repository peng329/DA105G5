package com.lesson.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestJNDI_Lesson")
public class TestJNDI_Lesson extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/olain; charset=UTF-8");
		LessonVO lessonVO = new LessonVO();
		LessonJDBCDAO dao = new LessonJDBCDAO();
		PrintWriter out = res.getWriter();
		
		// insert
//		LessonVO lessonVO1 = new LessonVO();
//		lessonVO1.setDs_no("DS0002");
//		lessonVO1.setDs_name("testname");
//		lessonVO1.setLes_name("testlesson");
//		lessonVO1.setLes_info("testinfo");
//		lessonVO1.setCoach("Tony");
//		lessonVO1.setCost(500);
//		lessonVO1.setDays(7);
//		lessonVO1.setSignup_startdate(new Date(2020-01-01));
//		lessonVO1.setSignup_enddate(new Date(2020-01-05));
//		//new Date(System.currentTimeMillis()); 當前時間
//		lessonVO1.setLes_state("報名中");
//		lessonVO1.setLes_max(4);
//		lessonVO1.setLes_limit(2);
//		lessonVO1.setLes_startdate(new Date(2020-01-07));
//		lessonVO1.setLes_enddate(new Date(2020-01-14));
//		lessonVO1.setLess_state("OPEN");
//		dao.insert(lessonVO1);

		// update
//		LessonVO lessonVO2 = new LessonVO();
//		
//		lessonVO2.setLes_name("update");
//		lessonVO2.setLes_info("testinfo");
//		lessonVO2.setCoach("Tony2");
//		lessonVO2.setCost(50);
//		lessonVO2.setDays(7);
//		lessonVO2.setSignup_startdate(java.sql.Date.valueOf("2020-01-01"));
//		lessonVO2.setSignup_enddate(java.sql.Date.valueOf("2020-01-10"));
//		lessonVO2.setLes_state("報名中");
//		lessonVO2.setLes_max(4);
//		lessonVO2.setLes_limit(2);
//		lessonVO2.setLes_startdate(java.sql.Date.valueOf("2020-02-10"));
//		lessonVO2.setLes_enddate(java.sql.Date.valueOf("2020-02-15"));
//		lessonVO2.setLess_state("OPEN");
//		lessonVO2.setLes_no("LE0012");
//		lessonVO2.setDs_name("testname");
//		dao.update(lessonVO2);

		// delete
//		dao.delete("LE0012");

		// 查詢課程(PK)
//		LessonVO lessonVO3 = dao.findByPrimaryKey("LE0001");
//		System.out.print(lessonVO3.getLes_name() + ",");
//		System.out.print(lessonVO3.getDs_no() + ",");
//		System.out.print(lessonVO3.getDs_name() + ",");
//		System.out.print(lessonVO3.getLes_info() + ",");
//		System.out.print(lessonVO3.getCoach() + ",");
//		System.out.print(lessonVO3.getCost() + ",");
//		System.out.print(lessonVO3.getDays() + ",");
//		System.out.print(lessonVO3.getSignup_startdate() + ",");
//		System.out.print(lessonVO3.getSignup_enddate() + ",");
//		System.out.print(lessonVO3.getLes_name() + ",");
//		System.out.print(lessonVO3.getLes_state() + ",");
//		System.out.print(lessonVO3.getLes_max() + ",");
//		System.out.print(lessonVO3.getLes_limit() + ",");
//		System.out.print(lessonVO3.getLes_startdate() + ",");
//		System.out.print(lessonVO3.getLes_enddate() + ",");
//		System.out.print(lessonVO3.getLess_state());
//		System.out.println();

		// 查詢課程(教練)
//		Set<LessonVO> set = dao.findByCoach("DAVID");
//		for(LessonVO ales : set) {
//		System.out.print(ales.getLes_no() + ",");
//		System.out.print(ales.getLes_name() + ",");
//		System.out.print(ales.getDs_no() + ",");
//		System.out.print(ales.getDs_name() + ",");
//		System.out.print(ales.getLes_info() + ",");
//		System.out.print(ales.getCoach() + ",");
//		System.out.print(ales.getCost() + ",");
//		System.out.print(ales.getDays() + ",");
//		System.out.print(ales.getSignup_startdate() + ",");
//		System.out.print(ales.getSignup_enddate() + ",");
//		System.out.print(ales.getLes_state() + ",");
//		System.out.print(ales.getLes_max() + ",");
//		System.out.print(ales.getLes_limit() + ",");
//		System.out.print(ales.getLes_startdate() + ",");
//		System.out.print(ales.getLes_enddate() + ",");
//		System.out.print(ales.getLess_state());
//		System.out.println();
//		}

		// 查詢課程(潛店)
//		Set<LessonVO> set = dao.findByShop("潛水貨艙");
//		for (LessonVO ales : set) {
//			System.out.print(ales.getLes_no() + ",");
//			System.out.print(ales.getLes_name() + ",");
//			System.out.print(ales.getDs_no() + ",");
//			System.out.print(ales.getDs_name() + ",");
//			System.out.print(ales.getLes_info() + ",");
//			System.out.print(ales.getCoach() + ",");
//			System.out.print(ales.getCost() + ",");
//			System.out.print(ales.getDays() + ",");
//			System.out.print(ales.getSignup_startdate() + ",");
//			System.out.print(ales.getSignup_enddate() + ",");
//			System.out.print(ales.getLes_state() + ",");
//			System.out.print(ales.getLes_max() + ",");
//			System.out.print(ales.getLes_limit() + ",");
//			System.out.print(ales.getLes_startdate() + ",");
//			System.out.print(ales.getLes_enddate() + ",");
//			System.out.print(ales.getLess_state());
//			System.out.println();
//		}
		// 查詢課程(課程名稱)
//		Set<LessonVO> set = dao.findByLessonName("OW");
//		for (LessonVO ales : set) {
//			System.out.print(ales.getLes_no() + ",");
//			System.out.print(ales.getLes_name() + ",");
//			System.out.print(ales.getDs_no() + ",");
//			System.out.print(ales.getDs_name() + ",");
//			System.out.print(ales.getLes_info() + ",");
//			System.out.print(ales.getCoach() + ",");
//			System.out.print(ales.getCost() + ",");
//			System.out.print(ales.getDays() + ",");
//			System.out.print(ales.getSignup_startdate() + ",");
//			System.out.print(ales.getSignup_enddate() + ",");
//			System.out.print(ales.getLes_state() + ",");
//			System.out.print(ales.getLes_max() + ",");
//			System.out.print(ales.getLes_limit() + ",");
//			System.out.print(ales.getLes_startdate() + ",");
//			System.out.print(ales.getLes_enddate() + ",");
//			System.out.print(ales.getLess_state());
//			System.out.println();
//		}
		//查詢所有課程
		List<LessonVO> list = dao.getAll();
		for(LessonVO all : list) {
			out.print(all.getLes_no() + ",");
			out.print(all.getLes_name() + ",");
			out.print(all.getDs_no() + ",");
			out.print(all.getDs_name() + ",");
			out.print(all.getLes_info() + ",");
			out.print(all.getCoach() + ",");
			out.print(all.getCost() + ",");
			out.print(all.getDays() + ",");
			out.print(all.getSignup_startdate() + ",");
			out.print(all.getSignup_enddate() + ",");
			out.print(all.getLes_state() + ",");
			out.print(all.getLes_max() + ",");
			out.print(all.getLes_limit() + ",");
			out.print(all.getLes_startdate() + ",");
			out.print(all.getLes_enddate() + ",");
			out.print(all.getLess_state());
			out.println();
		}
	}
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
