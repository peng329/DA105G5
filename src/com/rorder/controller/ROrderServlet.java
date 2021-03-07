package com.rorder.controller;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.equip.model.EquipDAO;
import com.equip.model.EquipService;
import com.equip.model.EquipVO;
import com.equipc.model.EquipCService;
import com.mem.model.MemVO;
import com.rodetail.model.RoDetailService;
import com.rodetail.model.RoDetailVO;
import com.rorder.model.*;
@MultipartConfig
public class ROrderServlet extends HttpServlet {

	private ServletRequest session;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAMem_All_RO".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mem_no = req.getParameter("mem_no");
				if (mem_no == null || (mem_no.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}else if(mem_no.length()!=7) {
					errorMsgs.add("會員編號長度錯誤");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rorder/listAMemRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				ROrderService rorderSvc = new ROrderService();
				List<ROrderVO> list= rorderSvc.getAMenAllRo(mem_no);
				if (list== null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rorder/listAMemRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("list", list); // 資料庫取出的equipVO物件,存入req
				String url = "/front-end/rorder/listAMemRO.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rorder/listAMemRO.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAMem_A_RO".equals(action)) {// 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mem_no = req.getParameter("mem_no");
				String ro_no = req.getParameter("ro_no");
	
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				ROrderService rorderSvc = new ROrderService();
				ROrderVO rorderVO= rorderSvc.getAMenRO(mem_no, ro_no);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("rorderVO", rorderVO); // 資料庫取出的equipVO物件,存入req
				String url = "/back-end/rorder/update_rorder_byDS.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getADS_All_RO".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ds_no = req.getParameter("ds_no");
				if (ds_no == null || (ds_no.trim()).length() == 0) {
					errorMsgs.add("請輸入潛店編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ROrderService rorderSvc = new ROrderService();
				List<ROrderVO> list= rorderSvc.getDsAllRo(ds_no);
				if (list== null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("list",list); // 資料庫取出的equipVO物件,存入req
				String url = "/back-end/rorder/listADSAllRO.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/listADSAllRO.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateByDS".equals(action)) { // 來自update_Equip_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
//			String requestURL = req.getParameter("requestURL"); 
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ro_no = req.getParameter("ro_no");
				String op_state = req.getParameter("op_state");
				
				java.sql.Date rr_date = null;
				try {
				rr_date = java.sql.Date.valueOf(req.getParameter("rr_date").trim());
				} catch (IllegalArgumentException e) {
					rr_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Integer ffine = new Integer(req.getParameter("ffine"));

				String o_state = req.getParameter("o_state");
				if (o_state == null ||o_state.trim().length() == 0) {
					errorMsgs.add("訂單狀態:請勿空白");
				}if(rr_date.toString()!="") {
					o_state="已歸還";
				}

				String o_note= req.getParameter("o_note");
				
				ROrderVO rorderVO = new ROrderVO();
				rorderVO.setOp_state(op_state);
				rorderVO.setRr_date(rr_date);
				rorderVO.setO_state(o_state);
				rorderVO.setFfine(ffine);
				rorderVO.setO_note(o_note);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rorderVO", rorderVO); // 含有輸入格式錯誤的equipCVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/update_rorder_byDS.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				if(rr_date.toString()!="") {
					RoDetailService rdSvc = new RoDetailService();
					List<RoDetailVO> list = rdSvc.getgetSameRoRdAll(ro_no);
					for(int i=0;i<list.size();i++) {
						EquipDAO equipDAO = new EquipDAO();
						equipDAO.updateByReturn(list.get(i).getEp_seq());
					}
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				ROrderService rorderSvc = new ROrderService();
				rorderVO =rorderSvc.updateByDS(ro_no,op_state, rr_date, ffine, o_state, o_note);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("rorderVO", rorderVO); // 資料庫update成功後,正確的的EquipVO物件,存入req
				String url = "/back-end/rorder/listADSAllRO.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEquip.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rorder/update_rorder_byDS.jsp");
				failureView.forward(req, res);
			}
		}

		
		if ("getPayInfo".equals(action)) {// 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String mem_no =req.getParameter("mem_no");
				String ro_no = req.getParameter("ro_no");
				if (ro_no == null || (ro_no.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/creditcard/credit_card.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************2.開始修改資料*****************************************/
				ROrderService rorderSvc=new ROrderService();
				rorderSvc.updateByMEM(ro_no);				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				ROrderVO rorderVO = rorderSvc.getAMenRO(mem_no, ro_no);				
				req.setAttribute("ro_no",ro_no); // 資料庫取出的equipVO物件,存入req
				req.setAttribute("rorderVO",rorderVO); // 資料庫取出的equipVO物件,存入req
				String url = "/front-end/rentcart/PaySuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rentcart/PaySuccess.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
