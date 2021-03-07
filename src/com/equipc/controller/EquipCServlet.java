package com.equipc.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.equipc.model.*;

public class EquipCServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("epc_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備分類編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/EquipC/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String epc_no = null;
				try {
					epc_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("裝備分類編號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/EquipC/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EquipCService equipCSvc = new EquipCService();
				EquipCVO equipCVO = equipCSvc.getByPK(epc_no);
				if (equipCVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/EquipC/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("equipCVO", equipCVO); // 資料庫取出的equipCVO物件,存入req
				String url = "/EquipC/listOneEquipc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquipC.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/EquipC/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) {// 來自listAllEquipC.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String epc_no = req.getParameter("epc_no");
				
				/***************************2.開始查詢資料****************************************/
				EquipCService equipCSvc = new EquipCService();
				EquipCVO equipCVO = equipCSvc.getByPK(epc_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("equipCVO", equipCVO);         // ��Ʈw���X��EquipCVO����,�s�Jreq
				String url = "/EquipC/update_equipc_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_EquipC_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/EquipC/listAllEquipc.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_EquipC_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

				String epc_no = req.getParameter("epc_no").trim();
				String epc_noReg = "^[A-Z]{2,20}$";
				if (epc_no == null || epc_no.trim().length() == 0) {
					errorMsgs.add("裝備分類編號: 請勿空白");
				} else if(!epc_no.trim().matches(epc_noReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備分類名稱: 只能是大寫英文字母 , 且長度必需在2到20之間");
	            }
				
				String epc_name = req.getParameter("epc_name");
				String epc_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
				if (epc_name == null || epc_name.trim().length() == 0) {
					errorMsgs.add("裝備分類名稱: 請勿空白");
				} else if(!epc_name.trim().matches(epc_nameReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備分類名稱: 只能是中文字母 , 且長度必需在2到20之間");
	            }
				
				EquipCVO equipCVO = new EquipCVO();
				equipCVO.setEpc_no(epc_no);
				equipCVO.setEpc_name(epc_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipCVO", equipCVO); // 含有輸入格式錯誤的equipCVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/EquipC/update_equipc_input.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EquipCService equipCSvc = new EquipCService();
				equipCVO = equipCSvc.updateEquipC(epc_no, epc_name);				
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("equipCVO", equipCVO); // 資料庫update成功後,正確的的EquipCVO物件,存入req
				String url = "/EquipC/listOneEquipc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEquipC.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/EquipC/update_equipc_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) {// 來自addEquipc.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String epc_no = req.getParameter("epc_no").trim();
				String epc_noReg = "^[A-Z]{2,20}$";
				if (epc_no == null || epc_no.trim().length() == 0) {
					errorMsgs.add("裝備分類編號: 請勿空白");
				} else if(!epc_no.trim().matches(epc_noReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備分類名稱: 只能是英文字母 , 且長度必需在2到20之間");
	            }
				
				String epc_name = req.getParameter("epc_name");
				String epc_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
				if (epc_name == null || epc_name.trim().length() == 0) {
					errorMsgs.add("裝備分類名稱: 請勿空白");
				} else if(!epc_name.trim().matches(epc_nameReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備分類名稱: 只能是中文字母 , 且長度必需在2到20之間");
	            }
				
				EquipCVO equipCVO = new EquipCVO();
				equipCVO.setEpc_no(epc_noReg);
				equipCVO.setEpc_name(epc_nameReg);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipCVO", equipCVO); // 含有輸入格式錯誤的equipCVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/EquipC/update_equipc_input.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				EquipCService equipCSvc = new EquipCService();
				equipCVO = equipCSvc.addEquipC(epc_no, epc_name);				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/EquipC/listAllEquipc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEquipC.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/EquipC/addEquipc.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String epc_no = req.getParameter("epc_no").trim();
				
				/***************************2.開始刪除資料***************************************/
				EquipCService equipCSvc = new EquipCService();
				equipCSvc.deleteEquipC(epc_no);				
					
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/EquipC/listAllEquipc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/EquipC/listAllEquipc.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
