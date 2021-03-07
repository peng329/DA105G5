package com.lespic.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestJNDI_lespic")
public class TestJNDI_lespic extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/olain; charset=UTF-8");
		LespicVO lespic = new LespicVO();
		LespicJNDIDAO dao = new LespicJNDIDAO();
		PrintWriter out = res.getWriter();
		
		//insert
//		LespicVO lespicVO1 = new LespicVO();
//		lespicVO1.setLes_no("LE0006");
//		lespicVO1.setLpic(null);
//		lespicVO1.setLpic_name("lessonpic");
//		dao.insert(lespicVO1);
		
		//update
//		LespicVO lespicVO2 = new LespicVO();
//		lespicVO2.setLes_no("LE0007");
//		lespicVO2.setLpic(null);
//		lespicVO2.setLpic_name("updatename");
//		lespicVO2.setLpic_seq("LSP003");
//		dao.update(lespicVO2);
		
		//delete
//		dao.delete("LSP006");
		
		//查詢圖片(PK)
//		LespicVO lespicVO3 = dao.findByPrimaryKey("LSP004");
//		System.out.print(lespicVO3.getLpic_seq() + ",");
//		System.out.print(lespicVO3.getLes_no() + ",");
//		System.out.print(lespicVO3.getLpic() + ",");
//		System.out.print(lespicVO3.getLpic_name());
//		System.out.println();
		
		//查詢圖片(LES_NO)
//		Set<LespicVO> set = dao.findByLes_no("LE0007");
//		for(LespicVO apic : set) {
//		System.out.print(apic.getLpic_seq() + ",");
//		System.out.print(apic.getLes_no() + ",");
//		System.out.print(apic.getLpic() + ",");
//		System.out.print(apic.getLpic_name());
//		System.out.println();
//		}
		
		//查詢全部圖片
		List<LespicVO> list = dao.getAll();
		for(LespicVO apic : list) {
			out.print(apic.getLpic_seq() + ",");
			out.print(apic.getLes_no() + ",");
			out.print(apic.getLpic() + ",");
			out.print(apic.getLpic_name());
			out.println();
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
