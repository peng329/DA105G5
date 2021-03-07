package com.func.controller;

import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;


import com.func.model.*;

public class FuncServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("fc_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入權限編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/func/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String fc_no = null;
				try {
					fc_no = str;
				} catch (Exception e) {
					errorMsgs.add("權限編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/func/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FuncService funcSvc = new FuncService();
				FuncVO funcVO = funcSvc.getOneFunc(fc_no);
				if (funcVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/func/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("funcVO", funcVO); // 資料庫取出的funcVO物件,存入req
				String url = "/back-end/func/listOneFunc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFunc.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/func/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllFunc.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String fc_no = req.getParameter("fc_no");
				
				/***************************2.開始查詢資料****************************************/
				FuncService funcSvc = new FuncService();
				FuncVO funcVO = funcSvc.getOneFunc(fc_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("funcVO", funcVO);         // 資料庫取出的funcVO物件,存入req
				
				
				
				String url = "/back-end/func/update_func_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_func_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/func/listAllFunc.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_func_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String fc_no = req.getParameter("fc_no");
				
				

				
				String fc_name = req.getParameter("fc_name");

				String fc_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (fc_name == null || fc_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!fc_name.trim().matches(fc_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
	

				FuncVO funcVO = new FuncVO();
			    funcVO.setFc_no(fc_no);
			    funcVO.setFc_name(fc_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("funcVO", funcVO); // 含有輸入格式錯誤的funcVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/func/update_func_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				FuncService funcSvc = new FuncService();
				funcVO = funcSvc.updateFunc(fc_no, fc_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("funcVO", funcVO); // 資料庫update成功後,正確的的funcVO物件,存入req
				String url = "/back-end/func/listOneFunc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneFunc.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/func/update_func_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addFunc.jsp的請求  

        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String fc_name = req.getParameter("fc_name");

				String fc_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (fc_name == null || fc_name.trim().length() == 0) {
					errorMsgs.add("權限名稱: 請勿空白");
				} else if(!fc_name.trim().matches(fc_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("權限名稱: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				

				FuncVO funcVO = new FuncVO();
			    funcVO.setFc_name(fc_name);
		    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("funcVO", funcVO); // 含有輸入格式錯誤的funcVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/func/addFunc.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				FuncService funcSvc = new FuncService();
				funcVO = funcSvc.addFunc(fc_name);

				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/func/listAllFunc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFunc.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/func/addFunc.jsp");
				failureView.forward(req, res);
			}
        }

	}
}
