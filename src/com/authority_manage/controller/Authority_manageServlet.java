package com.authority_manage.controller;

import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.authority_manage.model.*;
import com.func.model.FuncService;
import com.func.model.FuncVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;



public class Authority_manageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getAllWmAuthority_manage_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("wm_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/authority_manage/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String wm_no = str;

				
				/***************************2.開始查詢資料*****************************************/
	
				//取得員工權限的對照表，checkBox要用
				Authority_manageService authority_manageSvc = new Authority_manageService();
				List<String[]> list = authority_manageSvc.getAuthority_manageVOsByContrast(wm_no);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/authority_manage/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list);
				req.setAttribute("wm_no", wm_no); 
				
				String url = "/back-end/authority_manage/listAllWmAuthority_manage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAuthority_manage.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/authority_manage/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		

        if ("insert".equals(action)) { // 來自addAuthority_manage.jsp的請求  

        	System.out.println("------------------------新增權限");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String wm_no = req.getParameter("wm_no");

				String wm_noReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (wm_no == null || wm_no.trim().length() == 0) {
					errorMsgs.add("員工編號: 請勿空白");
				} else if(!wm_no.trim().matches(wm_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String fc_no = req.getParameter("fc_no");

				String fc_noReg = "^[(a-zA-Z0-9_)]{2,15}$";
				if (fc_no == null || fc_no.trim().length() == 0) {
					errorMsgs.add("權限編號: 請勿空白");
				} else if(!fc_no.trim().matches(fc_noReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("權限編號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				

				Authority_manageVO authority_manageVO = new Authority_manageVO();
			    authority_manageVO.setWm_no(wm_no);
			    authority_manageVO.setFc_no(fc_no);
			    
			    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("authority_manageVO", authority_manageVO); // 含有輸入格式錯誤的authority_manageVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/authority_manage/addAuthority_manage.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Authority_manageService authority_manageSvc = new Authority_manageService();
				authority_manageVO = authority_manageSvc.addAuthority_manage(wm_no, fc_no);
				
//				//取得員工權限的對照表，checkBox要用
//				List<String[]> list = authority_manageSvc.getAuthority_manageVOsByContrast(wm_no);
//				
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				req.setAttribute("list", list);
//				req.setAttribute("wm_no", wm_no); 
//				
//				String url = "/back-end/authority_manage/listAllWmAuthority_manage.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAuthority_manage.jsp
//				successView.forward(req, res);				
//				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/authority_manage/addAuthority_manage.jsp");
				failureView.forward(req, res);
			}
			
        }
        
		if ("delete".equals(action)) { // 來自listAllMem.jsp
			System.out.println("------------------------刪除權限");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wm_no = new String(req.getParameter("wm_no"));
				String fc_no = new String(req.getParameter("fc_no"));
				
				/***************************2.開始刪除資料***************************************/
				Authority_manageService authority_manageSvc = new Authority_manageService();
				authority_manageSvc.deleteAuthority_manage(wm_no, fc_no);
				
//				//取得員工權限的對照表，checkBox要用
//				List<String[]> list = authority_manageSvc.getAuthority_manageVOsByContrast(wm_no);
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/back-end/authority_manage/listAllWmAuthority_manage.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/authority_manage/listAllMemAuthority_manage.jsp");
				failureView.forward(req, res);
			}
		}
		
		//用ajax處理帳號重複
//		if ("ajaxCheckAmBox".equals(action)) { // 來自select_page.jsp的請求
//			System.out.println("---------------ajaxCheckAmBox");
////			List<String> errorMsgs = new LinkedList<String>();
////			// Store this set in the request scope, in case we need to
////			// send the ErrorPage view.
////			req.setAttribute("errorMsgs", errorMsgs);
//			
//			//session.invalidate(); //測試用
//
//			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String mem_no = req.getParameter("mem_no");
//				
//				
//				/***************************2.開始查詢資料*****************************************/
//				String checkAns = "";
//				
//				//利用ajax得來的id，當參數，查找是否有這個帳號				
//				MemService memSvc3 = new MemService();
//				MemVO memVO3 = memSvc3.getOneMemById(mem_id);
//				
//				if(memVO3 == null) {
//					checkAns = "ok";
//				}else {
//					checkAns = "no";
//				}
//				
//				System.out.println(checkAns);
//				//為了最後送出用
//				PrintWriter out = res.getWriter();
//				
//				
//				//用json處理方式
//			    JSONObject data = new JSONObject();
//			    data.put("checkAns", checkAns);
//				//將該json給ajax
//				out.println(data);
//				
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//
//				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				e.getMessage();
//
//			}
//		}
	}
}