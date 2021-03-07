package com.lesson.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.lespic.model.LespicService;
import com.lespic.model.LespicVO;
import com.lesson.model.LessonService;
import com.lesson.model.LessonVO;

@WebServlet("/LessonServlet")
@MultipartConfig
public class LessonServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("findByDsname".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			System.out.println(requestURL);
			try {
				/**********1.接收請求參數-輸入格式錯誤處理************/
				String ds_no = req.getParameter("ds_no");
				String ds_name = req.getParameter("ds_name");
				
				/********2.開始查詢資料**********/
				LessonService lessonSvc = new LessonService();
				List<LessonVO> list = lessonSvc.getLessonByDs_no(ds_no);
				if (list == null) {
					errorMsgs.add("查無資料");
					// send the use back to the form, if there were errors
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lesson_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/********* 3.查詢完畢，準備轉交 ********************/
				req.setAttribute("list", list);
				req.setAttribute("ds_no", ds_no);
				req.setAttribute("ds_name", ds_name);
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				req.setAttribute("diveshopVO", diveshopVO);
				if("/back-end/diveshop-master/diveshop-master.jsp".equals(requestURL) || "/back-end/diveshop-master/updateLessonByDsname.jsp".equals(requestURL)
				 ||"/back-end/lesson/addLesson.jsp".equals(requestURL) || "/back-end/lessonorder/listLessonOrderByDsno.jsp".equals(requestURL)
				 ||"/back-end/diveshop/update_diveshop_input.jsp".equals(requestURL) ||"/back-end/lesson/update_lesson_input.jsp".equals(requestURL)
				 ||"/back-end/equip/addEquipByBUP.jsp".equals(requestURL)||"/back-end/equip/listAllDSEquipByDs_no.jsp".equals(requestURL)
				 ||"/back-end/rorder/listADSAllRO.jsp".equals(requestURL)||"/back-end/back-end/eqpic/addEpic.jsp".equals(requestURL)||"/back-end/eqpic/update_eqpic_input.jsp".equals(requestURL)
				 ||"/back-end/rorder/update_rorder_byDS.jsp".equals(requestURL)||"/back-end/rorder/rodetail.jsp".equals(requestURL)||"/back-end/eqpic/EP_All_Display.jsp".equals(requestURL)
				 ||"/back-end/equip/update_equip_input.jsp".equals(requestURL)) {
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/diveshop-master/updateLessonByDsname.jsp"); // 成功轉交listOneDiveshop.jsp
					successView.forward(req, res);
				}
				if("/front-end/diveshop/listOneDiveshop.jsp".equals(requestURL)  || "/front-end/lesson/listOneLesson.jsp".equals(requestURL)
				|| "/front-end/checkout/checkoutSuccess.jsp".equals(requestURL)  || "/front-end/lesson/listLessonByDsname.jsp".equals(requestURL) 
				|| "/front-end/lessonorder/addLessonorder.jsp".equals(requestURL)||"/front-end/equip/listAllDSEquipByDs_no.jsp".equals(requestURL)){
				String url = "/front-end/lesson/listLessonByDsname.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);
				}
				
				/*********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***** 1.接收請求參數-輸入格式錯誤處理 ******/
				String ds_no = req.getParameter("ds_no");
				String str = req.getParameter("les_no");
				if (str == null || (str.trim().length()) == 0) {
					errorMsgs.add("請輸入課程編號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lesson_select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String les_no = null;
				try {
					les_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("潛店編號格式不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lesson_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********** 2.開始查詢資料 ***************/
				LessonService lessonSvc = new LessonService();
				LessonVO lessonVO = lessonSvc.getOneLesson(les_no);
				if (lessonVO == null) {
					errorMsgs.add("查無資料");
					// send the use back to the form, if there were errors
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/lesson_select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				/********* 3.查詢完畢，準備轉交 ********************/
				req.setAttribute("lessonVO", lessonVO);
				req.setAttribute("diveshopVO", diveshopVO);
				String url = "/front-end/lesson/listOneLesson.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneDiveshop.jsp
				successView.forward(req, res);

				/*********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/lesson_select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestRUL");

			try {
				/******* 1.接收請求參數 ********/
				String les_no = req.getParameter("les_no");
				/******* 2.開始查詢資料 ********/
				LessonService lessonSvc = new LessonService();
				LessonVO lessonVO = lessonSvc.getOneLesson(les_no);
				/******* 3.查詢完成，轉交 *****/
				req.setAttribute("lessonVO", lessonVO);
				String url = "/back-end/lesson/update_lesson_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				// 成功轉交update_lesson_input.jsp
				successView.forward(req, res);
				/****** 4.其他可能的錯誤處理 ******/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			System.out.println("requestURL:"+requestURL);
			try {
				/***** 1.接收請求參數 - 輸入格式錯誤處理 *****/
				String les_no = req.getParameter("les_no").trim();
				String ds_no = req.getParameter("ds_no");
				String ds_name = req.getParameter("ds_name");
				String les_name = req.getParameter("les_name");
				String les_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,12}$";
				if (les_name == null || les_name.trim().length() == 0) {
					errorMsgs.add("課程名稱:請勿空白");
				} else if (!les_name.trim().matches(les_nameReg)) {
					errorMsgs.add("課程名稱:只能是中、英文字母、數字和_ , 且長度必需在2到12之間");
				}

				String les_info = req.getParameter("les_info").trim();
				if (les_info == null || les_info.trim().length() == 0) {
					errorMsgs.add("課程介紹請勿空白");
				}
				
				String coach = req.getParameter("coach").trim();
				if (coach == null || coach.trim().length() == 0) {
					errorMsgs.add("教練名稱請勿空白");
				}
				
				Integer cost = null;
				try {
					cost = new Integer(req.getParameter("cost").trim());
				} catch (NumberFormatException nfe) {
					cost = 0;
					errorMsgs.add("課程費用請填數字");
				}
				
				Integer days = null;
				try {
					days = new Integer(req.getParameter("days").trim());
				} catch (NumberFormatException nfe) {
					days = 0;
					errorMsgs.add("課程天數請填數字");
				}
				java.sql.Date signup_startdate = null;
				try {
					signup_startdate = java.sql.Date.valueOf(req.getParameter("signup_startdate").trim());
				} catch (IllegalArgumentException e) {
					signup_startdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入報名開始日!");
				}
				
				java.sql.Date signup_enddate = null;
				try {
					signup_enddate = java.sql.Date.valueOf(req.getParameter("signup_enddate").trim());
				} catch (IllegalArgumentException e) {
					signup_enddate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入報名截止日!");
				}
				
				String les_state = req.getParameter("les_state").trim();
				
				Integer les_max = null;
				try {
					les_max = new Integer(req.getParameter("les_max").trim());
				} catch (NumberFormatException nfe) {
					les_max = 0;
					errorMsgs.add("報名人數上限請填數字");
				}
				
				Integer les_limit = null;
				try {
					les_limit = new Integer(req.getParameter("les_limit").trim());
				} catch (NumberFormatException nfe) {
					les_limit = 0;
					errorMsgs.add("報名人數下限請填數字");
				}
				
				java.sql.Date les_startdate = null;
				try {
					les_startdate = java.sql.Date.valueOf(req.getParameter("les_startdate").trim());
				} catch (IllegalArgumentException e) {
					les_startdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入課程開始日!");
				}
				
				java.sql.Date les_enddate = null;
				try {
					les_enddate = java.sql.Date.valueOf(req.getParameter("les_enddate").trim());
				} catch (IllegalArgumentException e) {
					les_enddate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入課程開始日!");
				}
				
				String less_state = req.getParameter("less_state").trim();
				
				LessonVO lessonVO = new LessonVO();
				lessonVO.setLes_no(les_no);
				lessonVO.setDs_no(ds_no);
				lessonVO.setDs_name(ds_name);
				lessonVO.setLes_name(les_name);
				lessonVO.setLes_info(les_info);
				lessonVO.setCoach(coach);
				lessonVO.setCost(cost);
				lessonVO.setDays(days);
				lessonVO.setSignup_startdate(signup_startdate);
				lessonVO.setSignup_enddate(signup_enddate);
				lessonVO.setLes_state(les_state);
				lessonVO.setLes_max(les_max);
				lessonVO.setLes_limit(les_limit);
				lessonVO.setLes_startdate(les_startdate);
				lessonVO.setLes_enddate(les_enddate);
				lessonVO.setLess_state(less_state);

				// 先以lpic_seq刪除被勾選的圖片
				String[] lpic_seq_delete = null;
				LespicService lespicSvc = new LespicService();
				try {
					lpic_seq_delete = req.getParameterValues("photo_delete");
				} catch (Exception e) {
				}
				// 如果勾選才執行
				if (lpic_seq_delete != null) {
					for (int i = 0; i < lpic_seq_delete.length; i++) {
						lespicSvc.deleteLespic(lpic_seq_delete[i]);
					}
				}

				// 接收前端上傳的檔案
				// 1.取得有勾選的照片編號
				try {
					String[] lpic_seq_update = req.getParameterValues("photo_update");
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
								lespicSvc.updateLespic(lpic_seq_update[update_count], les_no, buf);
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
								lespicSvc.addLespic(les_no, buf);
							}
						}
					}
				} catch (Exception e) {

				}

				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lessonVO", lessonVO); // 含有輸入格式錯誤的lessonVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lesson/update_lesson_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/******* 2.開始修改資料 ***********/
				LessonService lessonSvc = new LessonService();
				lessonVO = lessonSvc.updateLesson(les_no, ds_no, ds_name, les_name, les_info, coach, cost, days, signup_startdate, signup_enddate, les_state,
						les_max, les_limit, les_startdate, les_enddate, less_state);
				
				/******* 3.修改完成，轉被轉交 *********/
				
				if(requestURL.equals("/back-end/diveshop-master/updateLessonByDsname.jsp")) {
					System.out.println(321);
					req.setAttribute("list",lessonSvc.getLessonByDs_no(ds_no));
					req.setAttribute("ds_no", ds_no);
					req.setAttribute("ds_name", ds_name);
				} if(requestURL.equals("/back-end/lesson/listAllLesson.jsp")){
					req.setAttribute("list", lessonSvc.getAll());
					req.setAttribute("ds_no", ds_no);
					req.setAttribute("ds_name", ds_name);
				}
					
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***** 4.其他可能的錯誤處理 ************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lesson/update_lesson_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******* 1.接收請求參數 *********/
				String ds_no = req.getParameter("ds_no");
				String ds_name = req.getParameter("ds_name");
				

				String les_name = new String(req.getParameter("les_name"));
				String les_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,50}$";
				if (les_name == null || les_name.trim().length() == 0) {
					errorMsgs.add("課程名稱:請勿空白");
				} else if (!les_name.trim().matches(les_nameReg)) {
					errorMsgs.add("課程名稱:只能是中、英文字母、數字和_ , 且長度必需在2到50之間");
				}

				String les_info = req.getParameter("les_info").trim();
				if (les_info == null || les_info.trim().length() == 0) {
					errorMsgs.add("課程介紹請勿空白");
				}

				String coach = req.getParameter("coach").trim();
				if (coach == null || coach.trim().length() == 0) {
					errorMsgs.add("教練名稱請勿空白");
				}

				Integer cost = null;
				try {
					cost = new Integer(req.getParameter("cost").trim());
				} catch (NumberFormatException nfe) {
					cost = 0;
					errorMsgs.add("課程費用請填數字");
				}
				Integer days = null;
				try {
					days = new Integer(req.getParameter("days").trim());
				} catch (NumberFormatException nfe) {
					days = 0;
					errorMsgs.add("課程費用請填數字");
				}
				java.sql.Date signup_startdate = null;
				try {
					signup_startdate = java.sql.Date.valueOf(req.getParameter("signup_startdate").trim());
				} catch (IllegalArgumentException e) {
					signup_startdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入報名開始日!");
				}
				java.sql.Date signup_enddate = null;
				try {
					signup_enddate = java.sql.Date.valueOf(req.getParameter("signup_enddate").trim());
				} catch (IllegalArgumentException e) {
					signup_enddate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入報名截止日!");
				}
				String les_state = req.getParameter("les_state").trim();
				if (les_state == null || les_state.trim().length() == 0) {
					errorMsgs.add("課程狀態請勿空白");
				}
				Integer les_max = null;
				try {
					les_max = new Integer(req.getParameter("les_max").trim());
				} catch (NumberFormatException nfe) {
					les_max = 0;
					errorMsgs.add("報名人數上限請填數字");
				}
				Integer les_limit = null;
				try {
					les_limit = new Integer(req.getParameter("les_limit").trim());
				} catch (NumberFormatException nfe) {
					les_limit = 0;
					errorMsgs.add("報名人數下限請填數字");
				}
				java.sql.Date les_startdate = null;
				try {
					les_startdate = java.sql.Date.valueOf(req.getParameter("les_startdate").trim());
				} catch (IllegalArgumentException e) {
					les_startdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入課程開始日!");
				}
				java.sql.Date les_enddate = null;
				try {
					les_enddate = java.sql.Date.valueOf(req.getParameter("les_enddate").trim());
				} catch (IllegalArgumentException e) {
					les_enddate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入課程開始日!");
				}
				String less_state = req.getParameter("less_state").trim();
				if (less_state == null || less_state.trim().length() == 0) {
					errorMsgs.add("課程陳列狀態請勿空白");
				}
				LessonVO lessonVO = new LessonVO();
				lessonVO.setDs_no(ds_no);
				lessonVO.setDs_name(ds_name);
				lessonVO.setLes_name(les_name);
				lessonVO.setLes_info(les_info);
				lessonVO.setCoach(coach);
				lessonVO.setCost(cost);
				lessonVO.setDays(days);
				lessonVO.setSignup_startdate(signup_startdate);
				lessonVO.setSignup_enddate(signup_enddate);
				lessonVO.setLes_state(les_state);
				lessonVO.setLes_max(les_max);
				lessonVO.setLes_limit(les_limit);
				lessonVO.setLes_startdate(les_startdate);
				lessonVO.setLes_enddate(les_enddate);
				lessonVO.setLess_state(less_state);
				
				// 接收前端上傳的檔案
				Collection<Part> parts = req.getParts();
				List<LespicVO> list = new ArrayList<LespicVO>();
				
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
									LespicVO lespicVO = new LespicVO();
									lespicVO.setLpic(buf);
									list.add(lespicVO);
								}
							}
						
					}
				} catch (Exception e) {

				}


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lessonVO", lessonVO); // 含有輸入格式錯誤的lesonVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lesson/addLesson.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				LessonService lessonSvc = new LessonService();
				lessonSvc.addLessonWithPic(lessonVO, list);
				req.setAttribute("lessonVO", lessonVO);
				req.setAttribute("list",lessonSvc.getLessonByDs_no(ds_no));
				req.setAttribute("ds_no", ds_no);
				req.setAttribute("ds_name", ds_name);
				
				/*********** 3.新增完成，準備轉交 ****************/
				String url = "/back-end/diveshop-master/updateLessonByDsname.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/********** 4.其他可能的錯誤處理 *************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lesson/addLesson.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage
			// view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			try {
				/************1.接收請求參數*****************/
				String les_no = new String(req.getParameter("les_no"));
				String ds_no = new String(req.getParameter("ds_no"));
				String ds_name = new String(req.getParameter("ds_name"));
				/************2.開始刪除資料****************/
				LessonService lessonSvc = new LessonService();
				lessonSvc.deleteLesson(les_no);
				req.setAttribute("list",lessonSvc.getLessonByDs_no(ds_no));
				req.setAttribute("ds_no", ds_no);
				req.setAttribute("ds_name", ds_name);
				
				/***********3.刪除完成，轉交****************/
				String url = requestURL;
				RequestDispatcher successsView = req.getRequestDispatcher(url);
				successsView.forward(req, res);
				
				/**********4.其他可能錯誤*******************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lesson/listAllLesson.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listLesson_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				Map<String, String[]> map = req.getParameterMap();
				
				/***************************2.開始複合查詢***************************************/
				LessonService lessonSvc = new LessonService();
				List<LessonVO> list  = lessonSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listLesson_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/lesson/listLesson_ByCompositeQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/lesson_select_page.jsp");
				failureView.forward(req, res);
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
