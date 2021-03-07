package com.locale.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dive_point.model.DpService;
import com.dive_point.model.DpVO;
import com.locale.model.LocService;
import com.locale.model.LocVO;


@WebServlet(urlPatterns = {"/ajaxLoc.do"})
public class LocServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

//listDpsBylocno:OK
		// 來自select_page.jsp的請求 // 來自 locale/listAllLoc.jsp的請求
		if ("listDpsByLocno_A".equals(action) || "listDpsByLocno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String loc_no = new String(req.getParameter("loc_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				LocService locSvc = new LocService();
				Set<DpVO> set = locSvc.getDpsByLocno(loc_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listDpsByLocno", set); // 資料庫取出的set物件,存入request
				String url = null;
				if ("listDpsByLocno_A".equals(action)) {
					System.out.println("listDpsByLocno");// 哪裡來哪裡回去
					url = "/back-end/locale/listDpsByLocno.jsp";
				} // 成功轉交 locale/listDpsByLocno.jsp
				else if ("listDpsByLocno_B".equals(action)) {
					System.out.println("listAllLoc");// 哪裡來哪裡回去
					url = "/back-end/locale/listAllLoc.jsp";
				} // 成功轉交 locale/listAllLoc.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
// getOne_For_Update：OK
		if ("getOne_For_Update".equals(action)) { // 來自listAllDp.jsp的請求，由列表資料右側"修改"按鈕觸發，送loc_no進來

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String loc_no = new String(req.getParameter("loc_no").trim());
				System.out.println(loc_no);
				/*************************** 2.開始查詢資料 ****************************************/
				System.out.println("2------------------------in");
				LocService locSvc = new LocService();
				LocVO locVO = locSvc.getOneLoc(loc_no);
				System.out.println("2------------------------out");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				System.out.println("3------------------------in");
				req.setAttribute("locVO", locVO); // 資料庫取出的locVO物件,存入req
				String url = "/back-end/locale/update_loc_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_Dp_input.jsp
				successView.forward(req, res);
				System.out.println("3------------------------out");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println("4------------------------in");
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/listAllLoc.jsp");
				failureView.forward(req, res);
			}
		}
//update:ok
		//可以改的只有地區名稱跟國家代碼，天氣不能
		if ("update".equals(action)) { // 來自update_loc_input.jsp的請求
			System.out.println("update-------------1");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String loc_no = new String(req.getParameter("loc_no").trim());
				LocService locSvc = new LocService();
				LocVO locVO = locSvc.getOneLoc(loc_no);

				System.out.println(loc_no);
				String ctry = new String(req.getParameter("ctry").trim());
//				String ctryReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";//原始姓名輸入的regex
				if (ctry == null || ctry.trim().length() == 0) {
					errorMsgs.add("國家代號不能為空");
//					} else if (!ctry.trim().matches(ctryReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				} else {
					System.out.println(ctry);
				}
				String sub_region = new String(req.getParameter("sub_region").trim());
				if (sub_region == null || sub_region.trim().length() == 0) {
					errorMsgs.add("地區名稱不能為空");
				}
				String weather = locVO.getWeather();// 不檢查
				System.out.println("weather get");

				locVO.setLoc_no(loc_no);
				locVO.setCtry(ctry);
				locVO.setSub_region(sub_region);
				locVO.setWeather(weather);

//				這種寫法是一起檢查，一次設定，也可以改成在每個if判斷是寫入檢查過的參數，
//				但這樣locVO就必須先寫出來，不管出錯不出錯，這裡給LocVO的資料，
//				都會在#181被更新。
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("locVO", locVO); // 含有輸入格式錯誤的locVO物件,也存入req,不存的話，每次出錯回送的頁面之前輸入過的就白打
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/update_loc_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/

				locVO = locSvc.updateLoc(loc_no, ctry, sub_region, weather);
				System.out.println("updating");
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("locVO", locVO); // 資料庫update成功後,正確的的locVO物件,存入req
				String url = "/back-end/locale/listAllLoc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneLoc.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/update_loc_input.jsp");
				failureView.forward(req, res);
			}
		}
//insert
		if ("insert".equals(action)) { // 來自addLoc.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String sub_region = req.getParameter("sub_region");
				if (sub_region == null || sub_region.trim().length() == 0) {
					errorMsgs.add("請輸入地區名稱");

				}

				String ctry = req.getParameter("ctry");
//				String dp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ctry == null || ctry.trim().length() == 0) {
					errorMsgs.add("請輸入國家代號，參考ISO 3166-1三位字母代碼");
				}
				if (ctry.trim().length() > 3) {
					errorMsgs.add("參考ISO 3166-1三位字母代碼，請輸入三位字母");
				}

				String weather = req.getParameter("weather");
//				String dp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (weather == null || weather.trim().length() == 0) {
					weather = "";// 資料會自動更新，所以不管他輸入甚麼都OK
				}

				LocVO locVO = new LocVO();
				locVO.setSub_region(sub_region);
				locVO.setCtry(ctry);
				locVO.setWeather(weather);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("locVO", locVO);// 含有輸入格式錯誤的dpVO物件,也存入req
					System.out.println(req.getContextPath());

					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/addLoc.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				LocService locSvc = new LocService();
				locVO = locSvc.addLoc(ctry, sub_region, weather);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/locale/listAllLoc.jsp";
				System.out.println(url);
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/addLoc.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAllLoc".equals(action)) { // for AJAX
			System.out.println(action);
			JSONArray arr = new JSONArray();
			try {
				/*************************** 2.開始查詢資料 *****************************************/
				LocService locSvc = new LocService();
				List<LocVO> list = locSvc.getAll();
				for (LocVO vo : list) {
					JSONObject obj = new JSONObject();
					obj.put("loc_no", vo.getLoc_no());
					obj.put("ctry", vo.getCtry());
					obj.put("sub_region", vo.getSub_region());
					obj.put("weather", vo.getWeather());
					arr.put(obj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/plain");
			PrintWriter writer = res.getWriter();
			writer.append(arr.toString());
			writer.flush();
			writer.close();
		} else if ("getOneLoc".equals(action)) { // for AJAX
			System.out.println(action);
			JSONObject obj = new JSONObject();
			try {
				String dp_no = req.getParameter("dp_no");
				/*************************** 2.開始查詢資料 *****************************************/
				DpService dpSvc = new DpService();
				DpVO vo = dpSvc.getOneDp(dp_no);
				if (vo != null) {
					obj.put("dp_no", vo.getDp_no());
					obj.put("dp_name", vo.getDp_name());
					obj.put("dp_info", vo.getDp_info());
					obj.put("dp_lat", vo.getDp_lat());
					obj.put("dp_lng", vo.getDp_lng());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/plain");
			PrintWriter writer = res.getWriter();
			writer.append(obj.toString());
			writer.flush();
			writer.close();
		}
		
		
		if ("delete_Loc".equals(action))

		{ // 來自/locale/listAllLoc.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String loc_no = new String(req.getParameter("loc_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				LocService locSvc = new LocService();
				locSvc.deleteLoc(loc_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/locale/listAllLoc.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後, 成功轉交 回到 /locale/listAllLoc.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/locale/listAllLoc.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
