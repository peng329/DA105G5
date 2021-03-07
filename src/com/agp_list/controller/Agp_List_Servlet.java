package com.agp_list.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.act_list.model.Act_List_Service;
import com.act_list.model.Act_List_VO;
import com.agp_list.model.Agp_List_Service;
import com.agp_list.model.Agp_List_VO;
import com.gp_ept.model.Gp_Ept_Service;
import com.gp_ept.model.Gp_Ept_VO;
import com.mem.model.MemVO;

@MultipartConfig
public class Agp_List_Servlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		System.out.println("執行AGP");
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
				String str2 = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入揪團編號");
				}
				if (str2 == null || (str2.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String act_list_no = null;
				String mem_no = null;

				try {
					act_list_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("揪團編號格式不正確");
				}
				try {
					mem_no = new String(str2);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				Agp_List_VO agp_list_vo = agp_list_Svc.getOneAGP_LIST(act_list_no, mem_no);
				if (agp_list_vo == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("agp_list_vo", agp_list_vo); // 資料庫取出的agp_list_vo物件,存入req
				String url = "/front-end/agp_list/listOneAgp_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAgp_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		/*****************************************
		 * 來自listAllAct_List.jsp的請求
		 *********************************************/
		if ("getOne_For_Update".equals(action)) { // 來自listAllAgp_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String act_list_no = new String(req.getParameter("act_list_no"));
				String mem_no = new String(req.getParameter("mem_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				Agp_List_VO agp_list_vo = agp_list_Svc.getOneAGP_LIST(act_list_no, mem_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("agp_list_vo", agp_list_vo); // 資料庫取出的agp_list_vo物件,存入req
				String url = "/front-end/agp_list/update_agp_list_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAct_List.jsp");
				failureView.forward(req, res);
			}
		}

		/******************************************
		 * 來自update_act_list_input.jsp的請求
		 **********************************/
		if ("update".equals(action)) { // 來自update_agp_list_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
				String act_list_no = new String(req.getParameter("act_list_no").trim());
				if (act_list_no == null || act_list_no.trim().length() == 0) {
					errorMsgs.add("揪團編號請勿空白");
				}
				/*************************** mem_no團主會員編號編號錯誤處理 ***********************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("團主會員編號請勿空白");
				}
				/*************************** mem_lic潛水證編號錯誤處理 *******************************/
				String mem_lic = req.getParameter("mem_lic").trim();
				if (mem_lic == null || mem_lic.trim().length() == 0) {
					errorMsgs.add("潛水證編號請勿空白");
				}
				/*************************** act_num報名人數錯誤處理 ************************/
				Integer act_num = null;
				try {
					act_num = new Integer(req.getParameter("act_num").trim());
				} catch (NumberFormatException e) {
					act_num = 1;
					errorMsgs.add("報名人數請填數字.");
				}
				/*************************** mem_lic_pic潛水證圖片錯誤處理 *****************************/
//				
//				InputStream in =req.getPart("mem_lic_pic").getInputStream();
				byte[] mem_lic_pic = null;
//				if (in.available()!=0) {
//					mem_lic_pic = new byte[in.available()];
//					in.read(mem_lic_pic);
//					in.close();
//				}else {
//					errorMsgs.add("潛水證圖片請勿空白");
//				}
				Agp_List_Service agpSvc =new Agp_List_Service();
				Agp_List_VO agp_list_vo2 = agpSvc.getOneAGP_LIST(act_list_no, mem_no);
					mem_lic_pic =agp_list_vo2.getMem_lic_pic();

				/*************************** agp_state報名狀態錯誤處理 ****************************/
				String agp_state = req.getParameter("agp_state").trim();
				if (agp_state == null || agp_state.trim().length() == 0) {
					errorMsgs.add("報名狀態請勿空白");
				}
				/*************************************************************************/

				Agp_List_VO agp_list_vo = new Agp_List_VO();

				agp_list_vo.setMem_lic(mem_lic);
				agp_list_vo.setAct_num(act_num);
				agp_list_vo.setMem_lic_pic(mem_lic_pic);
				agp_list_vo.setAgp_state(agp_state);
				agp_list_vo.setAct_list_no(act_list_no);
				agp_list_vo.setMem_no(mem_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("agp_list_vo", agp_list_vo); // 含有輸入格式錯誤的agp_list_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/update_agp_list_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				agp_list_vo = agp_list_Svc.updateAGP_LIST(mem_lic,act_num,mem_lic_pic,agp_state,
				act_list_no,mem_no);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("agp_list_vo", agp_list_vo); // 資料庫update成功後,正確的的agp_list_vo物件,存入req
				String url = "/front-end/agp_list/listAllAgp_List.jsp";
				HttpSession session = req.getSession();

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				Act_List_Service act_list_Svc2 = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc2.getGroup(mem_vo.getMem_no());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/

				Act_List_Service act_list_Svc3 = new Act_List_Service();
				
				Set<Act_List_VO> act_list1 =  act_list_Svc3.getFreeGroup(mem_vo.getMem_no(), "發團中");
				
//				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				
//				Set<Agp_List_VO> agp_list =  agp_list_Svc.getMem_State("待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("JoinManagementToLeader",act_list1); 
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("filter_act_list_vo", act_list); // 資料庫取出的act_list_vo物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAgp_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/update_agp_list_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*****************************************
		 * 來自addAct_List.jsp的請求
		 ******************************************/
		if ("insert".equals(action)) { // 來自addAgp_List.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
//				String act_list_no = new String(req.getParameter("act_list_no").trim());
//				if (act_list_no == null || act_list_no.trim().length() == 0) {
//					errorMsgs.add("揪團編號請勿空白");
//				}
				/*************************** mem_no團主會員編號編號錯誤處理 ***********************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				/*************************** mem_lic潛水證編號錯誤處理 *******************************/
				String mem_lic = req.getParameter("mem_lic").trim();
				if (mem_lic == null || mem_lic.trim().length() == 0) {
					errorMsgs.add("潛水證編號請勿空白");
				}
				/*************************** act_num報名人數錯誤處理 ************************/
				Integer act_num = null;
				try {
					act_num = new Integer(req.getParameter("act_num").trim());
				} catch (NumberFormatException e) {
					act_num = 1;
					errorMsgs.add("報名人數請填數字.");
				}
				/*************************** mem_lic_pic潛水證圖片錯誤處理 *****************************/
				
				InputStream in =req.getPart("mem_lic_pic").getInputStream();
				byte[] mem_lic_pic = null;
				if (in.available()!=0) {
					mem_lic_pic = new byte[in.available()];
					in.read(mem_lic_pic);
					in.close();
				}else {
					errorMsgs.add("潛水證圖片請勿空白");
				}

				/*************************** agp_state報名狀態錯誤處理 ****************************/
				String agp_state = req.getParameter("agp_state").trim();
				if (agp_state == null || agp_state.trim().length() == 0) {
					errorMsgs.add("報名狀態請勿空白");
				}
				/*************************************************************************/
				HttpSession session = req.getSession();
				String act_list_no = (String) session.getAttribute("act_list_no");
				System.out.println(act_list_no);
//				String mem_no =(String) session.getAttribute("mem_no");
//				System.out.println(mem_no);
				Agp_List_VO agp_list_vo = new Agp_List_VO();

				agp_list_vo.setMem_lic(mem_lic);
				agp_list_vo.setAct_num(act_num);
				agp_list_vo.setMem_lic_pic(mem_lic_pic);
				agp_list_vo.setAgp_state(agp_state);
				agp_list_vo.setAct_list_no(act_list_no);
				agp_list_vo.setMem_no(mem_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("agp_list_vo", agp_list_vo); // 含有輸入格式錯誤的agp_list_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/update_agp_list_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				agp_list_vo = agp_list_Svc.addAGP_LIST(act_list_no, mem_no, mem_lic, act_num, mem_lic_pic, agp_state);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				session.removeAttribute("act_list_no");
				String url = "/front-end/agp_list/listAllAgp_List1.jsp";
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc4 = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list4 =  agp_list_Svc4.getJoinGroup(mem_vo.getMem_no(), "待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management", agp_list4); // 資料庫取出的act_list_vo物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAgp_List.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/addAgp_List.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*****************************************
		 * 來自listAllAct_List.jsp的delete
		 **********************************/
		if ("delete".equals(action)) { // 來自listAllAgp_List.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String act_list_no = new String(req.getParameter("act_list_no"));
				String mem_no = new String(req.getParameter("mem_no"));
//				String gp_ept_no = new String(req.getParameter("gp_ept_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
//				gp_ept_Svc.deleteGP_EPT(gp_ept_no);
				agp_list_Svc.deleteAGP_LIST(act_list_no,mem_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				HttpSession session = req.getSession();

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc2 = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list1 =  agp_list_Svc2.getJoinGroup(mem_vo.getMem_no(), "待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management", agp_list1); // 資料庫取出的act_list_vo物件,存入req
Act_List_Service act_list_Svc3 = new Act_List_Service();
				
				Set<Act_List_VO> act_list3 =  act_list_Svc3.getFreeGroup(mem_vo.getMem_no(), "發團中");
				
//				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				
//				Set<Agp_List_VO> agp_list =  agp_list_Svc.getMem_State("待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("JoinManagementToLeader",act_list3); 
				String url = "/front-end/agp_list/listAllAgp_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAgp_List.jsp");
				failureView.forward(req, res);
			}
		}
		

		/*****************************************
		 * 來自入團請求
		 **********************************/
		if ("nextgroup".equals(action)) { // 來自listAllAgp_List.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				HttpSession session = req.getSession();
//				String act_list_no = (String) session.getAttribute("act_list_no");
				String act_list_no = req.getParameter("act_list_no");
				String mem_no =req.getParameter("mem_no");
//				String mem_no =(String) session.getAttribute("mem_no");

				/*************************** 2.開始刪除資料 ***************************************/
			

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				session.setAttribute("act_list_no", act_list_no);
				session.setAttribute("mem_no", mem_no);
				String url = "/front-end/agp_list/addAgp_List.jsp";

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc1 = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list1 =  agp_list_Svc1.getJoinGroup(mem_vo.getMem_no(), "待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management", agp_list1); // 資料庫取出的act_list_vo物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/addAgp_List.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************
		 * 來自listAllAgp_List1.jsp的請求
		 *********************************************/
		if ("Join_management".equals(action)) { // 來自listAllAct_List.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				HttpSession session = req.getSession();

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list =  agp_list_Svc.getJoinGroup(mem_vo.getMem_no(), "待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management", agp_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/agp_list/listAllAgp_List1.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAgp_List1.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************
		 * 來自listAllAgp_List.jsp的請求
		 *********************************************/
		if ("Join_management2".equals(action)) { // 來自listAllAct_List.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
//			try {
				/*************************** 1.接收請求參數 ****************************************/
				HttpSession session = req.getSession();
				
				
				
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc.getFreeGroup(mem_vo.getMem_no(), "發團中");
				
//				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				
//				Set<Agp_List_VO> agp_list =  agp_list_Svc.getMem_State("待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("JoinManagementToLeader",act_list); 
//				req.setAttribute("JoinManagementToLeader", agp_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/agp_list/listAllAgp_List.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/agp_list/listAllAgp_List.jsp");
//				failureView.forward(req, res);
//			}
		}
		/*****************************************
		 * 來自listAllAgp_List2.jsp的請求
		 *********************************************/
		if ("Join_management1".equals(action)) { // 來自listAllAct_List.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				HttpSession session = req.getSession();
				
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list =  agp_list_Svc.getJoinGroup(mem_vo.getMem_no(), "通過");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management1", agp_list); // 資料庫取出的act_list_vo物件,存入req
				String url = "/front-end/agp_list/listAllAgp_List2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_act_1ist_input.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAgp_List2.jsp");
				failureView.forward(req, res);
			}
		}
	}
	
}
