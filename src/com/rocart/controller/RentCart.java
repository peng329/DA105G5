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
import com.mem.model.MemVO;
import com.rodetail.model.*;
import com.rorder.model.*;

@WebServlet("/RentCart")
public class RentCart extends HttpServlet {

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
				session.setAttribute("eplist", eplist);
				session.removeAttribute("eplistsize");
				String url="/front-end/rentcart/Cart.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(url);
				rd.forward(request,response);
			}else if(action.equals("ADD")){
				String ds_no = request.getParameter("ds_no");
				String ep_seq=request.getParameter("ep_seq");
				EquipService equipSvc = new EquipService();
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
				session.setAttribute("eplist", eplist);
				session.setAttribute("eplistsize", eplist.size());
				String url="/front-end/equip/listAllDSEquipByDs_no.jsp?ds_no="+ds_no;
				RequestDispatcher rd = request.getRequestDispatcher(url);
				rd.forward(request,response);
			}
		
		}else if (action.equals("CHECKOUT")) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			Set<String> ds_nos = new HashSet<>();
			for(int i=0;i<eplist.size();i++) {
				String ds_no = eplist.get(i).getDs_no();
				ds_nos.add(ds_no);
			}

            String ds_no=eplist.get(0).getDs_no();			

			String mem_no = request.getParameter("mem_no");
            
			Integer tepc=new Integer(request.getParameter("tepc"));
			
			Integer tpriz=new Integer(request.getParameter("tpriz"));
			String op_state="未付款";
			
			java.sql.Date rs_date = null;
			try {
				rs_date = java.sql.Date.valueOf(request.getParameter("rs_date"));
			} catch (IllegalArgumentException e) {
				rs_date = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入租賃開始日");
			}

			java.sql.Date rd_date = null;
			try {
				rd_date = java.sql.Date.valueOf(request.getParameter("rd_date"));
			} catch (IllegalArgumentException e) {
				rd_date = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入租賃結束日期");
			}

			String o_state="待取";
			String o_note=request.getParameter("o_note");

			List<RoDetailVO> rdlist = new ArrayList<>();

			for (int i = 0; i < eplist.size(); i++) {
				EquipVO equipVO = eplist.get(i);
				RoDetailVO rodetailVO = new RoDetailVO();
				rodetailVO.setEp_seq(equipVO.getEp_seq());
				rodetailVO.setEp_crp(equipVO.getEp_rp());	
				rdlist.add(rodetailVO);
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
			
			if (!errorMsgs.isEmpty()) {
				request.setAttribute("rorderVO", rorderVO);
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/rentcart/Cart.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			if (session.getAttribute("A") != null) {
				EquipDAO equipDAO = new EquipDAO();
				for(int i=0;i<eplist.size();i++) {
					equipDAO.updateByOrder(eplist.get(i).getEp_seq());
				}					
				ROrderService rorderSvc = new ROrderService();
				next_ro_no=rorderSvc.insertWithRoDetail(rorderVO, rdlist);
			}
			
			session.removeAttribute("A");
			session.removeAttribute("eplist");
			session.removeAttribute("eplistsize");
			session.setAttribute("next_ro_no", next_ro_no);
			session.setAttribute("rorderVO", rorderVO);
			session.setAttribute("rdlist", rdlist);

			String url="/front-end/rentcart/Checkout.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request,response);
		}

	}
}
