package com.dspic.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestJNDI_Dspic")
public class TestJNDI_Dspic extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/olain; charset=UTF-8");
		DspicVO dspicVO = new DspicVO();
		DspicJDBCDAO dao = new DspicJDBCDAO();
		PrintWriter out = res.getWriter();	
		
		//insert
//		DspicVO dspicVO1 = new DspicVO();
//		dspicVO1.setDs_no("DS0002");
//		dspicVO1.setDpic(null);
//		dspicVO1.setDpic_name("diveshop1");
//		dao.insert(dspicVO1);
		
		//update
//		DspicVO dspicVO2 = new DspicVO();
//		dspicVO2.setDs_no("DS0002");
//		dspicVO2.setDpic(null);
//		dspicVO2.setDpic_name("diveshop1");
//		dspicVO2.setDpic_seq("DSP001");
//		dao.update(dspicVO2);
		
		//delete
//		dao.delete("DSP006");
		
		//查詢圖片(PK)
//		DspicVO dspicVO3 = dao.findByPrimaryKey("DSP005");
//		System.out.print(dspicVO3.getDpic_seq() + ",");
//		System.out.print(dspicVO3.getDs_no() + ",");
//		System.out.print(dspicVO3.getDpic() + ",");
//		System.out.print(dspicVO3.getDpic_name());
//		System.out.println();
		
		//查詢圖片(潛店編號)
//		Set<DspicVO> set = dao.findByDs_no("DS0001");
//		for(DspicVO apic : set) {
//			System.out.print(apic.getDpic_seq() + ",");
//			System.out.print(apic.getDs_no() + ",");
//			System.out.print(apic.getDpic() + ",");
//			System.out.print(apic.getDpic_name());
//			System.out.println();
//		}
		
		//查詢全部圖片
		List<DspicVO> list = dao.getAll();
		for(DspicVO apic : list) {
			out.print(apic.getDpic_seq() + ",");
			out.print(apic.getDs_no() + ",");
			out.print(apic.getDpic() + ",");
			out.println();
		}
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
