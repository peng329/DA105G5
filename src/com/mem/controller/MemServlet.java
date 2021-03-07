package com.mem.controller;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.friend.model.FriendService;
import com.friend.model.FriendVO;
import com.mem.model.*;
import com.login.MailService2;

import org.json.JSONObject;





@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 100 * 1024 * 1024, maxRequestSize = 100 * 5 * 1024 * 1024)
public class MemServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		//為了圖片，取session
		HttpSession session = req.getSession();

		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = null;
				try {
					mem_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemService memService = new MemService();
				MemVO memVO = memService.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("memVO", memVO); // 資料庫取出的memVO物件,存入req
				String url = "/back-end/mem/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMem.jsp
				successView.forward(req, res);
			
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllMem.jsp的請求
			
			//以免有之前新增刪改失敗的圖片屬性保留，先清楚
			session.removeAttribute("imgSrc");
			session.removeAttribute("mem_pic2");
			
		
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mem_no = new String(req.getParameter("mem_no"));
				
				/***************************2.開始查詢資料****************************************/
				MemService memService = new MemService();
				MemVO memVO = memService.getOneMem(mem_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("memVO", memVO);         // 資料庫取出的memVO物件,存入req
				String url = "/back-end/mem/update_mem_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_mem_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_mem_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_no = req.getParameter("mem_no").trim();
				
				
				String mem_id = req.getParameter("mem_id").trim();
				
				
				String mem_psw = req.getParameter("mem_psw");
				String mpswReg = "^[a-zA-Z0-9_]{2,10}$";
				if (mem_psw == null || mem_psw.trim().length() == 0) {
					errorMsgs.add("會員密碼: 請勿空白");
				} else if(!mem_psw.trim().matches(mpswReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mem_name = req.getParameter("mem_name");
				String mnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("會員名稱: 請勿空白");
				} else if(!mem_name.trim().matches(mnameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員名稱: 只能是中文、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer mem_sex = new Integer(req.getParameter("mem_sex").trim());

				
				java.sql.Date mem_bd = null;
				try {
					mem_bd = java.sql.Date.valueOf(req.getParameter("mem_bd").trim());
				} catch (IllegalArgumentException e) {
					mem_bd = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String mem_mail = req.getParameter("mem_mail");
				String mmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (mem_mail == null || mem_mail.trim().length() == 0) {
					errorMsgs.add("會員信箱: 請勿空白");
				} else if(!mem_mail.trim().matches(mmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信箱格式有誤");
	            }

				String mem_phone = req.getParameter("mem_phone").trim();
				Integer iMem_phone = null;
				try {
					iMem_phone = new Integer(req.getParameter("mem_phone").trim());
				} catch (NumberFormatException e) {
					iMem_phone = 0;
					errorMsgs.add("手機請填數字.");
				}
				
				String mem_add = req.getParameter("mem_add").trim();
				if (mem_add == null || mem_add.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}
				
							
				byte[] mem_pic = null;	
				String imgSrc = null;
				
				
				//底下2行為了之後讀出原有沒改圖片用
				MemService memService2 = new MemService();
				MemVO memVO2 = memService2.getOneMem(mem_no);
				
				if(mem_pic == null) {
					mem_pic = memVO2.getMem_pic();
				}
				
				InputStream in = req.getPart("mem_pic").getInputStream();
				
				if(in.available()!=0) {
					mem_pic = new byte[in.available()];
					in.read(mem_pic);
					in.close();
					
					//取得隱藏欄位送來的圖檔路徑
					imgSrc = req.getParameter("imgSrc");
					session.setAttribute("imgSrc", imgSrc);
					
					session.setAttribute("mem_pic2",mem_pic);
					
					
				}else {
					//當圖片沒更換時，直接從vo取得
					mem_pic = (byte[]) session.getAttribute("mem_pic2");
				}
				
				
				
				java.sql.Timestamp reg_time = java.sql.Timestamp.valueOf(req.getParameter("reg_time").trim());
				Integer mem_rep_no = new Integer(req.getParameter("mem_rep_no").trim());

				String mem_state = req.getParameter("mem_state").trim();

				MemVO memVO = new MemVO();
			    memVO.setMem_no(mem_no);
			    memVO.setMem_id(mem_id);
			    memVO.setMem_psw(mem_psw);
			    memVO.setMem_name(mem_name);
			    memVO.setMem_sex(mem_sex);
			    memVO.setMem_bd(mem_bd);
			    memVO.setMem_mail(mem_mail);
			    memVO.setMem_phone(mem_phone);
			    memVO.setMem_add(mem_add);
			    memVO.setMem_pic(mem_pic);
			    memVO.setReg_time(reg_time);
			    memVO.setMem_rep_no(mem_rep_no);
			    memVO.setMem_state(mem_state);
			    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/memCenter/memCenter.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				//清除前面session的兩個屬性
				session.removeAttribute("imgSrc");
				session.removeAttribute("mem_pic2");
				
				
				MemService memService = new MemService();
				memVO = memService.updateMem(mem_no , mem_id, mem_psw, mem_name, mem_sex, mem_bd, mem_mail, mem_phone, mem_add, mem_pic, reg_time, mem_rep_no, mem_state);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的memVO物件,存入req
				
				session.setAttribute("memVO", memVO);
				
				String url = "/front-end/memCenter/memCenter.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMem.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memCenter/memCenter.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
        if ("insert".equals(action)) { // 來自addMem.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
						
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_id = req.getParameter("mem_id");
				String midReg = "^[a-zA-Z0-9_]{2,10}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員帳號: 請勿空白");
				} else if(!mem_id.trim().matches(midReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員帳號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				//用mem_id來查找資料庫，如果有找到，代表重複，輸出錯誤
				MemService memSvc2 = new MemService();
				MemVO memVO2 = memSvc2.getOneMemById(mem_id);
				if(memVO2 != null) {
					errorMsgs.add("會員帳號重複: 請重新輸入");
				}
				
				
				
				String mem_psw = req.getParameter("mem_psw");
				String mpswReg = "^[a-zA-Z0-9_]{2,10}$";
				if (mem_psw == null || mem_psw.trim().length() == 0) {
					errorMsgs.add("會員密碼: 請勿空白");
				} else if(!mem_psw.trim().matches(mpswReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mem_name = req.getParameter("mem_name");
				String mnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("會員名稱: 請勿空白");
				} else if(!mem_name.trim().matches(mnameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員名稱: 只能是中文、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
			
				
				Integer mem_sex = new Integer(req.getParameter("mem_sex").trim());

				
				java.sql.Date mem_bd = null;
				try {
					mem_bd = java.sql.Date.valueOf(req.getParameter("mem_bd").trim());
				} catch (IllegalArgumentException e) {
					mem_bd = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String mem_mail = req.getParameter("mem_mail");
				String mmailReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				if (mem_mail == null || mem_mail.trim().length() == 0) {
					errorMsgs.add("會員信箱: 請勿空白");
				} else if(!mem_mail.trim().matches(mmailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("信箱格式有誤");
	            }

				String mem_phone = req.getParameter("mem_phone").trim();
				Integer iMem_phone = null;
				try {
					iMem_phone = new Integer(req.getParameter("mem_phone").trim());
				} catch (NumberFormatException e) {
					iMem_phone = 0;
					errorMsgs.add("手機請填數字.");
				}
				
				String mem_add = req.getParameter("mem_add").trim();
				if (mem_add == null || mem_add.trim().length() == 0) {
					errorMsgs.add("請勿空白");
				}
				


							
				byte[] mem_pic = null;	
				String imgSrc = null;
				
				InputStream in = req.getPart("mem_pic").getInputStream();
				if(in.available()!=0) {

				
					mem_pic = new byte[in.available()];
					in.read(mem_pic);
					in.close();
					
					//取得隱藏欄位送來的圖檔路徑
					imgSrc = req.getParameter("imgSrc");
					session.setAttribute("imgSrc", imgSrc);
					
					session.setAttribute("mem_pic2",mem_pic);

				}else {
					
					mem_pic = (byte[]) session.getAttribute("mem_pic2");
						
				}			
					
				
				
				java.sql.Timestamp reg_time = new java.sql.Timestamp(System.currentTimeMillis());
				
				Integer mem_rep_no = new Integer(0);
				
				String mem_state = "待驗證";

				

				MemVO memVO = new MemVO();
			    memVO.setMem_id(mem_id);
			    memVO.setMem_psw(mem_psw);
			    memVO.setMem_name(mem_name);
			    memVO.setMem_sex(mem_sex);
			    memVO.setMem_bd(mem_bd);
			    memVO.setMem_mail(mem_mail);
			    memVO.setMem_phone(mem_phone);
			    memVO.setMem_add(mem_add);
			    memVO.setMem_pic(mem_pic);
			    memVO.setReg_time(reg_time);
			    memVO.setMem_rep_no(mem_rep_no);
			    memVO.setMem_state(mem_state);
			    
			    
			    

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
			
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mem/addMem.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/

				
				MemService memService = new MemService();
				memVO = memService.addMem(mem_id, mem_psw, mem_name, mem_sex, mem_bd, mem_mail, mem_phone, mem_add, mem_pic, reg_time, mem_rep_no, mem_state) ;
				
				
				//將驗證連結，email到該會員信箱
				MailService2 sendPass = new MailService2();
		        String to = mem_mail;
		        String subject = "開通帳號通知";
//		        String stateLink = "http://localhost:8081/" + req.getContextPath() + "/back-end/mem/mem.do?action=upstateState&mem_id=" + mem_id + "&stateNum=2";
		        String stateLink = "http://"+req.getServerName()+":8081"+req.getContextPath() + "/back-end/mem/mem.do?action=upstateState&mem_id=" + mem_id + "&stateNum=2";
		        String messageText = "Hello! ，你的帳號是" + mem_id + "\n" + " 請點擊連結開通會員 :" + stateLink;
				System.out.println(stateLink);
		        sendPass.sendMail(to, subject, messageText);
		        
		        
				//清除前面session的兩個屬性
				session.removeAttribute("imgSrc");
				session.removeAttribute("mem_pic2");
				
				
				//跳出註冊成功的彈窗
				
				List<String> message = new LinkedList<String>();
				message.add("success") ;
				req.setAttribute("message", message);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/mem/addMem.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMem.jsp
				successView.forward(req, res);
				
//				String contextPath = req.getContextPath();
//				res.setHeader("refresh", "1;URL=" + contextPath + "/index1.jsp");
				
				
				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/addMem.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllMem.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String mem_no = new String(req.getParameter("mem_no"));
				
				/***************************2.開始刪除資料***************************************/
				MemService memService = new MemService();
				memService.deleteMem(mem_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/mem/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
		//用ajax處理二級選單
		if ("ajaxFriend".equals(action)) { // 來自select_page.jsp的請求
			;
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			//session.invalidate(); //測試用

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_no = req.getParameter("mem_no");

				
				/***************************2.開始查詢資料*****************************************/
				
				//利用ajax得來的編號，當參數，呼叫資料庫另一個表單，得到list
				FriendService friendService = new FriendService();
				List<FriendVO> list = friendService.getFriendsByMem_no(mem_no);
				
				//為了最後送出用
				PrintWriter out = res.getWriter();
				
				//得到該編號的多筆資料，將多筆資料的該欄位用逗號串接成字串
				String strFriend = "";
				if (list == null) {
					errorMsgs.add("查無資料");
				}else {
					for(FriendVO i : list) {
						
						
						//重點在串接，判斷是我自己的邏輯判斷
						if(mem_no.equals(i.getMem_no_a()))
							strFriend += (strFriend == "") ? i.getMem_no_b() : "," + i.getMem_no_b();
						else if(mem_no.equals(i.getMem_no_b()))
							strFriend += (strFriend == "") ? i.getMem_no_a() : "," + i.getMem_no_a();
					}
				
				//用json處理方式
			    JSONObject data = new JSONObject();
			    data.put("frMem", strFriend);
					//將該json給ajax
					out.println(data);
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());

			}
		}
		
		//用ajax處理帳號重複
		if ("ajaxCheckId".equals(action)) { // 來自select_page.jsp的請求
			
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
			
			//session.invalidate(); //測試用

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_id = req.getParameter("mem_id");
				
				
				/***************************2.開始查詢資料*****************************************/
				String checkAns = "";
				
				//利用ajax得來的id，當參數，查找是否有這個帳號				
				MemService memSvc3 = new MemService();
				MemVO memVO3 = memSvc3.getOneMemById(mem_id);
				
				if(memVO3 == null) {
					checkAns = "ok";
				}else {
					checkAns = "no";
				}
				
				
				//為了最後送出用
				PrintWriter out = res.getWriter();
				
				
				//用json處理方式
			    JSONObject data = new JSONObject();
			    data.put("checkAns", checkAns);
				//將該json給ajax
				out.println(data);
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
		
		
		//更改會員狀態
		if ("upstateState".equals(action)) { // 來自select_page.jsp的請求
			

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_id = req.getParameter("mem_id");
				String stateNum = req.getParameter("stateNum");
				
				/***************************2.開始查詢資料*****************************************/
				
				MemService memSvc = new MemService();
				memSvc.updateMemState(mem_id, stateNum);

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/mem/memAuthSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
		
		//後台admin更改會員狀態
		if ("upMemState_by_admin".equals(action)) { // 來自select_page.jsp的請求
			

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_id = req.getParameter("mem_id");
				String mem_state = req.getParameter("mem_state");
			
				String stateNum ="1";
				if("通過".equals(mem_state)) {
					stateNum = "2";
				}else if("停權".equals(mem_state)) {
					stateNum = "3";
				}

				/***************************2.開始查詢資料*****************************************/
				
				MemService memSvc = new MemService();
				memSvc.updateMemState(mem_id, stateNum);

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/webmaster/adminMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
		
		
		//登出會員
		if ("memLogout".equals(action)) { // 來自select_page.jsp的請求
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

				
				/***************************2.開始查詢資料*****************************************/
				//HttpSession session = req.getSession();
				session.invalidate();

				

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/mem/memLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMem.jsp
				successView.forward(req, res);	
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
		
		
		//登出潛店
		if ("dsLogout".equals(action)) { // 來自select_page.jsp的請求
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

				
				/***************************2.開始查詢資料*****************************************/
				//HttpSession session = req.getSession();
				session.invalidate();

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/mem/dsLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMem.jsp
				successView.forward(req, res);	
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();

			}
		}
		
	}
}
