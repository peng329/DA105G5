package com.dsrm_record.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestJNDI_dsrm_record")
public class TestJNDI_dsrm_record extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/olain; charset=UTF-8");
		Dsrm_recordVO dsrm_record = new Dsrm_recordVO();
		Dsrm_recordJNDIDAO dao = new Dsrm_recordJNDIDAO();
		PrintWriter out = res.getWriter();
		
		//insert
//		Dsrm_recordVO dsrm_recordVO1 = new Dsrm_recordVO();
//		dsrm_recordVO1.setDs_no("DS0003");
//		dsrm_recordVO1.setMem_no("M000002");
//		dsrm_recordVO1.setRep_time(java.sql.Date.valueOf("2020-5-21"));
//		dsrm_recordVO1.setRep_content("檢舉內容6");
//		dsrm_recordVO1.setRep_state("審核通過");
//		dao.insert(dsrm_recordVO1);
		
		//update
//		Dsrm_recordVO dsrm_recordVO2 = new Dsrm_recordVO();
//		dsrm_recordVO2.setDs_no("DS0005");
//		dsrm_recordVO2.setMem_no("M000002");
//		dsrm_recordVO2.setRep_time(java.sql.Date.valueOf("2020-01-01"));
//		dsrm_recordVO2.setRep_content("updatecontent");
//		dsrm_recordVO2.setRep_state("未審核");
//		dsrm_recordVO2.setRdsr_no("DSR007");
//		dao.update(dsrm_recordVO2);
		
		//delete
//		dao.delete("DSR008");
		
		//查詢紀錄(PK)
//		Dsrm_recordVO dsrm_recordVO3 = dao.findByPrimaryKey("DSR007");
//			System.out.print(dsrm_recordVO3.getRdsr_no() + ",");
//			System.out.print(dsrm_recordVO3.getDs_no() + ",");	
//			System.out.print(dsrm_recordVO3.getMem_no() + ",");	
//			System.out.print(dsrm_recordVO3.getRep_time() + ",");
//			System.out.print(dsrm_recordVO3.getRep_content() + ",");	
//			System.out.print(dsrm_recordVO3.getRep_state() + ",");
//			System.out.println();
		
		//查詢紀錄(潛店)
//		Set<Dsrm_recordVO> set = dao.findByDiveshopNo("DS0001");
//		for(Dsrm_recordVO arecord : set) {
//			System.out.print(arecord.getRdsr_no() + ",");
//			System.out.print(arecord.getDs_no() + ",");	
//			System.out.print(arecord.getMem_no() + ",");	
//			System.out.print(arecord.getRep_time() + ",");
//			System.out.print(arecord.getRep_content() + ",");	
//			System.out.print(arecord.getRep_state() + ",");
//			System.out.println();
//		}
		//查詢紀錄(會員)
//		Set<Dsrm_recordVO> set = dao.findByByMemNo("M000001");
//		for(Dsrm_recordVO arecord : set) {
//			System.out.print(arecord.getRdsr_no() + ",");
//			System.out.print(arecord.getDs_no() + ",");	
//			System.out.print(arecord.getMem_no() + ",");	
//			System.out.print(arecord.getRep_time() + ",");
//			System.out.print(arecord.getRep_content() + ",");	
//			System.out.print(arecord.getRep_state() + ",");
//			System.out.println();
//		}
		
		//全部紀錄
		List<Dsrm_recordVO> list = dao.getAll();
		for(Dsrm_recordVO arecord :list) {
			System.out.print(arecord.getRdsr_no() + ",");
			System.out.print(arecord.getDs_no() + ",");	
			System.out.print(arecord.getMem_no() + ",");	
			System.out.print(arecord.getRep_content() + ",");	
			System.out.print(arecord.getRep_state() + ",");
			System.out.println();
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
