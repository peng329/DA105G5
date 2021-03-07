

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.mem.model.*;

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
			
			System.out.println("getOne_For_Update");
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
			System.out.println("------------------------------update");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_no = new String(req.getParameter("mem_no").trim());
				
				
				String mem_id = req.getParameter("mem_id");
				
				
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
							.getRequestDispatcher("/back-end/mem/update_mem_input.jsp");
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
				String url = "/back-end/mem/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMem.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/update_mem_input.jsp");
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
				
				String mem_state = "通過";

				

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
							.getRequestDispatcher("/back-end/mem/addMem.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/

				
				MemService memService = new MemService();
				memVO = memService.addMem(mem_id, mem_psw, mem_name, mem_sex, mem_bd, mem_mail, mem_phone, mem_add, mem_pic, reg_time, mem_rep_no, mem_state) ;
				
				
				//清除前面session的兩個屬性
				session.removeAttribute("imgSrc");
				session.removeAttribute("mem_pic2");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/mem/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMem.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/addMem.jsp");
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
	}
}
