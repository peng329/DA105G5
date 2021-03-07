package com.act_list.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.act_list.model.*;
import com.agp_list.model.Agp_List_Service;
import com.agp_list.model.Agp_List_VO;
import com.equipc.model.EquipCVO;
import com.gp_ept.model.Gp_Ept_Service;
import com.gp_ept.model.Gp_Ept_VO;
import com.mem.model.MemVO;


@MultipartConfig
public class Act_List_Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		System.out.println("執行ACT");

		/*******************************************
		 * 來自select_page.jsp的請求
		 ******************************************/
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("act_list_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入揪團編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String act_list_no = null;
				try {
					act_list_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("揪團編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				Act_List_VO act_list_vo = act_list_Svc.getOneACT_LIST(act_list_no);
				if (act_list_vo == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("act_list_vo", act_list_vo); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/act_list/listOneAct_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAct_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		/*****************************************
		 * 來自listAllAct_List.jsp的請求
		 *********************************************/
		if ("getOne_For_Update".equals(action)) { // 來自listAllAct_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String act_list_no = new String(req.getParameter("act_list_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				Act_List_VO act_list_vo = act_list_Svc.getOneACT_LIST(act_list_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("act_list_vo", act_list_vo); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/act_list/update_act_list_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/listAllAct_List.jsp");
				failureView.forward(req, res);
			}
		}

		/******************************************
		 * 來自update_act_list_input.jsp的請求
		 **********************************/
		if ("update".equals(action)) { // 來自update_act_list_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
				String act_list_no = new String(req.getParameter("act_list_no").trim());
				if (act_list_no == null || act_list_no.trim().length() == 0) {
					errorMsgs.add("團主會員編號請勿空白");
				}
				/*************************** mem_no團主會員編號編號錯誤處理 ***********************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("團主會員編號請勿空白");
				}
				/*************************** dp_no潛點編號錯誤處理 *******************************/
				String dp_no = req.getParameter("dp_no").trim();
				if (dp_no == null || dp_no.trim().length() == 0) {
					errorMsgs.add("潛點編號請勿空白");
				}
				/*************************** start_date報名起始日錯誤處理 ************************/
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** dual_date報名截止日錯誤處理 *************************/
				java.sql.Date dual_date = null;
				try {
					dual_date = java.sql.Date.valueOf(req.getParameter("dual_date").trim());
				} catch (IllegalArgumentException e) {
					dual_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** action_date出團日錯誤處理 *******************************/
				java.sql.Date action_date = null;
				try {
					action_date = java.sql.Date.valueOf(req.getParameter("action_date").trim());
				} catch (IllegalArgumentException e) {
					action_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** act_state開團狀態錯誤處理 **************************/
				String act_state = req.getParameter("act_state").trim();
				if (act_state == null || act_state.trim().length() == 0) {
					errorMsgs.add("開團狀態請勿空白");
				}
				/*************************** locale揪團地點錯誤處理 *****************************/
				String locale = req.getParameter("locale");
//				String localeReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{3,100}$";
				if (locale == null || locale.trim().length() == 0) {
					errorMsgs.add("揪團地點: 請勿空白");
				} // else if (!locale.trim().matches(localeReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團地點: 只能是中文、英文字母和_, 且長度必需在3到100之間");
//				}
				/*************************** act_max參加人上限錯誤處理 ***************************/
				Integer act_max = null;
				try {
					act_max = new Integer(req.getParameter("act_max").trim());
				} catch (NumberFormatException e) {
					act_max = 2;
					errorMsgs.add("參加人上限請填數字.");
				}
				/*************************** act_min參加人下限錯誤處理 ***************************/
				Integer act_min = null;
				try {
					act_min = new Integer(req.getParameter("act_min").trim());
				} catch (NumberFormatException e) {
					act_min = 2;
					errorMsgs.add("參加人下限請填數字.");
				}
				/*************************** gp_pic揪團圖片錯誤處理 *****************************/

				InputStream in = req.getPart("gp_pic").getInputStream();
				byte[] gp_pic = null;
				if (in.available() != 0) {
					gp_pic = new byte[in.available()];
					in.read(gp_pic);
					in.close();
				} else {
					errorMsgs.add("揪團圖片請勿空白");
				}

//				byte[] gp_pic = null;
//				InputStream in = null;
//				Part part = req.getPart("gp_pic");
//				try {
//					
//					in = part.getInputStream();
//					gp_pic = new byte[in.available()];
//					in.read(gp_pic);
//
//				} catch (IOException ie) {
//					errorMsgs.add("查無圖片");
//				} finally {
//					try {
//						in.close();
//					} catch (IOException ie) {
//						errorMsgs.add("管子關閉錯誤");
//					}
//				}

				/*************************** gp_info揪團介紹錯誤處理 ****************************/
				String gp_info = req.getParameter("gp_info");
//				String gp_infoReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{3,500}$";
				if (gp_info == null || gp_info.trim().length() == 0) {
					errorMsgs.add("揪團介紹: 請勿空白");
				} // else if (!locale.trim().matches(gp_infoReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團介紹: 只能是中、英文字母、數字和_ , 且長度必需在3到500之間");
//				}
				/*************************** gp_days天數錯誤處理 *******************************/
				Integer gp_days = null;
				try {
					gp_days = new Integer(req.getParameter("gp_days").trim());
				} catch (NumberFormatException e) {
					gp_days = 0;
					errorMsgs.add("天數請填數字.");
				}
				/*************************** act_list_name揪團團名錯誤處理 ***********************/
				String act_list_name = new String(req.getParameter("act_list_name").trim());
				if (act_list_name == null || act_list_name.trim().length() == 0) {
					errorMsgs.add("團名請勿空白");
				}
				/*************************************************************************/

				Act_List_VO act_list_vo = new Act_List_VO();
				act_list_vo.setAct_list_no(act_list_no);
				act_list_vo.setMem_no(mem_no);
				act_list_vo.setDp_no(dp_no);
				act_list_vo.setStart_date(start_date);
				act_list_vo.setDual_date(dual_date);
				act_list_vo.setAction_date(action_date);
				act_list_vo.setAct_state(act_state);
				act_list_vo.setLocale(locale);
				act_list_vo.setAct_max(act_max);
				act_list_vo.setAct_min(act_min);
				act_list_vo.setGp_pic(gp_pic);
				act_list_vo.setGp_info(gp_info);
				act_list_vo.setGp_days(gp_days);
				act_list_vo.setAct_list_name(act_list_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("act_list_vo", act_list_vo); // 含有輸入格式錯誤的act_list_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/update_act_list_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				act_list_vo = act_list_Svc.updateACT_LIST(mem_no, dp_no, start_date, dual_date, action_date, act_state,
						locale, act_max, act_min, gp_pic, gp_info, gp_days, act_list_name, act_list_no);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("act_list_vo", act_list_vo); // 資料庫update成功後,正確的的act_list_vo物件,存入req
				String url = "/front-end/act_list/listAllAct_List.jsp";
				
				HttpSession session = req.getSession();

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");

				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc2 = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc2.getGroup(mem_vo.getMem_no());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo", act_list); // 資料庫取出的act_list_vo物件,存入req

				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAct_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/update_act_list_input.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************
		 * 來自addAct_List.jsp的請求
		 ******************************************/
		if ("insert".equals(action)) { // 來自addAct_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
//				String act_list_no = new String(req.getParameter("act_list_no").trim());
//				if (act_list_no == null || act_list_no.trim().length() == 0) {
//					errorMsgs.add("團主會員編號請勿空白");
//				}
				/*************************** mem_no團主會員編號編號錯誤處理 ***********************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("團主會員編號請勿空白");
				}
				/*************************** dp_no潛點編號錯誤處理 *******************************/
				String dp_no = req.getParameter("dp_no").trim();
				if (dp_no == null || dp_no.trim().length() == 0) {
					errorMsgs.add("潛點編號請勿空白");
				}
				/*************************** start_date報名起始日錯誤處理 ************************/
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** dual_date報名截止日錯誤處理 *************************/
				java.sql.Date dual_date = null;
				try {
					dual_date = java.sql.Date.valueOf(req.getParameter("dual_date").trim());
				} catch (IllegalArgumentException e) {
					dual_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** action_date出團日錯誤處理 *******************************/
				java.sql.Date action_date = null;
				try {
					action_date = java.sql.Date.valueOf(req.getParameter("action_date").trim());
				} catch (IllegalArgumentException e) {
					action_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** act_state開團狀態錯誤處理 **************************/
				String act_state = req.getParameter("act_state").trim();
				if (act_state == null || act_state.trim().length() == 0) {
					errorMsgs.add("開團狀態請勿空白");
				}
				/*************************** locale揪團地點錯誤處理 *****************************/
				String locale = req.getParameter("locale");
//				String localeReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{3,100}$";
				if (locale == null || locale.trim().length() == 0) {
					errorMsgs.add("揪團地點: 請勿空白");
				} // else if (!locale.trim().matches(localeReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團地點: 只能是中文、英文字母和_, 且長度必需在3到100之間");
//				}
				/*************************** act_max參加人上限錯誤處理 ***************************/
				Integer act_max = null;
				try {
					act_max = new Integer(req.getParameter("act_max").trim());
				} catch (NumberFormatException e) {
					act_max = 2;
					errorMsgs.add("參加人上限請填數字.");
				}
				/*************************** act_min參加人下限錯誤處理 ***************************/
				Integer act_min = null;
				try {
					act_min = new Integer(req.getParameter("act_min").trim());
				} catch (NumberFormatException e) {
					act_min = 2;
					errorMsgs.add("參加人下限請填數字.");
				}
				/*************************** gp_pic揪團圖片錯誤處理 *****************************/
				InputStream in = req.getPart("gp_pic").getInputStream();
				byte[] gp_pic = null;
				if (in.available() != 0) {
					gp_pic = new byte[in.available()];
					in.read(gp_pic);
					in.close();
				} else {
					errorMsgs.add("揪團圖片請勿空白");
				}

//				byte[] gp_pic = null;
//				InputStream in = null;
//				Part part = req.getPart("gp_pic");
//				try {
//					in = part.getInputStream();
//					gp_pic = new byte[in.available()];
//					in.read(gp_pic);
//
//				} catch (IOException ie) {
//					errorMsgs.add("查無圖片");
//				} finally {
//					try {
//						in.close();
//					} catch (IOException ie) {
//						errorMsgs.add("管子關閉錯誤");
//					}
//				}

				/*************************** gp_info揪團介紹錯誤處理 ****************************/
				String gp_info = req.getParameter("gp_info");
//				String gp_infoReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{3,500}$";
				if (gp_info == null || gp_info.trim().length() == 0) {
					errorMsgs.add("揪團介紹: 請勿空白");
				} // else if (!locale.trim().matches(gp_infoReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團介紹: 只能是中、英文字母、數字和_ , 且長度必需在3到500之間");
//				}
				/*************************** gp_days天數錯誤處理 *******************************/
				Integer gp_days = null;
				try {
					gp_days = new Integer(req.getParameter("gp_days").trim());
				} catch (NumberFormatException e) {
					gp_days = 0;
					errorMsgs.add("天數請填數字.");
				}
				/*************************** act_list_name揪團團名錯誤處理 ***********************/
				String act_list_name = new String(req.getParameter("act_list_name").trim());
				if (act_list_name == null || act_list_name.trim().length() == 0) {
					errorMsgs.add("團名請勿空白");
				}
				/*************************************************************************/

				Act_List_VO act_list_vo = new Act_List_VO();
//				act_list_vo.setAct_list_no(act_list_no);
				act_list_vo.setMem_no(mem_no);
				act_list_vo.setDp_no(dp_no);
				act_list_vo.setStart_date(start_date);
				act_list_vo.setDual_date(dual_date);
				act_list_vo.setAction_date(action_date);
				act_list_vo.setAct_state(act_state);
				act_list_vo.setLocale(locale);
				act_list_vo.setAct_max(act_max);
				act_list_vo.setAct_min(act_min);
				act_list_vo.setGp_pic(gp_pic);
				act_list_vo.setGp_info(gp_info);
				act_list_vo.setGp_days(gp_days);
				act_list_vo.setAct_list_name(act_list_name);

				

				

				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("act_list_vo", act_list_vo); // 含有輸入格式錯誤的act_list_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/addAct_List.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				act_list_vo = act_list_Svc.addACT_LIST(mem_no, dp_no, start_date, dual_date, action_date, act_state,
						locale, act_max, act_min, gp_pic, gp_info, gp_days,act_list_name);
				

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/act_list/listAllAct_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAct_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/addAct_List.jsp");
				failureView.forward(req, res);
			}
		}

		/*****************************************
		 * 來自addAct_List.jsp的請求
		 ******************************************/
		if ("insertWithGpEpt".equals(action)) { // 來自addAct_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
//				String act_list_no = new String(req.getParameter("act_list_no").trim());
//				if (act_list_no == null || act_list_no.trim().length() == 0) {
//					errorMsgs.add("團主會員編號請勿空白");
//				}
				/*************************** mem_no團主會員編號編號錯誤處理 ***********************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("團主會員編號請勿空白");
				}
				/*************************** dp_no潛點編號錯誤處理 *******************************/
				String dp_no = req.getParameter("dp_no").trim();
				if (dp_no == null || dp_no.trim().length() == 0) {
					errorMsgs.add("潛點編號請勿空白");
				}
				/*************************** start_date報名起始日錯誤處理 ************************/
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** dual_date報名截止日錯誤處理 *************************/
				java.sql.Date dual_date = null;
				try {
					dual_date = java.sql.Date.valueOf(req.getParameter("dual_date").trim());
				} catch (IllegalArgumentException e) {
					dual_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** action_date出團日錯誤處理 *******************************/
				java.sql.Date action_date = null;
				try {
					action_date = java.sql.Date.valueOf(req.getParameter("action_date").trim());
				} catch (IllegalArgumentException e) {
					action_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				/*************************** act_state開團狀態錯誤處理 **************************/
				String act_state = req.getParameter("act_state").trim();
				if (act_state == null || act_state.trim().length() == 0) {
					errorMsgs.add("開團狀態請勿空白");
				}
				/*************************** locale揪團地點錯誤處理 *****************************/
				String locale = req.getParameter("locale");
//				String localeReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{3,100}$";
				if (locale == null || locale.trim().length() == 0) {
					errorMsgs.add("揪團地點: 請勿空白");
				} // else if (!locale.trim().matches(localeReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團地點: 只能是中文、英文字母和_, 且長度必需在3到100之間");
//				}
				/*************************** act_max參加人上限錯誤處理 ***************************/
				Integer act_max = null;
				try {
					act_max = new Integer(req.getParameter("act_max").trim());
				} catch (NumberFormatException e) {
					act_max = 2;
					errorMsgs.add("參加人上限請填數字.");
				}
				/*************************** act_min參加人下限錯誤處理 ***************************/
				Integer act_min = null;
				try {
					act_min = new Integer(req.getParameter("act_min").trim());
				} catch (NumberFormatException e) {
					act_min = 2;
					errorMsgs.add("參加人下限請填數字.");
				}
				/*************************** gp_pic揪團圖片錯誤處理 *****************************/
				InputStream in = req.getPart("gp_pic").getInputStream();
				byte[] gp_pic = null;
				if (in.available() != 0) {
					gp_pic = new byte[in.available()];
					in.read(gp_pic);
					in.close();
				} else {
					errorMsgs.add("揪團圖片請勿空白");
				}

//				byte[] gp_pic = null;
//				InputStream in = null;
//				Part part = req.getPart("gp_pic");
//				try {
//					in = part.getInputStream();
//					gp_pic = new byte[in.available()];
//					in.read(gp_pic);
//
//				} catch (IOException ie) {
//					errorMsgs.add("查無圖片");
//				} finally {
//					try {
//						in.close();
//					} catch (IOException ie) {
//						errorMsgs.add("管子關閉錯誤");
//					}
//				}

				/*************************** gp_info揪團介紹錯誤處理 ****************************/
				String gp_info = req.getParameter("gp_info");
//				String gp_infoReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{3,500}$";
				if (gp_info == null || gp_info.trim().length() == 0) {
					errorMsgs.add("揪團介紹: 請勿空白");
				} // else if (!locale.trim().matches(gp_infoReg)) { //
					// 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("揪團介紹: 只能是中、英文字母、數字和_ , 且長度必需在3到500之間");
//				}
				/*************************** gp_days天數錯誤處理 *******************************/
				Integer gp_days = null;
				try {
					gp_days = new Integer(req.getParameter("gp_days").trim());
				} catch (NumberFormatException e) {
					gp_days = 0;
					errorMsgs.add("天數請填數字.");
				}
				/*************************** act_list_name揪團團名錯誤處理 ***********************/
				String act_list_name = new String(req.getParameter("act_list_name").trim());
				if (act_list_name == null || act_list_name.trim().length() == 0) {
					errorMsgs.add("團名請勿空白");
				}
				/*************************************************************************/

				Act_List_VO act_list_vo = new Act_List_VO();
//				act_list_vo.setAct_list_no(act_list_no);
				act_list_vo.setMem_no(mem_no);
				act_list_vo.setDp_no(dp_no);
				act_list_vo.setStart_date(start_date);
				act_list_vo.setDual_date(dual_date);
				act_list_vo.setAction_date(action_date);
				act_list_vo.setAct_state(act_state);
				act_list_vo.setLocale(locale);
				act_list_vo.setAct_max(act_max);
				act_list_vo.setAct_min(act_min);
				act_list_vo.setGp_pic(gp_pic);
				act_list_vo.setGp_info(gp_info);
				act_list_vo.setGp_days(gp_days);
				act_list_vo.setAct_list_name(act_list_name);
				/***************************************************************************************************************************/

				
				HttpSession session = req.getSession();
				Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>)session.getAttribute("list_ep");
				
				System.out.println(list_ep);
				
				List <Gp_Ept_VO> list = new ArrayList<Gp_Ept_VO>();
			
				for(Gp_Ept_VO new_list_ep :list_ep) {
					Gp_Ept_VO Gp_Ept_List=null;
					 Gp_Ept_List = new Gp_Ept_VO();			
					
					Gp_Ept_List.setMem_no(mem_no);
					Gp_Ept_List.setEpc_no(new_list_ep.getEpc_no());
					Gp_Ept_List.setGp_t_size(new_list_ep.getGp_t_size());
					Gp_Ept_List.setGp_t_num(new_list_ep.getGp_t_num());
					list.add(Gp_Ept_List);
				}
				


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("act_list_vo", act_list_vo); // 含有輸入格式錯誤的act_list_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/addAct_List.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
//				act_list_vo = act_list_Svc.addACT_LIST(mem_no, dp_no, start_date, dual_date, action_date, act_state,
//						locale, act_max, act_min, gp_pic, gp_info, gp_days,act_list_name);
				act_list_Svc.insertWithGpEpt(act_list_vo, list);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				
				String url = "/front-end/act_list/listAllAct_List.jsp";
				
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				Act_List_Service act_list_Svc2 = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc2.getGroup(mem_vo.getMem_no());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo", act_list); // 資料庫取出的act_list_vo物件,存入req

				session.removeAttribute("list_ep");
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAct_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/addAct_List.jsp");
				failureView.forward(req, res);
			}
		}

		/*****************************************
		 * 來自listAllAct_List.jsp的delete
		 **********************************/
		if ("delete".equals(action)) { // 來自listAllAct_List.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String act_list_no = new String(req.getParameter("act_list_no"));
//				String gp_ept_no = new String(req.getParameter("gp_ept_no"));
//				String rlr_no = new String(req.getParameter("rlr_no"));
//				String mem_no = new String(req.getParameter("mem_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
//				Mrl_Record_Service mrl_record_Svc = new Mrl_Record_Service();
//				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
//				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				
//				gp_ept_Svc.deleteGP_EPT(gp_ept_no);
//				mrl_record_Svc.deleteMrl_record(rlr_no);
//				agp_list_Svc.deleteAGP_LIST(act_list_no,mem_no);
				act_list_Svc.deleteACT_LIST(act_list_no);
				HttpSession session = req.getSession();

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");

				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc2 = new Act_List_Service();
				
				Set<Act_List_VO> act_list1 =  act_list_Svc2.getGroup(mem_vo.getMem_no());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo", act_list1); // 資料庫取出的act_list_vo物件,存入req
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/act_list/listAllAct_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/listAllAct_List.jsp");
				failureView.forward(req, res);
			}
		}
	
		
		
		/*****************************************/
	
		
		HttpSession session = req.getSession();
//		@SuppressWarnings("unchecked")
		Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>)session.getAttribute("list_ep");
		
		if ("deletep".equals(action)) {
		String del =req.getParameter("del");
		int d =Integer.parseInt(del);
		list_ep.removeElementAt(d);
		
		
		String url="/front-end/act_list/addAct_List.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		}
		
		else if ("addep".equals(action)) { 

			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//用來判斷商品與購物車裡的商品一不一樣
				boolean match = false;
				
				//取得後來新增的商品
				Gp_Ept_VO gp_ept_vo = getGpEptNum(req);
				//新增第一樣商品
				if(list_ep==null) {
					list_ep = new Vector<Gp_Ept_VO>();
					list_ep.add(gp_ept_vo);
				}else {
					//商品與購物車裡的商品一樣
					for(int i=0; i<list_ep.size();i++) {
						Gp_Ept_VO gp_ept_vo2 =list_ep.get(i); 
						if(gp_ept_vo2.getEpc_no().equals(gp_ept_vo.getEpc_no())&&gp_ept_vo2.getGp_t_size().equals(gp_ept_vo.getGp_t_size())) {
							
							gp_ept_vo2.setGp_t_num(gp_ept_vo2.getGp_t_num()+gp_ept_vo.getGp_t_num());
							
							list_ep.setElementAt(gp_ept_vo2, i);
							match=true;
						}
					}
					//商品與購物車裡的商品不一樣
					if(!match) {
						list_ep.add(gp_ept_vo);
					}
				}
				
				session.setAttribute("list_ep", list_ep);
				String url="/front-end/act_list/addAct_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/addAct_List.jsp");
				failureView.forward(req, res);
			}
		
	}
		
		/*****************************************
		 * 來自listAllAct_List.jsp的請求
		 *********************************************/
		if ("Group_management".equals(action)) { // 來自listAllAct_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");

				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc.getGroup(mem_vo.getMem_no());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo", act_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/act_list/listAllAct_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/listAllAct_List.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************
		 * 來自listAllAct_List2.jsp的請求
		 *********************************************/
		if ("Group_management2".equals(action)) { // 來自listAllAct_List.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc.getFreeGroup(mem_vo.getMem_no(), "已開團");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo2", act_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/act_list/listAllAct_List2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/act_list/listAllAct_List2.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************
		 * 來自listAllAgp_List.jsp的請求
		 *********************************************/
		if ("Group_management3".equals(action)) { // 來自listAllAct_List.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc.getFreeGroup(mem_vo.getMem_no(), "發團中");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo3",act_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/agp_list/listAllAgp_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAgp_List.jsp");
				failureView.forward(req, res);
			}
		}
	}
			/*****************************************/
			private Gp_Ept_VO getGpEptNum(HttpServletRequest req) {
				
				
				String epc_no = req.getParameter("epc_no");
				String epc_name = req.getParameter("epc_name");			
				String epc_size = req.getParameter("epc_size");			
				Integer gp_t_num = new Integer(req.getParameter("gp_t_num")).intValue();
				
//				System.out.println(epc_no);
//				System.out.println(epc_name);
//				System.out.println(epc_size);
				System.out.println(gp_t_num);			
				
				Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();
				
				gp_ept_vo.setEpc_no(epc_no);
				gp_ept_vo.setGp_t_size(epc_size);
				gp_ept_vo.setGp_t_num(gp_t_num);
				return gp_ept_vo;
			}
			
			/*****************************************/

			
			
		}
		
	

