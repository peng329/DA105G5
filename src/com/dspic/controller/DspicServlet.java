package com.dspic.controller;

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

import com.dspic.model.DspicService;
import com.dspic.model.DspicVO;

@MultipartConfig
@WebServlet("/DspicServlet")
public class DspicServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

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
				String str = req.getParameter("dpic_seq");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入圖片流水號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/dspic_select_page.jsp");
					failure.forward(req, res);
					return;
				}
				String dpic_seq = null;
				try {
					dpic_seq = new String(str);
				} catch (Exception e) {
					errorMsgs.add("流水號格是不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/dspic_select_page.jsp");
					failure.forward(req, res);
					return; // 程式中斷
				}

				/********** 2.查詢資料(呼叫Service) ***************/
				DspicService dspicSvc = new DspicService();
				DspicVO dspicVO = dspicSvc.getOneDspic(dpic_seq);
				if (dspicVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/dspic_select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/********* 3.查詢完成，準備轉交 ************/
				req.setAttribute("dspicVO", dspicVO);
				String url = "/back-end/dspic/listOneDspic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/******** 4.其他可能的錯誤處理 *********/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dspic_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("erroeMsgs", errorMsgs);

			try {
				/********** 1.接收請求參數 *******************/
				String dpic_seq = req.getParameter("dpic_seq");
				/********** 2.開始查詢資料 ******************/
				DspicService dspicSvc = new DspicService();
				DspicVO dspicVO = dspicSvc.getOneDspic(dpic_seq);

				/********** 3.查詢完成，準備轉交 ***********/
				req.setAttribute("dspicVO", dspicVO);
				String url = "/back-end/dspic/update_dspic_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/********** 4.其他可能的錯誤處理 *********/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/listAllDspic.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/********* 1.接收請求-輸入格式錯誤處理 ************/

				String dpic_seq = req.getParameter("dpic_seq").trim();

				String ds_no = new String(req.getParameter("ds_no"));
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ds_no == null || ds_no.trim().length() == 0) {
					errorMsgs.add("潛店編號:請勿空白");
				} else if (!ds_no.trim().matches(ds_nameReg)) {
					errorMsgs.add("潛店編號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				byte[] dpic = null;
				InputStream in = req.getPart("dpic").getInputStream();
				Object temp = session.getAttribute("wrongImg");
				if (in.available() != 0) {
					dpic = new byte[in.available()];
					in.read(dpic);
					in.close();
				} else if(in.available() == 0 && temp!=null){
					 dpic = (byte[])temp;
				}else {
					try {
						DspicService dspicSvc = new DspicService();
						dpic = dspicSvc.getOneDspic(req.getParameter("dpic_seq")).getDpic();
					} catch (Exception e) {
						errorMsgs.add("請上傳圖片");
					}
				}
				
				DspicVO dspicVO = new DspicVO();
				dspicVO.setDpic_seq(dpic_seq);
				dspicVO.setDs_no(ds_no);
				dspicVO.setDpic(dpic);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dspicVO", dspicVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/update_dspic_input.jsp");
					failureView.forward(req, res);
				}
				/******** 2.開始修改資料 *********/
				session.removeAttribute("wrongImg");
				DspicService dspicSvc = new DspicService();
				dspicVO = dspicSvc.updatedspic(ds_no, dpic, dpic_seq);

				/******** 3.修改完成，準備轉交 *******/
				req.setAttribute("dspicVO", dspicVO);
				String url = "/back-end/dspic/listOneDspic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/********* 4.其他可能的錯誤處理 *******/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/update_dspic_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********** 1.接收請求參數 ************/
				String dpic_seq = new String(req.getParameter("dpic_seq"));

				/*********** 2.開始刪除資料 **********/
				DspicService dspicSvc = new DspicService();
				dspicSvc.deleteDspic(dpic_seq);

				/********* 3.刪除完成，轉交 ********/
				String url = "/back-end/dspic/listAllDspic.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);

				/********** 4.其他可能錯誤處理 **********/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/listAllDspic.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addDspic.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******** 1.接收請求參數，錯誤處理 ********/

				String ds_no = req.getParameter("ds_no");
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ds_no == null || ds_no.trim().length() == 0) {
					errorMsgs.add("潛店編號:請勿空白");
				} else if (!ds_no.trim().matches(ds_nameReg)) {
					errorMsgs.add("潛店編號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}
				
				
				InputStream in = req.getPart("dpic").getInputStream();
				
				byte[] dpic = null;
				if (in.available() != 0) {
					dpic = new byte[in.available()];
					in.read(dpic);
					in.close();
				} else {
					errorMsgs.add("請上傳圖片");
				}

				DspicVO dspicVO = new DspicVO();
				dspicVO.setDpic(dpic);
				dspicVO.setDs_no(ds_no);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dspicVO", dspicVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/addDspic.jsp");
					System.out.println("123");
					failureView.forward(req, res);
					return;
				}

				/*************** 2.開始新增資料 **************/
				DspicService dspicSvc = new DspicService();
				dspicVO = dspicSvc.addDspic(ds_no, dpic);

				/************* 3.新增完成，準備轉交 **********/

				String url = "/back-end/dspic/listAllDspic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/************* 4.其他可能的錯誤處理 **********/

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/addDspic.jsp");
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
