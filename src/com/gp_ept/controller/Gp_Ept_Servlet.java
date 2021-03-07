package com.gp_ept.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.act_list.model.Act_List_Service;
import com.act_list.model.Act_List_VO;
import com.agp_list.model.Agp_List_Service;
import com.agp_list.model.Agp_List_VO;
import com.gp_ept.model.Gp_Ept_Service;
import com.gp_ept.model.Gp_Ept_VO;
import com.mem.model.MemVO;


@MultipartConfig
public class Gp_Ept_Servlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/*******************************************
		 * 來自select_page.jsp的請求
		 ******************************************/
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("gp_ept_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入揪團裝備明細編號");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String gp_ept_no = null;

				try {
					gp_ept_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("揪團裝備明細編號格式不正確");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
				Gp_Ept_VO gp_ept_vo = gp_ept_Svc.getOneGP_EPT(gp_ept_no);
				if (gp_ept_vo == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("gp_ept_vo", gp_ept_vo); // 資料庫取出的gp_ept_vo物件,存入req
				String url = "/front-end/gp_ept/listOneGp_Ept.jsp";
				HttpSession session = req.getSession();
				
				
				
				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Act_List_Service act_list_Svc = new Act_List_Service();
				
				Set<Act_List_VO> act_list =  act_list_Svc.getFreeGroup(mem_vo.getMem_no(), "發團中");
				
//				Agp_List_Service agp_list_Svc = new Agp_List_Service();
//				
//				Set<Agp_List_VO> agp_list =  agp_list_Svc.getMem_State("待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("JoinManagementToLeader",act_list); 
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneGp_Ept.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*****************************************
		 * 來自listAllAct_List.jsp的請求
		 *********************************************/
		if ("getOne_For_Update".equals(action)) { // 來自listAllGp_Ept.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String gp_ept_no = new String(req.getParameter("gp_ept_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
				Gp_Ept_VO gp_ept_vo = gp_ept_Svc.getOneGP_EPT(gp_ept_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("gp_ept_vo", gp_ept_vo); // 資料庫取出的gp_ept_vo物件,存入req
				String url = "/front-end/gp_ept/update_gp_ept_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_gp_ept_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/listAllGp_Ept.jsp");
				failureView.forward(req, res);
			}
		}
		
		/******************************************
		 * 來自update_gp_ept_input.jsp的請求
		 **********************************/
		if ("update".equals(action)) { // 來自update_gp_ept_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*************************** gp_ept_no揪團裝備明細編號編號錯誤處理 ***********************/
				String gp_ept_no = new String(req.getParameter("gp_ept_no").trim());
				if (gp_ept_no == null || gp_ept_no.trim().length() == 0) {
					errorMsgs.add("揪團裝備明細編號請勿空白");
				}
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
				String act_list_no = req.getParameter("act_list_no").trim();
				if (act_list_no == null || act_list_no.trim().length() == 0) {
					errorMsgs.add("揪團編號請勿空白");
				}
				/*************************** mem_no會員編號錯誤處理 *******************************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
				/*************************** epc_no裝備分類編號錯誤處理 *******************************/
				String epc_no = req.getParameter("epc_no").trim();
				if (epc_no == null || epc_no.trim().length() == 0) {
					errorMsgs.add("裝備分類編號請勿空白");
				}
				/*************************** gp_t_num數量錯誤處理 ************************/
				Integer gp_t_num = null;
				try {
					gp_t_num = new Integer(req.getParameter("gp_t_num").trim());
				} catch (NumberFormatException e) {
					gp_t_num = 1;
					errorMsgs.add("數量請填數字.");
				}
				/*************************** gp_t_size揪團裝備明大小編號錯誤處理 ***********************/
				String gp_t_size = new String(req.getParameter("gp_t_size").trim());
				if (gp_t_size == null || gp_t_size.trim().length() == 0) {
					errorMsgs.add("揪團裝備尺寸請勿空白");
				}
				/*************************************************************************/

				Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();

				gp_ept_vo.setGp_ept_no(gp_ept_no);
				gp_ept_vo.setAct_list_no(act_list_no);
				gp_ept_vo.setMem_no(mem_no);
				gp_ept_vo.setEpc_no(epc_no);
				gp_ept_vo.setGp_t_num(gp_t_num);
				gp_ept_vo.setGp_t_size(gp_t_size);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gp_ept_vo", gp_ept_vo); // 含有輸入格式錯誤的gp_ept_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/update_gp_ept_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
				gp_ept_vo = gp_ept_Svc.updateGP_EPT(act_list_no, mem_no, epc_no, gp_t_num,gp_t_size, gp_ept_no);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("gp_ept_vo", gp_ept_vo); // 資料庫update成功後,正確的的gp_ept_vo物件,存入req
				String url = "/front-end/gp_ept/listOneGp_Ept.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneGp_Ept.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/update_gp_ept_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*****************************************
		 * 來自addGp_Ept.jsp的請求
		 ******************************************/
		if ("insert".equals(action)) { // 來自addGp_Ept.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*************************** gp_ept_no揪團裝備明細編號編號錯誤處理 ***********************/
//				String gp_ept_no = new String(req.getParameter("gp_ept_no").trim());
//				if (gp_ept_no == null || gp_ept_no.trim().length() == 0) {
//					errorMsgs.add("揪團裝備明細編號請勿空白");
//				}
				/*************************** act_list_no揪團編號編號錯誤處理 ***********************/
//				String act_list_no = req.getParameter("act_list_no").trim();
//				if (act_list_no == null || act_list_no.trim().length() == 0) {
//					errorMsgs.add("揪團編號請勿空白");
//				}
				/*************************** mem_no會員編號錯誤處理 *******************************/
				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號請勿空白");
				}
//				/*************************** epc_no裝備分類編號錯誤處理 *******************************/
//				String epc_no = req.getParameter("epc_no").trim();
//				if (epc_no == null || epc_no.trim().length() == 0) {
//					errorMsgs.add("裝備分類編號請勿空白");
//				}
//				/*************************** gp_t_num數量錯誤處理 ************************/
//				Integer gp_t_num = null;
//				try {
//					gp_t_num = new Integer(req.getParameter("gp_t_num").trim());
//				} catch (NumberFormatException e) {
//					gp_t_num = 1;
//					errorMsgs.add("數量請填數字.");
//				}
//				/*************************** gp_t_size揪團裝備明大小編號錯誤處理 ***********************/
//				String gp_t_size = new String(req.getParameter("gp_t_size").trim());
//				if (gp_t_size == null || gp_t_size.trim().length() == 0) {
//					errorMsgs.add("揪團裝備尺寸請勿空白");
//				}
//				/*************************************************************************/
//
//				Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();
//
////				gp_ept_vo.setGp_ept_no(gp_ept_no);
//				gp_ept_vo.setAct_list_no(act_list_no);
//				gp_ept_vo.setMem_no(mem_no);
//				gp_ept_vo.setEpc_no(epc_no);
//				gp_ept_vo.setGp_t_num(gp_t_num);
//				gp_ept_vo.setGp_t_size(gp_t_size);
				
				HttpSession session = req.getSession();
				String act_list_no = (String) session.getAttribute("act_list_no");
				System.out.println(act_list_no);
/***************************************************************************************************************************/

				
				
				Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>)session.getAttribute("list_ep");
				
				System.out.println(list_ep);
				
				List <Gp_Ept_VO> list = new ArrayList<Gp_Ept_VO>();
			
				Gp_Ept_VO gp_ept_vo=null;
				gp_ept_vo = new Gp_Ept_VO();			
				for(Gp_Ept_VO new_list_ep :list_ep) {
						
					gp_ept_vo.setAct_list_no(act_list_no);
					gp_ept_vo.setMem_no(mem_no);
					gp_ept_vo.setEpc_no(new_list_ep.getEpc_no());
					gp_ept_vo.setGp_t_size(new_list_ep.getGp_t_size());
					gp_ept_vo.setGp_t_num(new_list_ep.getGp_t_num());
					list.add(gp_ept_vo);
				}
/***************************************************************************************************************************/

				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gp_ept_vo", gp_ept_vo); // 含有輸入格式錯誤的gp_ept_vo物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/addGp_Ept.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
//				gp_ept_vo = gp_ept_Svc.addGP_EPT(act_list_no, mem_no, epc_no, gp_t_num,gp_t_size);
				gp_ept_Svc.insert3(list);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/gp_ept/listAllGp_Ept.jsp";
				
				session.removeAttribute("list_ep");
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllGp_Ept.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/addGp_Ept.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*****************************************
		 * 來自listAllGp_Ept.jsp的delete
		 **********************************/
		if ("delete".equals(action)) { // 來自listAllGp_Ept.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String gp_ept_no = new String(req.getParameter("gp_ept_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Gp_Ept_Service gp_ept_Svc = new Gp_Ept_Service();
				gp_ept_Svc.deleteGP_EPT(gp_ept_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/gp_ept/listAllGp_Ept.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/listAllGp_Ept.jsp");
				failureView.forward(req, res);
			}
		}
/*****************************************/
	
		
		HttpSession session = req.getSession();
		Vector<Gp_Ept_VO> list_ep = (Vector<Gp_Ept_VO>)session.getAttribute("list_ep");
		
		if ("deletep".equals(action)) {
		String del =req.getParameter("del");
		int d =Integer.parseInt(del);
		list_ep.removeElementAt(d);
		
		String url="/front-end/gp_ept/addGp_Ept.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		}
		
		else if ("addep".equals(action)) { 

			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//用來判斷商品與購物車裡的商品一不一樣
				boolean match = false;
				
				//取得後來新增的商品
				Gp_Ept_VO gp_ept_vo = getGpEptNum(req);
				//新增第一樣商品
				if(list_ep==null) {
					list_ep = new Vector<Gp_Ept_VO>();
					list_ep.add(gp_ept_vo);
				}else {
					//商品與購物車裡的商品一樣
					for(int i=0; i<list_ep.size();i++) {
						Gp_Ept_VO gp_ept_vo2 =list_ep.get(i); 
						if(gp_ept_vo2.getEpc_no().equals(gp_ept_vo.getEpc_no())&&gp_ept_vo2.getGp_t_size().equals(gp_ept_vo.getGp_t_size())) {
							
							gp_ept_vo2.setGp_t_num(gp_ept_vo2.getGp_t_num()+gp_ept_vo.getGp_t_num());
							
							list_ep.setElementAt(gp_ept_vo2, i);
							match=true;
						}
					}
					//商品與購物車裡的商品不一樣
					if(!match) {
						list_ep.add(gp_ept_vo);
					}
				}
				
				session.setAttribute("list_ep", list_ep);
				String url="/front-end/gp_ept/addGp_Ept.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
		
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp_ept/addGp_Ept.jsp");
				failureView.forward(req, res);
			}
		
	}
		/*****************************************/	
		
		/*****************************************
		 * 來自租裝請求
		 **********************************/
		if ("lease".equals(action)) { // 來自listAllAgp_List.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ***************************************/
				 session = req.getSession();
//			String act_list_no = (String) session.getAttribute("act_list_no");
				String act_list_no = req.getParameter("act_list_no");
				String mem_no =req.getParameter("mem_no");
//			String mem_no =(String) session.getAttribute("mem_no");
				
				/*************************** 2.開始刪除資料 ***************************************/
				
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				session.setAttribute("act_list_no", act_list_no);
				session.setAttribute("mem_no", mem_no);
				String url = "/front-end/gp_ept/addGp_Ept.jsp";
				

				MemVO mem_vo = (MemVO)session.getAttribute("memVO");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Agp_List_Service agp_list_Svc = new Agp_List_Service();
				
				Set<Agp_List_VO> agp_list =  agp_list_Svc.getJoinGroup(mem_vo.getMem_no(), "待審核");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("Join_management", agp_list); // 資料庫取出的act_list_vo物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/agp_list/listAllAgp_List.jsp");
				failureView.forward(req, res);
			}
		}
		/*****************************************/
	}
	/*****************************************/
	private Gp_Ept_VO getGpEptNum(HttpServletRequest req) {
		
		
		String epc_no = req.getParameter("epc_no");
		String epc_name = req.getParameter("epc_name");			
		String epc_size = req.getParameter("epc_size");			
		Integer gp_t_num = new Integer(req.getParameter("gp_t_num")).intValue();
		
//		System.out.println(epc_no);
//		System.out.println(epc_name);
//		System.out.println(epc_size);
//		System.out.println(gp_t_num);			
		
		Gp_Ept_VO gp_ept_vo = new Gp_Ept_VO();
		
		gp_ept_vo.setEpc_no(epc_no);
		gp_ept_vo.setGp_t_size(epc_size);
		gp_ept_vo.setGp_t_num(gp_t_num);
		return gp_ept_vo;
	}
	
}
