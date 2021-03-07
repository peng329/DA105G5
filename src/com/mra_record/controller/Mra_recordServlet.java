package com.mra_record.controller;

import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;


import com.mra_record.model.*;

public class Mra_recordServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String str = req.getParameter("fc_no");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入權限編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/mra_record/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				String fc_no = null;
//				try {
//					fc_no = str;
//				} catch (Exception e) {
//					errorMsgs.add("權限編號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/mra_record/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				Mra_recordService mra_recordSvc = new Mra_recordService();
//				Mra_recordVO mra_recordVO = mra_recordSvc.getOneMra_record(fc_no);
//				if (mra_recordVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/mra_record/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("mra_recordVO", mra_recordVO); // 資料庫取出的mra_recordVO物件,存入req
//				String url = "/back-end/mra_record/listOneMra_record.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMra_record.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/mra_record/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		
//		if ("getOne_For_Update".equals(action)) { // 來自listAllMra_record.jsp的請求
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				String fc_no = req.getParameter("fc_no");
//				
//				/***************************2.開始查詢資料****************************************/
//				Mra_recordService mra_recordSvc = new Mra_recordService();
//				Mra_recordVO mra_recordVO = mra_recordSvc.getOneMra_record(fc_no);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("mra_recordVO", mra_recordVO);         // 資料庫取出的mra_recordVO物件,存入req
//				
//				
//				
//				String url = "/back-end/mra_record/update_mra_record_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_mra_record_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/mra_record/listAllMra_record.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		
		if ("update".equals(action)) { // 來自update_mra_record_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String rar_no = req.getParameter("rar_no").trim();	
				String mem_no = req.getParameter("mem_no").trim();
				String mpo_no = req.getParameter("mpo_no").trim();
				
				java.sql.Timestamp rep_time = java.sql.Timestamp.valueOf(req.getParameter("rep_time").trim());
				
				String rep_content = req.getParameter("rep_content").trim();
				String rep_state = req.getParameter("rep_state").trim();
	

				Mra_recordVO mra_recordVO = new Mra_recordVO();
			    mra_recordVO.setRar_no(rar_no);
			    mra_recordVO.setMem_no(mem_no);
			    mra_recordVO.setMpo_no(mpo_no);
			    mra_recordVO.setRep_time(rep_time);
			    mra_recordVO.setRep_content(rep_content);
			    mra_recordVO.setRep_state(rep_state);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mra_recordVO", mra_recordVO); // 含有輸入格式錯誤的mra_recordVO物件,也存入req
					RequestDispatcher failureView = req
							//.getRequestDispatcher("/back-end/mra_record/listAllMra_record.jsp");
							.getRequestDispatcher("/back-end/mra_record/AllMra_record.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				System.out.println(rep_state);				
				/***************************2.開始修改資料*****************************************/
				Mra_recordService mra_recordSvc = new Mra_recordService();
				mra_recordVO = mra_recordSvc.updateMra_record(rar_no, mem_no, mpo_no, rep_time, rep_content, rep_state);
			
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				
				req.setAttribute("mra_recordVO", mra_recordVO); // 資料庫update成功後,正確的的mra_recordVO物件,存入req
				//String url = "/back-end/mra_record/listAllMra_record.jsp";
				String url = "/back-end/mra_record/AllMra_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMra_record.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mra_record/AllMra_record.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addMra_record.jsp的請求  

        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String mem_no = req.getParameter("mem_no");
				String mpo_no = req.getParameter("mpo_no");

				
				java.sql.Timestamp rep_time = new java.sql.Timestamp(System.currentTimeMillis());
				
				String rep_content = req.getParameter("rep_content");
				String rep_contentReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,50}$";
				if (rep_content == null || rep_content.trim().length() == 0) {
					errorMsgs.add("權限名稱: 請勿空白");
				} else if(!rep_content.trim().matches(rep_contentReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("權限名稱: 只能是英文字母、數字和_ , 且長度必需在1到50之間");
	            }
				
				String rep_state = "待審核";
				
				

				Mra_recordVO mra_recordVO = new Mra_recordVO();
			    mra_recordVO.setMem_no(mem_no);
			    mra_recordVO.setMpo_no(mpo_no);
			    mra_recordVO.setRep_time(rep_time);
			    mra_recordVO.setRep_content(rep_content);
			    mra_recordVO.setRep_content(rep_content);

		    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("mra_recordVO", mra_recordVO); // 含有輸入格式錯誤的mra_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mra_record/addMra_record.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Mra_recordService mra_recordSvc = new Mra_recordService();
				mra_recordVO = mra_recordSvc.addMra_record(mem_no, mpo_no, rep_time, rep_content, rep_content);

				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/mra_record/listAllMra_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMra_record.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mra_record/addMra_record.jsp");
				failureView.forward(req, res);
			}
        }

	}
}