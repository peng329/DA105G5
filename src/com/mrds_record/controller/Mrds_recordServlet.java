package com.mrds_record.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;
import com.mrds_record.model.*;
import com.diveshop.model.DiveshopService;
import com.diveshop.model.DiveshopVO;

public class Mrds_recordServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
			
		if ("update".equals(action)) { // 來自update_mrds_record_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mrds_no = req.getParameter("mrds_no").trim();	
				String mem_no = req.getParameter("mem_no").trim();
				String ds_no = req.getParameter("ds_no").trim();
				
				java.sql.Timestamp rep_time = java.sql.Timestamp.valueOf(req.getParameter("rep_time").trim());
				
				String rep_content = req.getParameter("rep_content").trim();
				String rep_state = req.getParameter("rep_state").trim();
	

				Mrds_recordVO mrds_recordVO = new Mrds_recordVO();
			    mrds_recordVO.setMrds_no(mrds_no);
			    mrds_recordVO.setMem_no(mem_no);
			    mrds_recordVO.setDs_no(ds_no);
			    mrds_recordVO.setRep_time(rep_time);
			    mrds_recordVO.setRep_content(rep_content);
			    mrds_recordVO.setRep_state(rep_state);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mrds_recordVO", mrds_recordVO); // 含有輸入格式錯誤的mrds_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mrds_record/AllMrds_record.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Mrds_recordService mrds_recordSvc = new Mrds_recordService();
				mrds_recordVO = mrds_recordSvc.updateMrds_record(mrds_no, mem_no, ds_no, rep_time, rep_content, rep_state);
				
				DiveshopService diveshopSvc = new DiveshopService();
				DiveshopVO diveshopVO = diveshopSvc.getOneDiveshop(ds_no);
				
				if(rep_state.equals("通過")) {
					diveshopVO.setDs_rep_no(diveshopVO.getDs_rep_no() + 1);
				}
				
				
				
				diveshopVO = diveshopSvc.updateDiveshop(ds_no, diveshopVO.getDs_name(), diveshopVO.getBrcid(), diveshopVO.getTel(), 
						diveshopVO.getAddress(), diveshopVO.getDsaccount(), diveshopVO.getDspaw(), diveshopVO.getDsmail(),
						diveshopVO.getDsinfo(), diveshopVO.getDs_latlng(), diveshopVO.getDs_state(), diveshopVO.getDs_ascore(), diveshopVO.getDs_rep_no());
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mrds_recordVO", mrds_recordVO); // 資料庫update成功後,正確的的mrds_recordVO物件,存入req
				String url = "/back-end/mrds_record/AllMrds_record.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMrds_record.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mrds_record/AllMrds_record.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addMrds_record.jsp的請求  

        	System.out.println("xxxxxxxxxxxxxxxxxxx1");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String mem_no = req.getParameter("mem_no");
				String ds_no = req.getParameter("ds_no");

				System.out.println(mem_no);
				System.out.println(ds_no);
				java.sql.Timestamp rep_time = new java.sql.Timestamp(System.currentTimeMillis());
				
				String rep_content = req.getParameter("rep_content");
				System.out.println(rep_content);
				
				String rep_state = "待審核";
				
				System.out.println("xxxxxxxxxxxxxxxxxxx3");

				Mrds_recordVO mrds_recordVO = new Mrds_recordVO();
			    mrds_recordVO.setMem_no(mem_no);
			    mrds_recordVO.setDs_no(ds_no);
			    mrds_recordVO.setRep_time(rep_time);
			    mrds_recordVO.setRep_content(rep_content);
			    
		    
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("mrds_recordVO", mrds_recordVO); // 含有輸入格式錯誤的mrds_recordVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mrds_record/addMrds_record.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("xxxxxxxxxxxxxxxxxxx4");
				/***************************2.開始新增資料***************************************/
				Mrds_recordService mrds_recordSvc = new Mrds_recordService();
				mrds_recordVO = mrds_recordSvc.addMrds_record(mem_no, ds_no, rep_time, rep_content, rep_state);
				System.out.println("xxxxxxxxxxxxxxxxxxx5");
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/diveshop/diveshop.do?action=getOne_For_Display&ds_no=" + ds_no;
				
				System.out.println("xxxxxxxxxxxxxxxxxxx5");
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMrds_record.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mrds_record/addMrds_record.jsp");
				failureView.forward(req, res);
			}
        }

	}
}
