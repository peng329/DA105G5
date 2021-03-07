package com.mat_record.controller;

import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;


import com.mat_record.model.*;



public class Mat_recordServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getAllMemMat_record_Display".equals(action)) { // 來自select_page.jsp的請求

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
							.getRequestDispatcher("/front-end/memCenter/mem_myFollow.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = str;

				
				/***************************2.開始查詢資料*****************************************/
				Mat_recordService mat_recordSvc = new Mat_recordService();
				List<Mat_recordVO> list = mat_recordSvc.getMatrsByMem_no(mem_no);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/memCenter/mem_myFollow.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list); // 資料庫取出的mat_recordVO物件,存入req
				String url = "/front-end/memCenter/mem_myFollow.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMat_record.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memCenter/mem_myFollow.jsp");
				failureView.forward(req, res);
			}
		}
		

        if ("insert".equals(action)) { // 來自addMat_record.jsp的請求  

        	
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
				
				String mpo_no = req.getParameter("mpo_no");

				String mpo_noReg = "^[(a-zA-Z0-9_)]{2,15}$";
				if (mpo_no == null || mpo_no.trim().length() == 0) {
					errorMsgs.add("文章編號: 請勿空白");
				} else if(!mpo_no.trim().matches(mpo_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("文章編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				

				Mat_recordVO mat_recordVO = new Mat_recordVO();
			    mat_recordVO.setMem_no(mem_no);
			    mat_recordVO.setMpo_no(mpo_no);
			    
			    java.sql.Timestamp trac_time = new java.sql.Timestamp(System.currentTimeMillis());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("mat_recordVO", mat_recordVO); // 含有輸入格式錯誤的mat_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mat_record/addMat_record.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Mat_recordService mat_recordSvc = new Mat_recordService();
				mat_recordVO = mat_recordSvc.addMat_record(mem_no, mpo_no, trac_time);
				
				List<Mat_recordVO> list = mat_recordSvc.getMatrsByMem_no(mem_no);
				req.setAttribute("list",list);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/mat_record/listAllMemMat_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMat_record.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mat_record/addMat_record.jsp");
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
				String mpo_no = new String(req.getParameter("mpo_no"));
				
				/***************************2.開始刪除資料***************************************/
				Mat_recordService mat_recordSvc = new Mat_recordService();
				mat_recordSvc.deleteMat_record(mem_no, mpo_no);
				
				List<Mat_recordVO> list = mat_recordSvc.getMatrsByMem_no(mem_no);
				req.setAttribute("list",list);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/memCenter/mem_myFollow.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memCenter/mem_myFollow.jsp");
				failureView.forward(req, res);
			}
		} 
		
		
		
        if ("ajaxInsert".equals(action)) { // 來自addMat_record.jsp的請求  
        	System.out.println("-----------******************");
        	
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
				
				String mpo_no = req.getParameter("mpo_no");

				String mpo_noReg = "^[(a-zA-Z0-9_)]{2,15}$";
				if (mpo_no == null || mpo_no.trim().length() == 0) {
					errorMsgs.add("文章編號: 請勿空白");
				} else if(!mpo_no.trim().matches(mpo_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("文章編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				

				Mat_recordVO mat_recordVO = new Mat_recordVO();
			    mat_recordVO.setMem_no(mem_no);
			    mat_recordVO.setMpo_no(mpo_no);
			    
			    java.sql.Timestamp trac_time = new java.sql.Timestamp(System.currentTimeMillis());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("mat_recordVO", mat_recordVO); // 含有輸入格式錯誤的mat_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mat_record/addMat_record.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Mat_recordService mat_recordSvc = new Mat_recordService();
				mat_recordVO = mat_recordSvc.addMat_record(mem_no, mpo_no, trac_time);
				
				List<Mat_recordVO> list = mat_recordSvc.getMatrsByMem_no(mem_no);
				req.setAttribute("list",list);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
			
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mat_record/addMat_record.jsp");
				failureView.forward(req, res);
			}
			
        }
        
		if ("ajaxDelete".equals(action)) { // 來自listAllMem.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String mem_no = new String(req.getParameter("mem_no"));
				String mpo_no = new String(req.getParameter("mpo_no"));
	
				/***************************2.開始刪除資料***************************************/
				Mat_recordService mat_recordSvc = new Mat_recordService();
				mat_recordSvc.deleteMat_record(mem_no, mpo_no);
				
				List<Mat_recordVO> list = mat_recordSvc.getMatrsByMem_no(mem_no);
				req.setAttribute("list",list);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								

				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memCenter/mem_myFollow.jsp");
				failureView.forward(req, res);
			}
		} 

	}
}