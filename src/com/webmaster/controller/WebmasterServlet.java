package com.webmaster.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;

import com.authority_manage.model.Authority_manageService;
import com.func.model.FuncService;
import com.func.model.FuncVO;
import com.login.MailService2;
import com.webmaster.model.*;

public class WebmasterServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { 

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
							.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String wm_no = null;
				try {
					wm_no = str;
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				WebmasterVO webmasterVO = webmasterSvc.getOneWebmaster(wm_no);
				if (webmasterVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("webmasterVO", webmasterVO); // 資料庫取出的webmasterVO物件,存入req
				String url = "/back-end/webmaster/oneWebmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneWebmaster.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllWebmaster.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
						
			
			try {
				/***************************1.接收請求參數****************************************/
				String wm_no = req.getParameter("wm_no");
				
				/***************************2.開始查詢資料****************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				WebmasterVO webmasterVO = webmasterSvc.getOneWebmaster(wm_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("webmasterVO", webmasterVO);         // 資料庫取出的webmasterVO物件,存入req
				
				
				
				//String url = "/back-end/webmaster/update_webmaster_input.jsp";
				String url = "/back-end/webmaster/update_webmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_webmaster_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/webmaster/listAllWebmaster.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_webmaster_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String wm_no = req.getParameter("wm_no");
				String wm_id = req.getParameter("wm_id");
				
				
				
				String wm_psw = req.getParameter("wm_psw");
				String mpswReg = "^[a-zA-Z0-9_]{2,10}$";
				if (wm_psw == null || wm_psw.trim().length() == 0) {
					errorMsgs.add("員工密碼: 請勿空白");
				} else if(!wm_psw.trim().matches(mpswReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String wm_name = req.getParameter("wm_name");
				String wm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (wm_name == null || wm_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!wm_name.trim().matches(wm_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer wm_sex = new Integer(req.getParameter("wm_sex").trim());

				

				String wm_mail = req.getParameter("wm_mail");
				String wmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (wm_mail == null || wm_mail.trim().length() == 0) {
					errorMsgs.add("會員信箱: 請勿空白");
				} else if(!wm_mail.trim().matches(wmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信箱格式有誤");
	            }
				
				java.sql.Date ob_date = null;
				try {
					ob_date = java.sql.Date.valueOf(req.getParameter("ob_date").trim());

				} catch (IllegalArgumentException e) {
					ob_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				java.sql.Date dd_date = null;
				try {
					dd_date = java.sql.Date.valueOf(req.getParameter("dd_date").trim());

				} catch (IllegalArgumentException e) {
					dd_date = null;
				}
				
				
				
				java.sql.Timestamp reg_time = java.sql.Timestamp.valueOf(req.getParameter("reg_time").trim());

				WebmasterVO webmasterVO = new WebmasterVO();
			    webmasterVO.setWm_no(wm_no);
			    webmasterVO.setWm_name(wm_name);
			    webmasterVO.setWm_sex(wm_sex);
			    webmasterVO.setWm_id(wm_id);
			    webmasterVO.setWm_psw(wm_psw);
			    webmasterVO.setWm_mail(wm_mail);
			    webmasterVO.setOb_date(ob_date);
			    webmasterVO.setDd_date(dd_date);
			    webmasterVO.setReg_time(reg_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("webmasterVO", webmasterVO); // 含有輸入格式錯誤的webmasterVO物件,也存入req
					RequestDispatcher failureView = req
							//.getRequestDispatcher("/back-end/webmaster/update_webmaster_input.jsp");
							.getRequestDispatcher("/back-end/webmaster/update_webmaster.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				webmasterVO = webmasterSvc.updateWebmaster(wm_no, wm_name, wm_sex, wm_id, wm_psw, 
						wm_mail, ob_date, dd_date, reg_time);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("webmasterVO", webmasterVO); // 資料庫update成功後,正確的的webmasterVO物件,存入req
				
				HttpSession session = req.getSession();
				WebmasterVO sessionWmVO = (WebmasterVO)session.getAttribute("webmasterVO");
				if (wm_no.equals(sessionWmVO.getWm_no())) {
					session.setAttribute("webmasterVO", webmasterVO);
				}
				
				
				//String url = "/back-end/webmaster/listOneWebmaster.jsp";
				String url = "/back-end/webmaster/AllWebmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneWebmaster.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						//.getRequestDispatcher("/back-end/webmaster/update_webmaster_input.jsp");
						.getRequestDispatcher("/back-end/webmaster/update_webmaster.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("oneUpdate".equals(action)) { // 來自update_webmaster_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String wm_no = req.getParameter("wm_no");
				String wm_id = req.getParameter("wm_id");
				
								
				
				String wm_psw = req.getParameter("wm_psw");
				String mpswReg = "^[a-zA-Z0-9_]{2,10}$";
				if (wm_psw == null || wm_psw.trim().length() == 0) {
					errorMsgs.add("員工密碼: 請勿空白");
				} else if(!wm_psw.trim().matches(mpswReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
								
				String wm_name = req.getParameter("wm_name");
				String wm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (wm_name == null || wm_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!wm_name.trim().matches(wm_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer wm_sex = new Integer(req.getParameter("wm_sex").trim());

				

				String wm_mail = req.getParameter("wm_mail");
				String wmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (wm_mail == null || wm_mail.trim().length() == 0) {
					errorMsgs.add("會員信箱: 請勿空白");
				} else if(!wm_mail.trim().matches(wmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信箱格式有誤");
	            }
						
				java.sql.Date ob_date = null;
				
				try {
					ob_date = java.sql.Date.valueOf(req.getParameter("ob_date").trim());
					
				} catch (IllegalArgumentException e) {
					ob_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				java.sql.Date dd_date = null;
			
				
				java.sql.Timestamp reg_time = java.sql.Timestamp.valueOf(req.getParameter("reg_time").trim());
				
				WebmasterVO webmasterVO = new WebmasterVO();
			    webmasterVO.setWm_no(wm_no);
			    webmasterVO.setWm_name(wm_name);
			    webmasterVO.setWm_sex(wm_sex);
			    webmasterVO.setWm_id(wm_id);
			    webmasterVO.setWm_psw(wm_psw);
			    webmasterVO.setWm_mail(wm_mail);
			    webmasterVO.setOb_date(ob_date);
			    webmasterVO.setDd_date(dd_date);
			    webmasterVO.setReg_time(reg_time);
			    
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("webmasterVO", webmasterVO); // 含有輸入格式錯誤的webmasterVO物件,也存入req
					RequestDispatcher failureView = req
							//.getRequestDispatcher("/back-end/webmaster/update_webmaster_input.jsp");
							.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
					
				/***************************2.開始修改資料*****************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				webmasterVO = webmasterSvc.updateWebmaster(wm_no, wm_name, wm_sex, wm_id, wm_psw, 
						wm_mail, ob_date, dd_date, reg_time);
							
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("webmasterVO", webmasterVO); // 資料庫update成功後,正確的的webmasterVO物件,存入req
				
				HttpSession session = req.getSession();
				session.setAttribute("webmasterVO", webmasterVO);
				
				//String url = "/back-end/webmaster/listOneWebmaster.jsp";
				String url = "/back-end/webmaster/oneWebmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneWebmaster.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						//.getRequestDispatcher("/back-end/webmaster/update_webmaster_input.jsp");
						.getRequestDispatcher("/back-end/webmaster/oneWebmaster.jsp");
				failureView.forward(req, res);
			}
		}

		

        if ("insert".equals(action)) { // 來自addWebmaster.jsp的請求  

        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String wm_id = req.getParameter("wm_id");

				String wmidReg = "^[a-zA-Z0-9_]{2,10}$";
				if (wm_id == null || wm_id.trim().length() == 0) {
					errorMsgs.add("會員帳號: 請勿空白");
				} else if(!wm_id.trim().matches(wmidReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員帳號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				//用wm_id來查找資料庫，如果有找到，代表重複，輸出錯誤
				WebmasterService wmSvc2 = new WebmasterService();
				WebmasterVO webmasterVO2 = wmSvc2.getOneWmById(wm_id);
				if(webmasterVO2 != null) {
					errorMsgs.add("會員帳號重複: 請重新輸入");
				}
				

				//利用工具產生亂數，再把亂數給員工當密碼
				PassRandom ranPass = new PassRandom();
				String wm_psw = ranPass.genAuthCode();
				
				String wm_name = req.getParameter("wm_name");
				String wm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (wm_name == null || wm_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!wm_name.trim().matches(wm_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
			
				
				
				Integer wm_sex = new Integer(req.getParameter("wm_sex").trim());

				

				String wm_mail = req.getParameter("wm_mail");
				String wmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (wm_mail == null || wm_mail.trim().length() == 0) {
					errorMsgs.add("會員信箱: 請勿空白");
				} else if(!wm_mail.trim().matches(wmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信箱格式有誤");
	            }
				
				java.sql.Date ob_date = null;
				try {
					ob_date = java.sql.Date.valueOf(req.getParameter("ob_date").trim());
				} catch (IllegalArgumentException e) {
					ob_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				
				
				java.sql.Date dd_date = null;

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
				java.sql.Timestamp reg_time = java.sql.Timestamp.valueOf(dateFormat.format(System.currentTimeMillis()));
				
				
				String[] fc_noArray = req.getParameterValues("fc_no");
				
//				HashMap<String, String> fc_noMap = new HashMap<String, String>();
//				FuncService funcSvc = new FuncService();
//				List<FuncVO> funcList = funcSvc.getAll();

				//將有勾選的權限，與所有權限名單比較，再存進Map中，key為權限編號全名單，value為有勾選的為編號，沒勾選的給null
//				for(FuncVO funcVO:funcList) {
//					int checki = 0;
//					for(int i = 0; i<fc_noArray.length; i++) {
//						if(funcVO.getFc_no().equals(fc_noArray[i])) {
//							fc_noMap.put(funcVO.getFc_no(), fc_noArray[i]);
//							checki = 1;
//							break;
//						}
//					}					
//					if(checki == 0) {
//						fc_noMap.put(funcVO.getFc_no(), null);
//					}
//				}
				
		
				WebmasterVO webmasterVO = new WebmasterVO();
			    webmasterVO.setWm_name(wm_name);
			    webmasterVO.setWm_sex(wm_sex);
			    webmasterVO.setWm_id(wm_id);
			    webmasterVO.setWm_psw(wm_psw);
			    webmasterVO.setWm_mail(wm_mail);
			    webmasterVO.setOb_date(ob_date);
			    webmasterVO.setDd_date(dd_date);
			    webmasterVO.setReg_time(reg_time);
			    
			    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("webmasterVO", webmasterVO); // 含有輸入格式錯誤的webmasterVO物件,也存入req
//					req.setAttribute("fc_noMap", fc_noMap);
					
					req.setAttribute("fc_noArray", fc_noArray);
					
					RequestDispatcher failureView = req
							//.getRequestDispatcher("/back-end/webmaster/addWebmaster.jsp");
							.getRequestDispatcher("/back-end/webmaster/addWebmaster.jsp");
					
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				Authority_manageService amSvc = new Authority_manageService();
				
				webmasterVO = webmasterSvc.addWebmaster(wm_name, wm_sex, wm_id, wm_psw, 
						wm_mail, ob_date, dd_date, reg_time, amSvc, fc_noArray);
				
				

				
				
				
				//將該筆新增員工的亂數密碼，email到該員工信箱
				MailService2 sendPass = new MailService2();
		        String to = wm_mail;
		        String subject = "帳號密碼通知";
		        String adminUrl = "http://localhost:8081/" + req.getContextPath() + "/back-end/webmaster/adminLogin.jsp";

		        String messageText = "Hello! ，你的帳號是" + wm_id + " 密碼是: " + wm_psw + "\n" +" (已經啟用)請至以下網址登入後台管理"+ "\n" + adminUrl;
		        
		        sendPass.sendMail(to, subject, messageText);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				//String url = "/back-end/webmaster/listAllWebmaster.jsp";
		        String url = "/back-end/webmaster/AllWebmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllWebmaster.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/webmaster/addWebmaster.jsp");
				failureView.forward(req, res);
			}
        }
		
		
		if ("delete".equals(action)) { // 來自listAllWebmaster.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wm_no = req.getParameter("wm_no");
				
				/***************************2.開始刪除資料***************************************/
				WebmasterService webmasterSvc = new WebmasterService();
				webmasterSvc.deleteWebmaster(wm_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/webmaster/listAllWebmaster.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/webmaster/listAllWebmaster.jsp");
				failureView.forward(req, res);
			}
		}
		
		//登出管理員
		if ("adminLogout".equals(action)) { // 來自select_page.jsp的請求
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

				
				/***************************2.開始查詢資料*****************************************/
				HttpSession session = req.getSession();
				session.invalidate();

				

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/webmaster/adminLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMem.jsp
				successView.forward(req, res);	
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
	}
}