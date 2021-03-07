package com.rocart.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import com.eqpic.model.EqpicService;
import com.equip.model.*;
import com.rodetail.model.*;
import com.rorder.model.*;

@WebServlet("/RentCartORI2")
public class RentCartORI2 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		Vector<EquipVO> eplist = (Vector<EquipVO>)session.getAttribute("eplist");
		String action = request.getParameter("action");
		
		if(!action.equals("CHECKOUT")) {
			
			if(action.equals("DELETE")) {
				String del = request.getParameter("del");
				int d= Integer.parseInt(del);
				eplist.remove(d);
			}else if(action.equals("ADD")){
				EquipService equipSvc = new EquipService();
				String ep_seq=request.getParameter("ep_seq");
				EquipVO anequip = equipSvc.getOneEq(ep_seq);
				if(eplist == null) {
					eplist = new Vector<EquipVO>();
					eplist.add(anequip);
				}else {
					if (eplist.contains(anequip)) {
						EquipVO innerEquip = eplist.get(eplist.indexOf(anequip));
					} else {
						eplist.add(anequip);
					}
				}
			}
			session.setAttribute("rentcart", eplist);
			String url="/RentCart/EPShop.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request,response);
		}else if (action.equals("CHECKOUT")) {
			Set<String> ds_nos = new HashSet<>();
			for(int i=0;i<eplist.size();i++) {
				String ds_no = eplist.get(i).getDs_no();
				ds_nos.add(ds_no);
			}
//			System.out.println("-------------------");			
//			System.out.println(((EquipVO)eplist.get(0)).getDs_no());			
//			System.out.println(((EquipVO)eplist.get(1)).getDs_no());			
//			System.out.println("-------------------");			
//			
            String ds_no="DS0001";			
            String mem_no = "M000001";
            
//          String ds_no=eplist.get(0).getDs_no();			
//			String mem_no = request.getParameter("mem_no");
            
//			Integer tepc=new Integer(request.getParameter("tepc"));
//			
//			Integer tpriz=new Integer(request.getParameter("tpriz"));
			String op_state="未付款";
			
			java.sql.Date rs_date = null;
			rs_date = java.sql.Date.valueOf(request.getParameter("start_date").trim());
			
			java.sql.Date rd_date = null;
			rd_date = java.sql.Date.valueOf(request.getParameter("end_date").trim());
			
			String o_state="待取";
			String o_note=request.getParameter("o_note");
			
			Map<String,List<RoDetailVO>> map = new LinkedHashMap<String,List<RoDetailVO>>();

			Integer tepc=null;
			Integer tpriz=null;
			
			List<RoDetailVO> rdlist = new ArrayList<>();
			for (int i = 0; i < eplist.size(); i++) {
				EquipVO equipVO = eplist.get(i);
				RoDetailVO rodetailVO = new RoDetailVO();
				rodetailVO.setEp_seq(equipVO.getEp_seq());
				rodetailVO.setEp_crp(equipVO.getEp_rp());	
				tpriz=+equipVO.getEp_rp();
				rdlist.add(rodetailVO);
				if(ds_nos.toString().equals(equipVO.getDs_no())) {
					map.put(equipVO.getDs_no(),rdlist);
				}
				
			}
			ROrderVO rorderVO=new ROrderVO();
			rorderVO.setDs_no(ds_no);
			rorderVO.setMem_no(mem_no);
			rorderVO.setTpriz(tpriz);
			rorderVO.setTepc(tepc);
			rorderVO.setRs_date(rs_date);
			rorderVO.setRd_date(rd_date);
			rorderVO.setOp_state(op_state);
			rorderVO.setO_state(o_state);
			rorderVO.setO_note(o_note);
			String next_ro_no=null;
			HttpSession session1 = request.getSession();
			if (session1.getAttribute("A") != null) {
				ROrderService rorderSvc = new ROrderService();
				next_ro_no=rorderSvc.insertWithRoDetail(rorderVO, rdlist);
			}
			session1.removeAttribute("A");
			session.removeAttribute("eplist");
			
			session.setAttribute("next_ro_no", next_ro_no);
			session.setAttribute("rorderVO", rorderVO);
			session.setAttribute("rdlist", rdlist);
			
//			System.out.println(	rdlist.get(0).getEp_seq());
//			System.out.println(	rdlist.get(1).getEp_seq());

			String url="/RentCart/Checkout.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request,response);
		}

	}
}
