package com.diveshop.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestDiveshopJNDI")
public class TestJNDI_diveshop extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("charset=UTF-8");
		DiveshopVO diveshopVO = new DiveshopVO();
		DiveshopJNDIDAO dao = new DiveshopJNDIDAO();
		PrintWriter out = res.getWriter();
		
		// insert
//		DiveshopVO diveshopVO1 = new DiveshopVO();
//		diveshopVO1.setDs_name("testshop");
//		diveshopVO1.setBrcid(12345678);
//		diveshopVO1.setTel("03918743");
//		diveshopVO1.setAddress("testAddress");
//		diveshopVO1.setDsaccount("testaccount");
//		diveshopVO1.setDspaw("testpassword");
//		diveshopVO1.setDsmail("test@mail.com");
//		diveshopVO1.setDsinfo("testInfo");
//		diveshopVO1.setDs_lng("13.2158");
//		diveshopVO1.setDs_lat("12.2186");
//		diveshopVO1.setDs_state("審核通過");
//		diveshopVO1.setDs_ascore(90);
//		diveshopVO1.setDs_rep_no(0);
//		dao.insert(diveshopVO1);

		// update
//		DiveshopVO diveshopVO2 = new DiveshopVO();
//		diveshopVO2.setDs_name("updatetest");
//		diveshopVO2.setBrcid(65412578);
//		diveshopVO2.setTel("0354876541");
//		diveshopVO2.setAddress("updateAddress");
//		diveshopVO2.setDsaccount("updateaccount");
//		diveshopVO2.setDspaw("updatepassword");
//		diveshopVO2.setDsmail("update@mail.com");
//		diveshopVO2.setDsinfo("updateInfo");
//		diveshopVO2.setDs_no("DS0005");
//		dao.update(diveshopVO2);

		// delete
//		dao.delete("DS0005");

		// 查詢某間潛店
//		DiveshopVO diveshopVO3 = dao.findByPrimaryKey("DS0003");
//		System.out.print(diveshopVO3.getDs_name() + ",");
//		System.out.print(diveshopVO3.getAddress() + ",");
//		System.out.print(diveshopVO3.getBrcid() + ",");
//		System.out.print(diveshopVO3.getTel() + ",");
//		System.out.print(diveshopVO3.getAddress() + ",");
//		System.out.print(diveshopVO3.getDsaccount() + ",");
//		System.out.print(diveshopVO3.getDspaw() + ",");
//		System.out.print(diveshopVO3.getDsmail() + ",");
//		System.out.print(diveshopVO3.getDsinfo() + ",");
//		System.out.print(diveshopVO3.getDs_lng() + ",");
//		System.out.print(diveshopVO3.getDs_lat()+ ",");
//		System.out.print(diveshopVO3.getDs_state() + ",");
//		System.out.print(diveshopVO3.getDs_ascore() + ",");
//		System.out.print(diveshopVO3.getDs_rep_no());
//		System.out.println();

		// getAll test
//		List<DiveshopVO> list = dao.getaAll();
//		for (DiveshopVO aDive : list) {
//			System.out.print(aDive.getDs_name() + ",");
//			System.out.print(aDive.getAddress() + ",");
//			System.out.print(aDive.getBrcid() + ",");
//			System.out.print(aDive.getTel() + ",");
//			System.out.print(aDive.getAddress() + ",");
//			System.out.print(aDive.getDsaccount() + ",");
//			System.out.print(aDive.getDspaw() + ",");
//			System.out.print(aDive.getDsmail() + ",");
//			System.out.print(aDive.getDsinfo() + ",");
//			System.out.print(aDive.getDs_lng() + ",");
//			System.out.print(aDive.getDs_lat() + ",");
//			System.out.print(aDive.getDs_state() + ",");
//			System.out.print(aDive.getDs_ascore() + ",");
//			System.out.print(aDive.getDs_rep_no());
//			System.out.println();
//		}
		// test find by add
		Set<DiveshopVO> set = dao.getShopByAddress("%台北%");
		for (DiveshopVO adiveshop : set) {
			out.print(adiveshop.getDs_name() + ",");
			out.print(adiveshop.getTel() + ",");
			out.print(adiveshop.getAddress() + ",");
			out.print(adiveshop.getDsmail() + ",");
			out.print(adiveshop.getDs_ascore());
			out.println();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
