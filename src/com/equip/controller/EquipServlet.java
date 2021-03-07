package com.equip.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.eqpic.model.EqpicVO;
import com.equip.model.*;

@MultipartConfig
public class EquipServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Eq".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("ep_seq");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備流水號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String ep_seq = null;
				try {
					ep_seq = new String(str);
				} catch (Exception e) {
					errorMsgs.add("裝備流水號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EquipService equipSvc = new EquipService();
				EquipVO equipVO = equipSvc.getOneEq(ep_seq);
				if (equipVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/equip/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("equipVO", equipVO); // 資料庫取出的equipVO物件,存入req
				String url = "/equip/listOneEquip.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {// 來自listAllEquip.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
//			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String ep_seq = req.getParameter("ep_seq");

				/*************************** 2.開始查詢資料 ****************************************/
				EquipService equipSvc = new EquipService();
				EquipVO equipVO = equipSvc.getOneEq(ep_seq);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("equipVO", equipVO); // 資料庫取出的equipVO物件,存入req

				boolean openModal=true;
				req.setAttribute("openModal",openModal );
				
//				String url = "/back-end/equip/listAllDSEquipByDs_no.jsp";
				String url = "/back-end/equip/update_equip_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
				return;
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/listAllDSEquipByDs_no.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_DS_EQAll".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ds_no = req.getParameter("ds_no");
				System.out.println(ds_no);
				if (ds_no == null || (ds_no.trim()).length() == 0) {
					errorMsgs.add("請輸入潛店編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EquipService equipSvc = new EquipService();
				List<EquipVO> list = equipSvc.getDSAll(ds_no);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("list", list); // 資料庫取出的equipVO物件,存入req
				String url = "/equip/listAllDSEquipByDs_no.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquip.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_Equip_input.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ep_seq = req.getParameter("ep_seq");
				String epc_no = req.getParameter("epc_no").trim();
				if (epc_no == null|| epc_no.trim().length() == 0) {
					errorMsgs.add("裝備分類編號請勿留空");
				}

				String ds_no = req.getParameter("ds_no");
				String ds_noReg = "^[(A-Z0-9)]{2,20}$";
				if (ds_no == null || ds_no.trim().length() == 0) {
					errorMsgs.add("潛店編號: 請勿空白");
				} else if (!ds_no.trim().matches(ds_noReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店編號: 只能是英文字母 加數字, 且長度必需在2到20之間");
				}

				String ds_name = req.getParameter("ds_name");
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (ds_name == null || ds_name.trim().length() == 0) {
					errorMsgs.add("潛店名稱: 請勿空白");
				} else if (!ds_name.trim().matches(ds_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店名稱:只能是中、英文字母、數字或_, 且長度必需在2到20之間");
				}

				String ep_no = req.getParameter("ep_no");
				String ep_noReg = "^[(A-Z0-9)]{2,20}$";
				if (ep_no == null || ep_no.trim().length() == 0) {
					errorMsgs.add("裝備編號: 請勿空白");
				} else if (!ep_no.trim().matches(ep_noReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備編號: 只能是大寫英文字母加數字, 且長度必需在2到20之間");
				}

				String ep_name = req.getParameter("ep_name");
				String ep_nameReg = "^[(\u4e00-\u9fa5)(A-Z0-9)]{2,20}$";
				if (ep_name == null || ep_name.trim().length() == 0) {
					errorMsgs.add("裝備名稱: 請勿空白");
				} else if (!ep_name.trim().matches(ep_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備名稱: 只能是中、英文字, 且長度必需在2到20之間");
				}

				String ep_size = req.getParameter("ep_size");
				if (ep_size == null) {
					errorMsgs.add("裝備尺寸: 請勿空白");
				}

				Integer ep_priz = null;
				try {
					ep_priz = new Integer(req.getParameter("ep_priz").trim());
					if (ep_priz == 0) {
						errorMsgs.add("價格大於零");
					}
				} catch (NumberFormatException ne) {
					ep_priz = 0;
					errorMsgs.add("價格請填數字");
				}

				Integer ep_rp = null;
				try {
					ep_rp = new Integer(req.getParameter("ep_rp").trim());
					if (ep_rp == 0) {
						errorMsgs.add("租金請大於零");
					}
				} catch (NumberFormatException ne) {
					ep_rp = 0;
					errorMsgs.add("租金請填數字");
				}

				String ep_state = req.getParameter("ep_state");
				if (ep_state == null) {
					errorMsgs.add("裝備狀態請勿空白");
				}

				String epr_state = req.getParameter("epr_state");
				if (epr_state == null) {
					errorMsgs.add("裝備租賃狀態請勿空白");
				}

				String eps_state = req.getParameter("eps_state");
				if (eps_state == null) {
					errorMsgs.add("裝備陳列狀態請勿空白");
				}

				EquipVO equipVO = new EquipVO();
				equipVO.setEp_no(ep_seq);
				equipVO.setEpc_no(epc_no);
				equipVO.setDs_no(ds_no);
				equipVO.setDs_name(ds_name);
				equipVO.setEp_name(ep_name);
				equipVO.setEp_no(ep_no);
				equipVO.setEp_size(ep_size);
				equipVO.setEp_priz(ep_priz);
				equipVO.setEp_rp(ep_rp);
				equipVO.setEp_state(ep_state);
				equipVO.setEpr_state(epr_state);
				equipVO.setEps_state(eps_state);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipVO", equipVO); // 含有輸入格式錯誤的equipCVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/update_equip_input.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EquipService equipSvc = new EquipService();
				equipVO = equipSvc.updateEquip(ep_seq, epc_no, ds_no, ds_name, ep_name, ep_no, ep_size, ep_priz, ep_rp,
						ep_state, epr_state, eps_state);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("equipVO", equipVO); // 資料庫update成功後,正確的的EquipVO物件,存入req
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEquip.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/update_equip_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String ep_seq = req.getParameter("ep_seq").trim();

				/*************************** 2.開始刪除資料 ***************************************/
				EquipService equipSvc = new EquipService();
				equipSvc.deleteEquip(ep_seq);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/equip/listAllEquip.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/equip/listAllEquip.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insertBUP".equals(action)) {// 來自addEquipc.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String epc_no = req.getParameter("epc_no").trim();
				if (epc_no == null|| epc_no.trim().length() == 0) {
					errorMsgs.add("裝備分類編號請勿留空");
				}
				System.out.println(epc_no);
				String ds_no = req.getParameter("ds_no");
				String ds_noReg = "^[(A-Z0-9)]{2,20}$";
				if (ds_no == null || ds_no.trim().length() == 0) {
					errorMsgs.add("潛店編號: 請勿空白");
				} else if (!ds_no.trim().matches(ds_noReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店編號: 只能是英文字母 加數字, 且長度必需在2到20之間");
				}

				String ds_name = req.getParameter("ds_name");
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (ds_name == null || ds_name.trim().length() == 0) {
					errorMsgs.add("潛店名稱: 請勿空白");
				} else if (!ds_name.trim().matches(ds_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店名稱:只能是中、英文字母、數字或_, 且長度必需在2到20之間");
				}

				String ep_no = req.getParameter("ep_no");
				String ep_noReg = "^[(A-Z0-9)]{2,20}$";
				if (ep_no == null || ep_no.trim().length() == 0) {
					errorMsgs.add("裝備編號: 請勿空白");
				} else if (!ep_no.trim().matches(ep_noReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備編號: 只能是大寫英文字母加數字, 且長度必需在2到20之間");
				}

				String ep_name = req.getParameter("ep_name");
				String ep_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
				if (ep_name == null || ep_name.trim().length() == 0) {
					errorMsgs.add("裝備名稱: 請勿空白");
				} else if (!ep_name.trim().matches(ep_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備名稱: 只能是中文字, 且長度必需在2到20之間");
				}

				Integer ep_priz = 0;
				try {
					ep_priz = new Integer(req.getParameter("ep_priz").trim());
					if (ep_priz == 0||ep_priz==null) {
						errorMsgs.add("價格大於零");
					}
				} catch (NumberFormatException ne) {
					ep_priz = 0;
					errorMsgs.add("價格請填數字");
				}

				Integer ep_rp = 0;
				try {
					ep_rp = new Integer(req.getParameter("ep_rp").trim());
					if (ep_rp == 0||ep_rp==null) {
						errorMsgs.add("租金請大於零");
					}
				} catch (NumberFormatException ne) {
					ep_rp = 0;
					errorMsgs.add("租金請填數字");
				}

				String ep_size = req.getParameter("ep_size");
				if (ep_size == null) {
					errorMsgs.add("裝備尺寸: 請勿空白");
				}

				String ep_state = req.getParameter("ep_state");
				if (ep_state == null) {
					errorMsgs.add("裝備狀態請勿空白");
				}

				String epr_state = req.getParameter("epr_state");
				if (epr_state == null) {
					errorMsgs.add("裝備租賃狀態請勿空白");
				}

				String eps_state = req.getParameter("eps_state");
				if (eps_state == null) {
					errorMsgs.add("裝備陳列狀態請勿空白");
				}

				EquipVO equipVO = new EquipVO();
				equipVO.setEpc_no(epc_no);
				equipVO.setDs_no(ds_no);
				equipVO.setDs_name(ds_name);
				equipVO.setEp_name(ep_name);
				equipVO.setEp_no(ep_no);
				equipVO.setEp_size(ep_size);
				equipVO.setEp_priz(ep_priz);
				equipVO.setEp_rp(ep_rp);
				equipVO.setEp_state(ep_state);
				equipVO.setEpr_state(epr_state);
				equipVO.setEps_state(eps_state);

				List<EqpicVO> list=new ArrayList<EqpicVO>();
				
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					String filename = getFileNameFromPart(part);
					if (filename!=null&& part.getContentType()!=null) {
						InputStream in = part.getInputStream();
						byte[] epic = new byte[in.available()];
						in.read(epic);
						EqpicVO eqpicVO = new EqpicVO();
						eqpicVO.setEpic(epic);
						in.close();
						list.add(eqpicVO);
					}
				}	

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipVO", equipVO); // 含有輸入格式錯誤的equipVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/addEquipByBUP.jsp?ds_no="+equipVO.getDs_no());
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				EquipService equipSvc = new EquipService();
				equipSvc.addBEquip(equipVO,list);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("equipVO", equipVO);
				String url = "/back-end/equip/listAllDSEquipByDs_no.jsp?ds_no="+equipVO.getDs_no();
				System.out.println(url);
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEquipC.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/addEquipByBUP.jsp");
				failureView.forward(req, res);
			}
		}

	
	if("Equipment_ByCompositeQuery".equals(action)) {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
	
		try {
			
			/***********************將輸入資料轉成Map**************************************/
			Map<String,String[]> map = req.getParameterMap();
			/***************************開始複合查詢**************************************/
			EquipService equipSvc = new EquipService();
			List<EquipVO> list = equipSvc.getAll(map);
			/***************************查詢完成，開始轉交****************************************************************/
			req.setAttribute("Equipment_ByCompositeQuery",list);
			RequestDispatcher successView = req.getRequestDispatcher("/equip/search_page.jsp");
			successView.forward(req,res);
		}catch(Exception e){
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/equip/select_page.jsp");
			failureView.forward(req, res);
		}	
	}
}
	
	public String getFileNameFromPart(Part part) {

		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); 
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();		
		
		System.out.println("filename=" + filename);
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
