package com.mdpst_record.controller;

import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;

import com.mdpst_record.model.*;


public class Mdpst_recordServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getAllMemMdpst_record_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mdpst_record/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = str;

				
				/***************************2.開始查詢資料*****************************************/
				Mdpst_recordService mdpst_recordSvc = new Mdpst_recordService();
				List<Mdpst_recordVO> list = mdpst_recordSvc.getMdpstrsByMem_no(mem_no);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mdpst_record/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list); // 資料庫取出的mdpst_recordVO物件,存入req
				String url = "/front-end/mdpst_record/listAllMemMdpst_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMdpst_record.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mdpst_record/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		

        if ("insert".equals(action)) { // 來自addMdpst_record.jsp的請求  

        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String mem_no = req.getParameter("mem_no");

				String mem_noReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號: 請勿空白");
				} else if(!mem_no.trim().matches(mem_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String dp_no = req.getParameter("dp_no");

				String dp_noReg = "^[(a-zA-Z0-9_)]{2,15}$";
				if (dp_no == null || dp_no.trim().length() == 0) {
					errorMsgs.add("文章編號: 請勿空白");
				} else if(!dp_no.trim().matches(dp_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("文章編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				

				Mdpst_recordVO mdpst_recordVO = new Mdpst_recordVO();
			    mdpst_recordVO.setMem_no(mem_no);
			    mdpst_recordVO.setDp_no(dp_no);
			    
			    java.sql.Timestamp trac_time = new java.sql.Timestamp(System.currentTimeMillis());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("mdpst_recordVO", mdpst_recordVO); // 含有輸入格式錯誤的mdpst_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mdpst_record/addMdpst_record.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Mdpst_recordService mdpst_recordSvc = new Mdpst_recordService();
				mdpst_recordVO = mdpst_recordSvc.addMdpst_record(mem_no, dp_no, trac_time);


				List<Mdpst_recordVO> list = mdpst_recordSvc.getMdpstrsByMem_no(mem_no);
				req.setAttribute("list",list);
				
				System.out.println("-----------------------------------1");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/mdpst_record/listAllMemMdpst_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMdpst_record.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mdpst_record/addMdpst_record.jsp");
				failureView.forward(req, res);
			}
        }
        
        
		
		if ("delete".equals(action)) { // 來自listAllMem.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String mem_no = new String(req.getParameter("mem_no"));
				String dp_no = new String(req.getParameter("dp_no"));
				
				/***************************2.開始刪除資料***************************************/
				Mdpst_recordService mdpst_recordSvc = new Mdpst_recordService();
				mdpst_recordSvc.deleteMdpst_record(mem_no, dp_no);
				
				List<Mdpst_recordVO> list = mdpst_recordSvc.getMdpstrsByMem_no(mem_no);
				req.setAttribute("list",list);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/mdpst_record/listAllMemMdpst_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mdpst_record/listAllMemMdpst_record.jsp");
				failureView.forward(req, res);
			}
		}
        

	}
}
