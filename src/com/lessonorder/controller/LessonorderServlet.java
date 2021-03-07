package com.lessonorder.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.diveshop.model.DiveshopService;
import com.diveshop.model.DiveshopVO;
import com.lesson.model.LessonVO;
import com.lessonorder.model.LessonOrderService;
import com.lessonorder.model.LessonOrderVO;

@WebServlet("/LessonorderServlet")
public class LessonorderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		if("findorder_By_Ds_no".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.輸入請求參數-格式錯誤處理*****/
				String ds_no = req.getParameter("ds_no");
				System.out.println(ds_no);
				/*****2.開始查詢資料*******/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				List<LessonOrderVO> orderList = lessonOrderSvc.getLessonOrderByDsno(ds_no);
				
				if (orderList == null) {
					errorMsgs.add("查無資料");
					// send the use back to the form, if there were errors
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*****3.查詢完畢，轉交******/
				req.setAttribute("orderList", orderList);
				String url = "/back-end/lessonorder/listLessonOrderByDsno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);
				
				/******4.其他可能錯誤處理*******/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***** 1.接收請求參數-輸入格式錯誤處理 ******/
				String str = req.getParameter("les_o_no");
				if (str == null || (str.trim().length()) == 0) {
					errorMsgs.add("請輸入課程編號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String les_o_no = null;
				try {
					les_o_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("潛店編號格式不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********** 2.開始查詢資料 ***************/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				LessonOrderVO lessonOrderVO = lessonOrderSvc.getOneLessonOrder(les_o_no);
				if (lessonOrderVO == null) {
					errorMsgs.add("查無資料");
					// send the use back to the form, if there were errors
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/********* 3.查詢完畢，準備轉交 ********************/
				req.setAttribute("lessonOrderVO", lessonOrderVO);
				String url = "/back-end/lessonorder/listOneLessonorder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);

				/*********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/lessonorder_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestRUL");

			try {
				/******* 1.接收請求參數 ********/
				String les_o_no = req.getParameter("les_o_no");
				/******* 2.開始查詢資料 ********/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				LessonOrderVO lessonOrderVO = lessonOrderSvc.getOneLessonOrder(les_o_no);
				/******* 3.查詢完成，轉交 *****/
				req.setAttribute("lessonOrderVO", lessonOrderVO);
				String url = "/back-end/lessonorder/update_lessonorder_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				// 成功轉交update_lessonorder_input.jsp
				successView.forward(req, res);
				/****** 4.其他可能的錯誤處理 ******/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage
			// view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			try {
				/************ 1.接收請求參數 *****************/
				String les_o_no = req.getParameter("les_o_no");
				String ds_no = req.getParameter("ds_no");
				/************ 2.開始刪除資料 ****************/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				lessonOrderSvc.deleteLessonOrder(les_o_no);
				List<LessonOrderVO> orderList = lessonOrderSvc.getLessonOrderByDsno(ds_no); 
				req.setAttribute("orderList", orderList);
				/*********** 3.刪除完成，轉交 ****************/
				String url = requestURL;
				RequestDispatcher successsView = req.getRequestDispatcher(url);
				successsView.forward(req, res);

				/********** 4.其他可能錯誤 *******************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lessonorder/listAllLessonorder.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/***** 1.接收請求參數 - 輸入格式錯誤處理 *****/
				String les_o_no = req.getParameter("les_o_no");
				
				String mem_no = req.getParameter("mem_no");
				
				String ds_no = req.getParameter("ds_no");

				String les_no = req.getParameter("les_no");

				String mem_name = req.getParameter("mem_name");

				String les_name = req.getParameter("les_name");

				String coach = req.getParameter("coach");

				Integer cost = new Integer(req.getParameter("cost"));

				String lo_state = req.getParameter("lo_state");
				LessonOrderVO lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setLes_o_no(les_o_no);
				lessonOrderVO.setMem_no(mem_no);
				lessonOrderVO.setLes_no(les_no);
				lessonOrderVO.setDs_no(ds_no);
				lessonOrderVO.setMem_name(mem_name);
				lessonOrderVO.setLes_name(les_name);
				lessonOrderVO.setCoach(coach);
				lessonOrderVO.setCost(cost);
				lessonOrderVO.setLo_state(lo_state);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lessonOrderVO", lessonOrderVO); // 含有輸入格式錯誤的lessonVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/lessonorder/update_lessonorder_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/******* 2.開始修改資料 ***********/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				lessonOrderVO = lessonOrderSvc.updateLessonOrder(les_o_no, mem_no, les_no, ds_no, mem_name, les_name, cost, coach, lo_state);
				List<LessonOrderVO> orderList = lessonOrderSvc.getLessonOrderByDsno(ds_no); 
				/******* 3.修改完成，轉被轉交 *********/
				if(requestURL.equals("/back-end/lessonorder/listAllLessonorder.jsp")){
					req.setAttribute("lessonOrderVO", lessonOrderVO);
				}
				if(requestURL.equals("/back-end/lessonorder/listLessonOrderByDsno.jsp")) {
					req.setAttribute("orderList", orderList);
				}
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllLessonorder.jsp
				successView.forward(req, res);

				/***** 4.其他可能的錯誤處理 ************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lessonorder/update_lessonorder_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/******* 1.接收請求參數 *********/
				String mem_no = req.getParameter("mem_no");
				String les_no = req.getParameter("les_no");
				String ds_no = req.getParameter("ds_no");
				String mem_name = req.getParameter("mem_name");
				String les_name = req.getParameter("les_name");
				String coach = req.getParameter("coach");
				Integer cost = new Integer(req.getParameter("cost"));
				String lo_state = req.getParameter("lo_state");
				String ds_name = req.getParameter("ds_name");
				
				LessonOrderVO lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setMem_no(mem_no);
				lessonOrderVO.setLes_no(les_no);
				lessonOrderVO.setDs_no(ds_no);
				lessonOrderVO.setMem_name(mem_name);
				lessonOrderVO.setLes_name(les_name);
				lessonOrderVO.setCoach(coach);
				lessonOrderVO.setCost(cost);
				lessonOrderVO.setLo_state(lo_state);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lessonOrderVO", lessonOrderVO); // 含有輸入格式錯誤的lesonVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lessonorder/addLessonorder.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				LessonOrderService lessonOrderSvc = new LessonOrderService();
				lessonOrderSvc.addLessonOrder(mem_no, les_no, ds_no, mem_name, les_name, cost, coach, lo_state);
				
				req.setAttribute("lessonOrderVO", lessonOrderVO);

				/*********** 3.新增完成，準備轉交 ****************/
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				req.setAttribute("diveshopVO", diveshopVO);
				req.setAttribute("ds_no", ds_no);
				req.setAttribute("ds_name", ds_name);
				String url = "/front-end/checkout/checkoutSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllLesson.jsp
				successView.forward(req, res);

				/********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lessonorder/addLessonorder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getLessonOrder".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();
			LessonVO lessonVO = (LessonVO)session.getAttribute("lessonVO");
			LessonOrderService lessonOrderSvc = new LessonOrderService();
			
			try {
				/******* 1.接收請求參數 *********/
				
				String mem_no = req.getParameter("mem_no");
				
				String les_no = req.getParameter("les_no");
				
				String mem_name = req.getParameter("mem_name");
				if(lessonOrderSvc.getMem_noByLes_no(les_no).contains(mem_no)) {
					errorMsgs.add("請勿重複報名");
				}
				
				String les_name = req.getParameter("les_name");
				
				String coach = req.getParameter("coach");
				
				Integer cost = new Integer(req.getParameter("cost"));
				
				String lo_state = req.getParameter("lo_state");
				
				String ds_no = req.getParameter("ds_no");
				
				String ds_name = req.getParameter("ds_name");
				
				LessonOrderVO lessonOrderVO = new LessonOrderVO();
				lessonOrderVO.setMem_no(mem_no);
				lessonOrderVO.setLes_no(les_no);
				lessonOrderVO.setMem_name(mem_name);
				lessonOrderVO.setLes_name(les_name);
				lessonOrderVO.setCoach(coach);
				lessonOrderVO.setCost(cost);
				lessonOrderVO.setLo_state(lo_state);
				lessonOrderVO.setDs_no(ds_no);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lessonOrderVO", lessonOrderVO); // 含有輸入格式錯誤的lesonVO物件,也存入req
					session.setAttribute("lessonVO", lessonVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lesson/listOneLesson.jsp");
					failureView.forward(req, res);
					return;
				}


				/*********** 2.新增完成，準備轉交 ****************/
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				req.setAttribute("diveshopVO", diveshopVO);
				req.setAttribute("lessonOrderVO", lessonOrderVO);
				req.setAttribute("ds_name", ds_name);
				String url = "/front-end/lessonorder/addLessonorder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllLesson.jsp
				successView.forward(req, res);

				/********** 3.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lesson/listOneLesson.jsp");
				failureView.forward(req, res);
			}
		}


		
	}
}
