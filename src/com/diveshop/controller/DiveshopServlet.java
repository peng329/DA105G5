package com.diveshop.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.diveshop.model.DiveshopService;
import com.diveshop.model.DiveshopVO;
import com.dspic.model.DspicService;
import com.dspic.model.DspicVO;
import com.login.MailService2;
import com.mem.model.MemService;

@WebServlet("/DiveshopServlet")
@MultipartConfig
public class DiveshopServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getByAddress".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******* 1.接收請求參數-輸入格式錯誤處理 *******/
				String str = req.getParameter("address");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入縣市名稱");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				String address = null;
				try {
					address = new String(str);
				} catch (Exception e) {
					errorMsgs.add("地址格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/******* 2.開始查詢資料 *******/
				DiveshopService diveshopSvc = new DiveshopService();
				Set<DiveshopVO> diveshopSet = diveshopSvc.getDiveshopByAddress(address);
				if (diveshopSet == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********* 3.查詢成功，開始轉交 **********/
				req.setAttribute("diveshopSet", diveshopSet);
				String url = "/back-end/diveshop/listAllDiveshop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display".equals(action)) { // select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/********** 1.接收請求參數-輸入格式錯誤處理 **********/
				String str = req.getParameter("ds_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入潛店編號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String ds_no = null;
				try {
					ds_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("潛店編號格式不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/************* 2.開始查詢資料 ****************/
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				if (diveshopVO == null) {
					errorMsgs.add("查無資料");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************** 3.查詢完成,準備轉交(send the Success view) ************************/
				req.setAttribute("diveshopVO", diveshopVO);
				String url = "/front-end/diveshop/listOneDiveshop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);

				/*********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/diveshop_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage
			// view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/******** 1.接收請求參數 ********/
				String ds_no = new String(req.getParameter("ds_no"));

				/******** 2.開始查詢資料 ***************/
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);

				/******** 3.查詢完成，轉交 ****************/
				req.setAttribute("diveshopVO", diveshopVO);
				String url = "/back-end/diveshop/update_diveshop_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				// 成功轉交update_diveshop_input.jsp
				successView.forward(req, res);

				/******** 4.其他可能的錯誤處理 *****************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_diveshop_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {

				/********** 1.接收請求參數 - 輸入格式錯誤處理 *************/
				String ds_no = new String(req.getParameter("ds_no").trim());

				String ds_name = req.getParameter("ds_name");
				String ds_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ds_name == null || ds_name.trim().length() == 0) {
					errorMsgs.add("潛店名稱:請勿空白");
				} else if (!ds_name.trim().matches(ds_nameReg)) {
					errorMsgs.add("潛店名稱:只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				Integer brcid = null;
				try {
					brcid = new Integer(req.getParameter("brcid").trim());
				} catch (NumberFormatException e) {
					brcid = 12345678;
					errorMsgs.add("營業登記證請填數字");
				}

				String tel = req.getParameter("tel").trim();
				if (tel == null || tel.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}

				String dsaccount = req.getParameter("dsaccount").trim();
				if (dsaccount == null || dsaccount.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}

				String dspaw = req.getParameter("dspaw").trim();
				if (dspaw == null || dspaw.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}

				String dsmail = req.getParameter("dsmail").trim();
				if (dsmail == null || dsmail.trim().length() == 0) {
					errorMsgs.add("信箱請勿空白");
				}

				String dsinfo = req.getParameter("dsinfo").trim();
				if (dsinfo == null || dsinfo.trim().length() == 0) {
					errorMsgs.add("潛店介紹請勿空白");
				}

				String ds_latlng = req.getParameter("ds_latlng").trim();
				if (ds_latlng == null || ds_latlng.trim().length() == 0) {
					errorMsgs.add("緯經度請勿空白");
				}

				String ds_state = req.getParameter("ds_state").trim();
				if (ds_state == null || ds_state.trim().length() == 0) {
					errorMsgs.add("潛店狀態請勿空白");
				}

				Integer ds_ascore = null;
				try {
					ds_ascore = new Integer(req.getParameter("ds_ascore").trim());
				} catch (NumberFormatException e) {
					ds_ascore = 00;
					errorMsgs.add("請輸入潛店評分");
				}
				Integer ds_rep_no = null;
				try {
					ds_rep_no = new Integer(req.getParameter("ds_rep_no").trim());
					if ((ds_rep_no) > 100) {
						errorMsgs.add("檢舉次數不得超過100");
					}
				} catch (NumberFormatException e) {
					ds_rep_no = 00;
					errorMsgs.add("請輸入潛店被檢舉次數");
				}
				DiveshopVO diveshopVO = new DiveshopVO();
				diveshopVO.setDs_no(ds_no);
				diveshopVO.setDs_name(ds_name);
				diveshopVO.setBrcid(brcid);
				diveshopVO.setTel(tel);
				diveshopVO.setAddress(address);
				diveshopVO.setDsaccount(dsaccount);
				diveshopVO.setDspaw(dspaw);
				diveshopVO.setDsmail(dsmail);
				diveshopVO.setDsinfo(dsinfo);
				diveshopVO.setDs_latlng(ds_latlng);
				diveshopVO.setDs_state(ds_state);
				diveshopVO.setDs_ascore(ds_ascore);
				diveshopVO.setDs_rep_no(ds_rep_no);

				// 先以dpic_seq刪除被勾選的圖片
				String[] dpic_seq_delete = null;
				DspicService dspicSvc = new DspicService();
				try {
					dpic_seq_delete = req.getParameterValues("photo_delete");
				} catch (Exception e) {
				}
				// 如果勾選才執行
				if (dpic_seq_delete != null) {
					for (int i = 0; i < dpic_seq_delete.length; i++) {
						dspicSvc.deleteDspic(dpic_seq_delete[i]);
					}
				}

				// 接收前端上傳的檔案
				// 1.取得有勾選的照片編號
				try {
					String[] dpic_seq_update = req.getParameterValues("photo_update");
					List<Part> parts = (ArrayList<Part>) req.getParts();
					int update_count = 0;
					for (int i = 0; i < parts.size(); i++) {
						if ("upfile".equals(parts.get(i).getName())) {
							String filename = getFileNameFromPart(parts.get(i));
							if (filename != null && parts.get(i).getContentType() != null) {
								// 有勾選修改並實際有上傳新照片的
								InputStream in = parts.get(i).getInputStream();
								byte[] buf = new byte[in.available()];
								in.read(buf);
								in.close();
								dspicSvc.updatedspic(ds_no, buf, dpic_seq_update[update_count]);
							}
							update_count++; // 加一換下一張
						}
					}
				} catch (Exception e) {

				}
				try {
					List<Part> parts = (ArrayList<Part>) req.getParts();
					for (int i = 0; i < parts.size(); i++) {
						if ("addPics".equals(parts.get(i).getName())) {
							String filename = getFileNameFromPart(parts.get(i));
							if (filename != null && parts.get(i).getContentType() != null) {
								// 有勾選修改並實際有上傳新照片的
								InputStream in = parts.get(i).getInputStream();
								byte[] buf = new byte[in.available()];
								in.read(buf);
								in.close();
								dspicSvc.addDspic(ds_no, buf);
							}
						}
					}
				} catch (Exception e) {

				}

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("diveshopVO", diveshopVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/diveshop/update_diveshop_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/****************** 2.開始修改資料 ***************************/
				DiveshopService diveshopSvc = new DiveshopService();
				diveshopVO = diveshopSvc.updateDiveshop(ds_no, ds_name, brcid, tel, address, dsaccount, dspaw, dsmail,
						dsinfo, ds_latlng, ds_state, ds_ascore, ds_rep_no);

				/****************** 3.修改完成，轉交 *************************/
				req.setAttribute("diveshopVO", diveshopVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllDiveshop.jsp
				successView.forward(req, res);

				/****************** 4.其他可能的錯誤處理 *********************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/diveshop/update_diveshop_input.jsp");
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) { // 來自addDiveshop.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String ds_name = req.getParameter("ds_name");
				String ds_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,40}$";
				if (ds_name == null || ds_name.trim().length() == 0) {
					errorMsgs.add("潛店名稱: 請勿空白");
				} else if (!ds_name.trim().matches(ds_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("潛店名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				Integer brcid = null;
				try {
					brcid = new Integer(req.getParameter("brcid").trim());
				} catch (NumberFormatException e) {
					brcid = 00000000;
					errorMsgs.add("營業登記證請填數字");
				}

				String tel = req.getParameter("tel").trim();
				if (tel == null || tel.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}

				String dsaccount = req.getParameter("dsaccount").trim();
				if (dsaccount == null || dsaccount.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}

				String dspaw = req.getParameter("dspaw").trim();
				if (dspaw == null || dspaw.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}

				String dsmail = req.getParameter("dsmail").trim();
				if (dsmail == null || dsmail.trim().length() == 0) {
					errorMsgs.add("信箱請勿空白");
				}

				String dsinfo = req.getParameter("dsinfo").trim();
				if (dsinfo == null || dsinfo.trim().length() == 0) {
					errorMsgs.add("潛店介紹請勿空白");
				}

				String ds_latlng = req.getParameter("ds_latlng").trim();
				if (ds_latlng == null || ds_latlng.trim().length() == 0) {
					errorMsgs.add("緯經度請勿空白");
				}

				String ds_state = req.getParameter("ds_state").trim();
				if (ds_state == null || ds_state.trim().length() == 0) {
					errorMsgs.add("潛店狀態請勿空白");
				}

				Integer ds_ascore = null;
				try {
					ds_ascore = new Integer(req.getParameter("ds_ascore").trim());
				} catch (NumberFormatException e) {
					ds_ascore = 00;
					errorMsgs.add("請輸入潛店評分");
				}

				Integer ds_rep_no = null;
				try {
					ds_rep_no = new Integer(req.getParameter("ds_rep_no").trim());
				} catch (NumberFormatException e) {
					ds_rep_no = 00;
					errorMsgs.add("請輸入潛店被檢舉次數");
				}

				DiveshopVO diveshopVO = new DiveshopVO();

				diveshopVO.setDs_name(ds_name);
				diveshopVO.setBrcid(brcid);
				diveshopVO.setTel(tel);
				diveshopVO.setAddress(address);
				diveshopVO.setDsaccount(dsaccount);
				diveshopVO.setDspaw(dspaw);
				diveshopVO.setDsmail(dsmail);
				diveshopVO.setDsinfo(dsinfo);
				diveshopVO.setDs_latlng(ds_latlng);
				diveshopVO.setDs_state(ds_state);
				diveshopVO.setDs_ascore(ds_ascore);
				diveshopVO.setDs_rep_no(ds_rep_no);

				// 接收前端上傳的檔案
				Collection<Part> parts = req.getParts();
				List<DspicVO> list = new ArrayList<DspicVO>();

				// 將parts(將所有input輸入值)用foreach取出
				try {

					parts = (ArrayList<Part>) req.getParts();

					for (Part part : parts) {
						
							if ("addPics".equals(part.getName())) {
								String filename = getFileNameFromPart(part);
								if (filename != null && part.getContentType() != null) {
									// 有勾選修改並實際有上傳新照片的
									InputStream in = part.getInputStream();
									byte[] buf = new byte[in.available()];
									in.read(buf);
									in.close();
									DspicVO dspicVO = new DspicVO();
									dspicVO.setDpic(buf);
									list.add(dspicVO);
								}
							}

					}
				} catch (Exception e) {

				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("diveshopVO", diveshopVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/addDs.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				DiveshopService diveshopSvc = new DiveshopService();
				diveshopSvc.addDiveshopWithPic(diveshopVO, list);

				req.setAttribute("diveshopVO", diveshopVO);
				
				//peng寫的，跳出註冊成功的彈窗				
				List<String> message = new LinkedList<String>();
				message.add("success") ;
				req.setAttribute("message", message);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				//peng改的，轉交仍然跳回潛店註冊頁，再由潛店註冊頁彈窗轉跳
				String url = "/front-end/mem/addDs.jsp";				
				//String url = "/front-end/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/addDs.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自ListAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage
			// view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			try {
				/************** 1.接收請求參數 ************/
				String ds_no = new String(req.getParameter("ds_no"));

				/************** 2.開始刪除資料 ************/
				DiveshopService diveshopSvc = new DiveshopService();
				diveshopSvc.deleteDiveshop(ds_no);

				/************* 3.刪除完成，轉交 ***********/
				String url = requestURL;
				RequestDispatcher successsView = req.getRequestDispatcher(url);
				successsView.forward(req, res);

				/************ 4.其他可能錯誤處理 ***********/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/diveshop/listAllDiveshop.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		//peng補，後台admin更改潛店狀態
		if ("upDsState_by_admin".equals(action)) { 
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ds_no = new String(req.getParameter("ds_no"));
				
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				
				String ds_state = req.getParameter("ds_state");	
					

				/***************************2.開始查詢資料*****************************************/								
				diveshopVO = diveshopSvc.updateDiveshop(ds_no, diveshopVO.getDs_name(), diveshopVO.getBrcid(), diveshopVO.getTel(), 
						diveshopVO.getAddress(), diveshopVO.getDsaccount(), diveshopVO.getDspaw(), diveshopVO.getDsmail(),
						diveshopVO.getDsinfo(), diveshopVO.getDs_latlng(), ds_state, diveshopVO.getDs_ascore(), diveshopVO.getDs_rep_no());
								
				
				//將驗證連結，email到該會員信箱
				MailService2 sendPass = new MailService2();
		        String to = diveshopVO.getDsmail();
		        
		        if(ds_state.equals("審核通過")) {
			        String subject = "開通帳號通知";
			        String stateLink = "http://localhost:8081" + req.getContextPath() + "/front-end/index.jsp";
			        String messageText = "Hello! ，你的帳號已經審核通過\n" + " 可點擊登入\n :" + stateLink;
					
			        sendPass.sendMail(to, subject, messageText);
		        }else if(ds_state.equals("停權")){
		        	 String subject = "帳號停權通知";
				        
				        String messageText = "Hello! ，你的帳號已經停權，無法登入，如要申訴，請聯絡網站管理人員";
						
				        sendPass.sendMail(to, subject, messageText);
		        }

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/webmaster/adminDiveShop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}
	
	

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	
}
