package com.eqpic.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.eqpic.model.*;
@MultipartConfig
public class EqpicServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=Big5");
		String action = req.getParameter("action");
		
		
		if ("getADS_All_Display".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ds_no = req.getParameter("ds_no");
				if (ds_no== null || (ds_no.trim()).length() == 0) {
					errorMsgs.add("請輸入潛店編號");
				}if(ds_no.trim().length() != 6){
					errorMsgs.add("潛店編號長度錯誤");
					}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/eqpic/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EqpicService eqpicSvc = new EqpicService();
				List<EqpicVO> eqpicVO = eqpicSvc.findByDsAll(ds_no);
				if (eqpicVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/eqpic/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqpicVO", eqpicVO); // 資料庫取出的eqpicVO物件,存入req
				String url = "/eqpic/ADS_All_Display.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEqpic.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/eqpic/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("get_EPAL_Display".equals(action)) {// 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ep_seq = req.getParameter("ep_seq");
				if (ep_seq == null || (ep_seq.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備流水號");
				}if(ep_seq.trim().length()!=6) {
					errorMsgs.add("裝備流水號長度錯誤");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/eqpic/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String ep_name = req.getParameter("ep_name");
				
				/***************************2.開始查詢資料*****************************************/
				EqpicService eqpicSvc = new EqpicService();
				List<EqpicVO> eqpicVO = eqpicSvc.findByEpAll(ep_seq);
				if (eqpicVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/eqpic/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqpicVO", eqpicVO); // 資料庫取出的eqpicVO物件,存入req
				String url = "/back-end/eqpic/EP_All_Display.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEqpic.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqpic/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ("getOne_For_Update".equals(action)) {// 來自listAllEqpic.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String epic_seq= req.getParameter("epic_seq");
				/***************************2.開始查詢資料****************************************/
				EqpicService eqpicSvc = new EqpicService();
				EqpicVO eqpicVO = eqpicSvc.getOneEpic(epic_seq);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("eqpicVO", eqpicVO);         //資料庫取出的eqpicVO物件,存入req
				String url = "/back-end/eqpic/update_eqpic_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交update_eqpic_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqpic/ADS_All_Display.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_Eqpic_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String epic_seq = req.getParameter("epic_seq").trim();
				String ep_seq = req.getParameter("ep_seq");
				String ep_seqReg = "^[A-Z0-9]{2,20}$";
				if (ep_seq == null || ep_seq.trim().length() == 0) {
					errorMsgs.add("裝備流水號: 請勿空白");
				} else if(!ep_seq.trim().matches(ep_seqReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備流水號 : 只能是英文字母加數字, 且長度必需在2到20之間");
	            }
				
				byte[] epic = null;
				InputStream in =req.getPart("epic").getInputStream();
				System.out.println(in);
				if(in.available()!=0){
					epic = new byte[in.available()];
					in.read(epic);
					in.close();
				}else errorMsgs.add("裝備圖片：請上傳裝備圖片");
			
				EqpicVO eqpicVO = new EqpicVO();
				eqpicVO.setEpic_seq(epic_seq);
				eqpicVO.setEp_seq(ep_seq);
				eqpicVO.setEpic(epic);				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eqpicVO", eqpicVO); // 含有輸入格式錯誤的eqpicCVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqpic/update_eqpic_input.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EqpicService eqpicSvc = new EqpicService();
				eqpicVO = eqpicSvc.updateEqpic(epic_seq,ep_seq, epic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqpicVO", eqpicVO); // 資料庫update成功後,正確的的EqpicVO物件,存入req
				String url = "/back-end/eqpic/listOneEqpic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEqpic.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} 
				catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqpic/update_eqpic_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) {// 來自addEqpicc.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String ds_no = req.getParameter("ds_no");
				String ds_noReg = "^[(A-Z0-9)]{2,20}$";
				if (ds_no == null || ds_no.trim().length() == 0) {
					errorMsgs.add("潛店編號: 請勿空白");
				} else if(!ds_no.trim().matches(ds_noReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店編號: 只能是英文字母 加數字, 且長度必需在2到20之間");
	            }
				
				String ep_seq = req.getParameter("ep_seq");
				String ep_seqReg = "^[(A-Z0-9)]{2,20}$";
				if (ep_seq == null || ep_seq.trim().length() == 0) {
					errorMsgs.add("裝備流水號: 請勿空白");
				} else if(!ep_seq.trim().matches(ep_seqReg)) {  //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("裝備流水號: 只能是大寫英文字母加數字, 且長度必需在2到20之間");
	            }
				List<EqpicVO> list=new ArrayList<EqpicVO>();
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					String filename = getFileNameFromPart(part);
					if (filename!=null&& part.getContentType()!=null) {
						InputStream in = part.getInputStream();
						byte[] epic = new byte[in.available()];
						in.read(epic);
						EqpicVO eqpicVO = new EqpicVO();
						eqpicVO.setDs_no(ds_no);
						eqpicVO.setEp_seq(ep_seq);
						eqpicVO.setEpic(epic);
						in.close();
						list.add(eqpicVO);
					}
				}	
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("list", list); // 含有輸入格式錯誤的EqpicVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqpic/addEpic.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************2.開始新增資料***************************************/
				HttpSession session = req.getSession();
				if (session.getAttribute("A") != null) {
					for(int i=0;i<list.size();i++) {
					EqpicService eqpicSvc = new EqpicService();
					eqpicSvc.addEqpic(list.get(i).getDs_no(),list.get(i).getEp_seq(),list.get(i).getEpic());
					}
				}
				session.removeAttribute("A");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/eqpic/EP_All_Display.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEqpicC.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/eqpic/addEpic.jsp");
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
				String epic_seq = req.getParameter("epic_seq");
				String ep_seq = req.getParameter("ep_seq");
				/***************************2.開始刪除資料***************************************/
				EqpicService EqpicSvc = new EqpicService();
				EqpicSvc.deleteEqpic(epic_seq);				
					
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/Eqpic/eqpic.do?action=get_EP_All_Display&ep_seq="+ep_seq;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/eqpic/EP_All_Display.jsp");
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
