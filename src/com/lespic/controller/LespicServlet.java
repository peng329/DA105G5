package com.lespic.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.lespic.model.LespicService;
import com.lespic.model.LespicVO;


@MultipartConfig
@WebServlet("/LespicServlet")
public class LespicServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/************ 1.接收請求參數-輸入格式錯誤處理 **************/
				String str = req.getParameter("lpic_seq");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入圖片流水號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/lespic_select_page.jsp");
					failure.forward(req, res);
					return;
				}
				String lpic_seq = null;
				try {
					lpic_seq = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/lespic_select_page.jsp");
					failure.forward(req, res);
					return; // 程式中斷
				}

				/********** 2.查詢資料(呼叫Service) ***************/
				LespicService lespicSvc = new LespicService();
				LespicVO lespicVO = lespicSvc.getOneLespic(lpic_seq);
				if (lespicVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lespic_select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/********* 3.查詢完成，準備轉交 ************/
				req.setAttribute("lespicVO", lespicVO);
				String url = "/back-end/lespic/listOneLespic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/******** 4.其他可能的錯誤處理 *********/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/lespic_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("erroeMsgs", errorMsgs);

			try {
				/********** 1.接收請求參數 *******************/
				String lpic_seq = req.getParameter("lpic_seq");
				/********** 2.開始查詢資料 ******************/
				LespicService lespicSvc = new LespicService();
				LespicVO lespicVO = lespicSvc.getOneLespic(lpic_seq);

				/********** 3.查詢完成，準備轉交 ***********/
				req.setAttribute("lespicVO", lespicVO);
				String url = "/back-end/lespic/update_lespic_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/********** 4.其他可能的錯誤處理 *********/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/listAllLespic.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/********* 1.接收請求-輸入格式錯誤處理 ************/

				String lpic_seq = req.getParameter("lpic_seq").trim();

				String les_no = new String(req.getParameter("les_no"));
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (les_no == null || les_no.trim().length() == 0) {
					errorMsgs.add("潛店編號:請勿空白");
				} else if (!les_no.trim().matches(ds_nameReg)) {
					errorMsgs.add("潛店編號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				byte[] lpic = null;
				InputStream in = req.getPart("lpic").getInputStream();
				Object temp = session.getAttribute("wrongImg");
				if (in.available() != 0) {
					lpic = new byte[in.available()];
					in.read(lpic);
					in.close();
				} else if(in.available() == 0 && temp!=null){
					 lpic = (byte[])temp;
				}else {
					try {
						LespicService lespicSvc = new LespicService();
						lpic = lespicSvc.getOneLespic(req.getParameter("lpic_seq")).getLpic();
					} catch (Exception e) {
						errorMsgs.add("請上傳圖片");
					}
				}
				
				LespicVO lespicVO = new LespicVO();
				lespicVO.setLpic_seq(lpic_seq);
				lespicVO.setLes_no(les_no);
				lespicVO.setLpic(lpic);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lespicVO", lespicVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/update_lespic_input.jsp");
					failureView.forward(req, res);
				}
				/******** 2.開始修改資料 *********/
				session.removeAttribute("wrongImg");
				LespicService lespicSvc = new LespicService();
				lespicVO = lespicSvc.updateLespic(lpic_seq, les_no, lpic);

				/******** 3.修改完成，準備轉交 *******/
				req.setAttribute("lespicVO", lespicVO);
				String url = "/back-end/lespic/listOneLespic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/********* 4.其他可能的錯誤處理 *******/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/update_lespic_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********** 1.接收請求參數 ************/
				String lpic_seq = req.getParameter("lpic_seq");

				/*********** 2.開始刪除資料 **********/
				LespicService lespicSvc = new LespicService();
				lespicSvc.deleteLespic(lpic_seq);

				/********* 3.刪除完成，轉交 ********/
				String url = "/back-end/lespic/listAllLespic.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);

				/********** 4.其他可能錯誤處理 **********/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/listAllLespic.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addLespic.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******** 1.接收請求參數，錯誤處理 ********/

				String les_no = req.getParameter("les_no");
								
				InputStream in = req.getPart("lpic").getInputStream();
				
				byte[] lpic = null;
				if (in.available() != 0) {
					lpic = new byte[in.available()];
					in.read(lpic);
					in.close();
				} else {
					errorMsgs.add("請上傳圖片");
				}

				LespicVO lespicVO = new LespicVO();
				lespicVO.setLpic(lpic);
				lespicVO.setLes_no(les_no);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lespicVO", lespicVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/addLespic.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************** 2.開始新增資料 **************/
				LespicService lespicSvc = new LespicService();
				lespicVO = lespicSvc.addLespic(les_no, lpic);

				/************* 3.新增完成，準備轉交 **********/

				String url = "/back-end/lespic/listAllLespic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/************* 4.其他可能的錯誤處理 **********/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lespic/addLespic.jsp");
				failureView.forward(req, res);
			}
		}
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}