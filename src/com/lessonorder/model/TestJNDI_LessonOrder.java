package com.lessonorder.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestJNDI_LessonOrder")
public class TestJNDI_LessonOrder extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		LessonOrderVO lessonOrderVO = new LessonOrderVO();
		LessonOrderJDBCDAO dao = new LessonOrderJDBCDAO();
		PrintWriter out = res.getWriter();
		
		//insert
//		LessonOrderVO lessonOrderVO1 = new LessonOrderVO();
//		lessonOrderVO1.setMem_no("M000002");
//		lessonOrderVO1.setLes_no("LE0005");
//		lessonOrderVO1.setMem_name("seafood");
//		lessonOrderVO1.setLes_name("潛水長");
//		lessonOrderVO1.setCost(2000);
//		lessonOrderVO1.setCoach("DAVID");
//		lessonOrderVO1.setLo_state("未付款");
//		dao.insert(lessonOrderVO1);
		
		//update
//		LessonOrderVO lessonOrderVO2 = new LessonOrderVO();
//		lessonOrderVO2.setMem_no("M000002");
//		lessonOrderVO2.setLes_no("LE0005");
//		lessonOrderVO2.setMem_name("seafood");
//		lessonOrderVO2.setLes_name("潛水長");
//		lessonOrderVO2.setCost(200000);
//		lessonOrderVO2.setCoach("DAVID2");
//		lessonOrderVO2.setLo_state("未付款");
//		lessonOrderVO2.setLes_o_no("L20200125-004");
//		dao.update(lessonOrderVO2);
		
		//delete
//		dao.delete("L20200125-004");
		
		//查詢訂單(PK)
//		LessonOrderVO lessonOrderVO3 = dao.findByPrimaryKey("L20200125-002");
//		System.out.print(lessonOrderVO3.getLes_o_no() + ",");
//		System.out.print(lessonOrderVO3.getMem_no() + ",");
//		System.out.print(lessonOrderVO3.getLes_no() + ",");
//		System.out.print(lessonOrderVO3.getMem_name() + ",");
//		System.out.print(lessonOrderVO3.getLes_name() + ",");
//		System.out.print(lessonOrderVO3.getCost() + ",");
//		System.out.print(lessonOrderVO3.getCoach() + ",");
//		System.out.print(lessonOrderVO3.getLo_state());
//		System.out.println();
		
		//查詢訂單(會員編號)
//		Set<LessonOrderVO> set = dao.findByMem_no("M000001");
//		for(LessonOrderVO aorder : set) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
		
		//查詢訂單(課程編號)
//		Set<LessonOrderVO> set = dao.findByLes_no("LE0003");
//		for(LessonOrderVO aorder : set) {
//			System.out.print(aorder.getLes_o_no() + ",");
//			System.out.print(aorder.getMem_no() + ",");
//			System.out.print(aorder.getLes_no() + ",");
//			System.out.print(aorder.getMem_name() + ",");
//			System.out.print(aorder.getLes_name() + ",");
//			System.out.print(aorder.getCost() + ",");
//			System.out.print(aorder.getCoach() + ",");
//			System.out.print(aorder.getLo_state());
//			System.out.println();
//		}
		
		//查詢所有訂單
		List<LessonOrderVO> list = dao.getAll();
		for(LessonOrderVO aorder : list) {
			out.print(aorder.getLes_o_no() + ",");
			out.print(aorder.getMem_no() + ",");
			out.print(aorder.getLes_no() + ",");
			out.print(aorder.getMem_name() + ",");
			out.print(aorder.getLes_name() + ",");
			out.print(aorder.getCost() + ",");
			out.print(aorder.getCoach() + ",");
			out.print(aorder.getLo_state());
			out.println();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
