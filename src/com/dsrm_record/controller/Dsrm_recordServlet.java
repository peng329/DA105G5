package com.dsrm_record.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dsrm_record.model.Dsrm_recordService;
import com.dsrm_record.model.Dsrm_recordVO;
import com.lesson.model.LessonService;
import com.lesson.model.LessonVO;


@WebServlet("/Dsrm_record")
public class Dsrm_recordServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/********1.接收請求參數-輸入格式錯誤處理*********/
				String str = req.getParameter("rdsr_no");
				if ((str == null) || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入檢舉編號");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/dsrm_record_select_page.jsp");
					failure.forward(req, res);
					return;
				}
				String rdsr_no = null;
				try {
					rdsr_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("流水號格是不正確");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/dsrm_record_select_page.jsp");
					failure.forward(req, res);
					return; // 程式中斷
				}
				
				/********2.查詢資料(呼叫Service)**********/
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				Dsrm_recordVO dsrm_recordVO = dsrm_recordSvc.getOneDsrm_record(rdsr_no);
				if (dsrm_recordVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failure = req.getRequestDispatcher("/dsrm_record_select_page.jsp");
					failure.forward(req, res);
					return; // 程式中斷
				}
				
				/********3.查詢完成，準備轉交**************/
				req.setAttribute("dsrm_recordVO", dsrm_recordVO);
				String url = "/back/dsrm_record/listOneDsrm_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/*******4.其他可能錯誤處理************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dspic_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("erroeMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestRUL");
			try {
				/*********1.接收請求參數*************/
				String rdsr_no = req.getParameter("rdsr_no");
				
				/*********2.開始查詢資料*************/
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				Dsrm_recordVO dsrm_recordVO = dsrm_recordSvc.getOneDsrm_record(rdsr_no);
				
				/*********3.查詢完成，準備轉交******/
				req.setAttribute("dsrm_recordVO", dsrm_recordVO);
				String url = "/back-end/dsrm_record/update_dsrm_record_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/*********4.其他可能錯誤處理********/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			try {
				/***********1.接收請求 - 輸入格式錯誤處理************/
				String rdsr_no = req.getParameter("rdsr_no");
				
				String ds_no = req.getParameter("ds_no");
				
				String mem_no = req.getParameter("mem_no");
				
				String rep_content = req.getParameter("rep_content");
				if (rep_content == null || rep_content.trim().length() == 0) {
					errorMsgs.add("檢舉內容請勿空白");
				}
				
				String rep_state = req.getParameter("rep_state");
				
				Dsrm_recordVO dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rdsr_no);
				dsrm_recordVO.setDs_no(ds_no);
				dsrm_recordVO.setMem_no(mem_no);
				dsrm_recordVO.setRep_content(rep_content);
				dsrm_recordVO.setRep_state(rep_state);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dsrm_recordVO", dsrm_recordVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dsrm_record/update_dsrm_record_input.jsp");
					failureView.forward(req, res);
				}
				
				/*********2.開始修改資料*************/
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				dsrm_recordVO = dsrm_recordSvc.updateDsrm_record(rdsr_no, ds_no, mem_no, rep_content, rep_state);
				
				/********3.修改完成，準備轉交***********/
				req.setAttribute("dsrm_recordVO", dsrm_recordVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/********4.其他可能的錯誤處理**********/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dspic/update_dspic_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			try {
				/*********** 1.接收請求參數 ************/
				String rdsr_no = req.getParameter("rdsr_no");
				
				/********** 2.開始刪除資料***********/
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				dsrm_recordSvc.deleteDsrm_reocrd(rdsr_no);
				
				/*********3.刪除完成*********/
				String url = requestURL;
				RequestDispatcher successsView = req.getRequestDispatcher(url);
				successsView.forward(req, res);
				
				/*******4.其他可能錯誤處理*******/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dsrm_record/listAllDsrm_record.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********1.接收請求參數************/
				String rdsr_no = req.getParameter("rdsr_no");
				
				String ds_no = req.getParameter("ds_no");
				
				String mem_no = req.getParameter("mem_no");
				
				String rep_content = req.getParameter("rep_content");
				if (rep_content == null || rep_content.trim().length() == 0) {
					errorMsgs.add("檢舉內容請勿空白");
				}
				
				String rep_state = req.getParameter("rep_state");
				
				Dsrm_recordVO dsrm_recordVO = new Dsrm_recordVO();
				dsrm_recordVO.setRdsr_no(rdsr_no);
				dsrm_recordVO.setDs_no(ds_no);
				dsrm_recordVO.setMem_no(mem_no);
				dsrm_recordVO.setRep_content(rep_content);
				dsrm_recordVO.setRep_state(rep_state);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dsrm_recordVO", dsrm_recordVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dsrm_record/addDsrm_record.jsp");
					failureView.forward(req, res);
				}
				/********2.開始新增資料**************/
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				dsrm_recordSvc.addDsrm_record(ds_no, mem_no, rep_content, rep_state);
				req.setAttribute("dsrm_recordVO", dsrm_recordVO);
				
				/*******3.新增完成，準備轉交********/
				String url = "/back-end/dsrm_record/listAllDsrm_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllLesson.jsp
				successView.forward(req, res);
				
				/*******4.其他可能錯誤處理********/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dsrm_record/addDsrm_record.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listDsrm_record_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
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
				Dsrm_recordService dsrm_recordSvc = new Dsrm_recordService();
				List<Dsrm_recordVO> list  = dsrm_recordSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listDsrm_record_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/dsrm_record/listDsrm_record_ByCompositeQuery.jsp"); // 成功轉交
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dsrm_record_select_page.jsp");
				failureView.forward(req, res);
			}
		}		
	}

}
