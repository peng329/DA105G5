package com.friend.controller;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.friend.model.*;
import com.mem.model.*;


public class FriendServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getAllMemFriend_Display".equals(action)) { // 來自select_page.jsp的請求

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
							.getRequestDispatcher("/front-end/memCenter/mem_myFriends.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = str;

				
				/***************************2.開始查詢資料*****************************************/
				
				FriendService friendSvc = new FriendService();
				//取得該會員的所有好友VO列表
				List<FriendVO> friendsList = friendSvc.getFriendsByMem_no(mem_no);
				if (friendsList == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/memCenter/mem_myFriends.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				//取得該會員的所有好友會員編號列表
				List<String> memNolist = friendSvc.getFriendsMem_NoByMem_no(mem_no);

				
				//用好友會員編號列表取得好友的會員VO列表
				MemService memService = new MemService();
				List<MemVO> memVOList = new ArrayList<MemVO>();
				for(String imem_no : memNolist) {
					memVOList.add(memService.getOneMem(imem_no));					
				}
				
				//取得(處在會員B時)未通過的好友VO列表
				List<FriendVO> unFriendlist = friendSvc.getMem_no_bUnconfirm(mem_no);
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				//req.setAttribute("friendsList", friendsList); // 資料庫取出的friendVO物件,存入req
				//req.setAttribute("memVOList", memVOList); // memVO會員好友的物件,存入req
				req.setAttribute("memNolist", memNolist);
				req.setAttribute("unFriendlist", unFriendlist);
				
				String url = "/front-end/memCenter/mem_myFriends.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFriend.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memCenter/mem_myFriends.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getAllMemFriendUnc_Display".equals(action)) { // 來自select_page.jsp的請求

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
							.getRequestDispatcher("/front-end/friend/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = str;

				
				/***************************2.開始查詢資料*****************************************/
				
				FriendService friendSvc = new FriendService();
				//取得一位會員所有被加好友(自己是會員B時)，狀態待通過的好友表格VO
				List<FriendVO> list = friendSvc.getMem_no_bUnconfirm(mem_no);

				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/friend/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/

				req.setAttribute("list", list);
				
				String url = "/front-end/friend/listAllMemFriendUnc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFriend.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/friend/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_friend_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_no = req.getParameter("mem_no").trim();
				
				String mem_no_a = req.getParameter("mem_no_a").trim();	
				String mem_no_b = req.getParameter("mem_no_b").trim();
				System.out.println("=========================1");
				
				String fri_state = req.getParameter("fri_state").trim();
				System.out.println("fri_state");
	

				FriendVO friendVO = new FriendVO();
			    friendVO.setMem_no_a(mem_no_a);
			    friendVO.setMem_no_b(mem_no_b);
			    friendVO.setFri_state(fri_state);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("friendVO", friendVO); // 含有輸入格式錯誤的friendVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/friend/listAllFriend.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				
				FriendService friendSvc = new FriendService();
				friendSvc.updateFriend(mem_no_a, mem_no_b, fri_state);
				
				//取得一位會員所有被加好友(自己是會員B時)，狀態待通過的好友表格VO
				List<FriendVO> list = friendSvc.getMem_no_bUnconfirm(mem_no);
				
			
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list);
				
//				String url = req.getHeader("referer");
//				System.out.println(url);
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneFriend.jsp
//				successView.forward(req, res);
				String retUrl = req.getHeader("Referer");   
				  
				if(retUrl != null){   
				    res.sendRedirect(retUrl);   
				} 

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/friend/listAllFriend.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("insert".equals(action)) { // 來自addFriend.jsp的請求  

        	
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_no_a = req.getParameter("mem_no_a").trim();	
				String mem_no_b = req.getParameter("mem_no_b").trim();
				String fri_state = "待通過";
				
	

				FriendVO friendVO = new FriendVO();
			    friendVO.setMem_no_a(mem_no_a);
			    friendVO.setMem_no_b(mem_no_b);
			    friendVO.setFri_state(fri_state);

		   
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("friendVO", friendVO); // 含有輸入格式錯誤的friendVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/friend/addFriend.jsp");
					failureView.forward(req, res);
					return;
				}
				
				//檢查是否已加好友，oracle會檢查複合pk，但兩個會員編號順序調換時，不會檢查，下面自己檢查
				FriendService friendSvc = new FriendService();
				FriendVO friendVO2 = friendSvc.getOneFriend(mem_no_b, mem_no_a);
				if(friendVO2 != null) {
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				
				
				friendVO = friendSvc.addFriend(mem_no_a, mem_no_b, fri_state);

				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/friend/listAllFriend.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFriend.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/friend/addFriend.jsp");
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
				String mem_no = req.getParameter("mem_no");
				
				String mem_no_a = new String(req.getParameter("mem_no_a"));
				String mem_no_b = new String(req.getParameter("mem_no_b"));
				
				/***************************2.開始刪除資料***************************************/
				FriendService friendSvc = new FriendService();
				friendSvc.deleteFriend(mem_no_a, mem_no_b);
				
			
				//取得一位會員所有被加好友(自己是會員B時)，狀態待通過的好友表格VO
				List<FriendVO> list = friendSvc.getMem_no_bUnconfirm(mem_no);
				
			
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list);						
				String url = "/front-end/friend/listAllMemFriendUnc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/friend/listAllMemFriend.jsp");
				failureView.forward(req, res);
			}
		} 

	}
}