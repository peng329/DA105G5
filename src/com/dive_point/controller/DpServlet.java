package com.dive_point.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dive_point.model.DpService;
import com.dive_point.model.DpVO;

@MultipartConfig
@WebServlet(urlPatterns = {"/ajaxDp.do"})
public class DpServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//getOne_For_Display:OK

		System.out.println(action + "called");
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("getOne_For_Display-------------1");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String dp_no = req.getParameter("dp_no");

				if (dp_no == null || (dp_no.trim()).length() == 0) {
					errorMsgs.add("請輸入潛點編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/dp_home.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				System.out.println("getOne_For_Display-------------2");
				// Send the use back to the form, if there were errors

				/*************************** 2.開始查詢資料 *****************************************/
				DpService dpSvc = new DpService();
				DpVO dpVO = dpSvc.getOneDp(dp_no);
				if (dpVO == null) {
					errorMsgs.add("查無資料");
					System.out.println("getOne_For_Display-------------3");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/dp_home.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				System.out.println("getOne_For_Display-------------4");

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("dpVO", dpVO); // 資料庫取出的dpVO物件,存入req
				String url = "/back-end/dive_point/listOneDp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneDp.jsp
				successView.forward(req, res);
				System.out.println("getOne_For_Display-------------5");
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料：" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/dp_home.jsp");
				failureView.forward(req, res);
			}
			System.out.println("getOne_For_Display-------------6");
		}

// getOne_For_Update�GOK

		if ("getOne_For_Update".equals(action)) { // 來自listAllDp.jsp的請求，由列表資料右側"修改"按鈕觸發，送dp_no進來

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String dp_no = new String(req.getParameter("dp_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				DpService dpSvc = new DpService();
				DpVO dpVO = dpSvc.getOneDp(dp_no);
				if (dpVO.getDp_pic1() != null) {
					System.out.println();
				}
				if (dpVO.getDp_pic1() != null) {
					if (dpVO.getDp_pic1().length != 0) {
						System.out.println(dpVO.getDp_pic1().length);
					}
				}
				if (dpVO.getDp_pic2() != null) {
					if (dpVO.getDp_pic2().length != 0) {
						System.out.println(dpVO.getDp_pic2().length);
					}
				}
				if (dpVO.getDp_pic3() != null)
					if (dpVO.getDp_pic3().length != 0) {
						{
							System.out.println(dpVO.getDp_pic3().length);
						}
					}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("dpVO", dpVO); // 資料庫取出的dpVO物件,存入req
				String url = "/back-end/dive_point/update_dp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_Dp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dive_point/listAllDp.jsp");
				failureView.forward(req, res);
			}
		}
		// update：OK 20200208
		if ("update".equals(action)) { // 來自update_dp_input.jsp的請求
			System.out.println("update-------------1");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String dp_no = new String(req.getParameter("dp_no").trim());
				System.out.println(dp_no);
				String loc_no = req.getParameter("loc_no").trim();
				if (loc_no == null || loc_no.trim().length() == 0) {
					errorMsgs.add("地區編號請勿空白");
				} else {
					System.out.println(loc_no);
				}
				System.out.println("update-------------2");
				String dp_name = req.getParameter("dp_name");
//				String dp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";//原始姓名輸入的regex
				if (dp_name == null || dp_name.trim().length() == 0) {
					errorMsgs.add("請輸入潛點名稱");
//					} else if (!dp_name.trim().matches(dp_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				} else {
					System.out.println(dp_name);
				}
				System.out.println("update-------------3");
				Double dp_lat = null;
				try {
					dp_lat = new Double(req.getParameter("dp_lat").trim());
					System.out.println(dp_lat);
				} catch (NumberFormatException e) {
					dp_lat = 0.0;
					errorMsgs.add("經度請填數字");
				}
				System.out.println("update-------------4");
				Double dp_lng = null;
				try {
					dp_lng = new Double(req.getParameter("dp_lng").trim());
					System.out.println(loc_no);
				} catch (NumberFormatException e) {
					dp_lng = 0.0;
					errorMsgs.add("緯度請填數字.");
				}
				System.out.println("update-------------5");
				String dp_info = new String(req.getParameter("dp_info"));
				if (dp_info == null || dp_info.trim().length() == 0) {
					errorMsgs.add("潛點資訊請勿空白");
				} else {
					System.out.println(dp_info);
				}
				System.out.println("update-------------6");
//				以下圖片處理跳過
//				測試參數名稱
				System.out.println("paran below---------------------");
				Enumeration<String> paran = req.getParameterNames();
				while (paran.hasMoreElements()) {
					System.out.println(paran.nextElement());
				}
				Collection<Part> parlist = req.getParts();

				for (Part part : parlist) {
					System.out.println(part.getSize());
				}
				DpService dpSvcForPic = new DpService();
				DpVO dpVOForPic = dpSvcForPic.getOneDp(dp_no);
				byte[] dp_pic1 = null;
				if (req.getPart("dp_pic1").getInputStream().available() != 0) {
					Part pic1part = req.getPart("dp_pic1");
					if (getFileNameFromPart(pic1part) != null && pic1part.getContentType() != null) {
						System.out.println("update-------------6-3");
						if (pic1part.getSize() != 0) {
							InputStream pic1in = pic1part.getInputStream();
							dp_pic1 = new byte[pic1in.available()];
							pic1in.read(dp_pic1);
							pic1in.close();
						}
					}
				} else {
					if (!"delete".equals(req.getParameter("dp_pic1d"))) {
						dp_pic1 = dpVOForPic.getDp_pic1();
					}
				}

//				out.println("buffer length: " + buf.length);//可以指定上述pic的陣列，測試長度
				byte[] dp_pic2 = null;
				System.out.println("update-------------7");
				if (req.getPart("dp_pic2").getInputStream().available() != 0) {
					Part pic2part = req.getPart("dp_pic2");
					if (getFileNameFromPart(pic2part) != null && pic2part.getContentType() != null) {
						if (pic2part.getSize() != 0) {
							System.out.println(pic2part.getSize());
							InputStream pic2in = pic2part.getInputStream();
							dp_pic2 = new byte[pic2in.available()];
							pic2in.read(dp_pic2);
							pic2in.close();
							System.out.println(pic2part.getInputStream().available() + ",22222222222");
						}
					}
				} else {
					if (!"delete".equals(req.getParameter("dp_pic2d"))) {
						dp_pic2 = dpVOForPic.getDp_pic2();
					}
				}

				byte[] dp_pic3 = null;
				System.out.println("update-------------8");

				if (req.getPart("dp_pic3").getInputStream().available() != 0) {
					Part pic3part = req.getPart("dp_pic3");
					if (getFileNameFromPart(pic3part) != null && pic3part.getContentType() != null) {
						if (pic3part.getSize() != 0) {
							System.out.println(pic3part.getSize());
							InputStream pic3in = pic3part.getInputStream();
							dp_pic3 = new byte[pic3in.available()];
							pic3in.read(dp_pic3);
							pic3in.close();
							System.out.println(pic3part.getInputStream().available() + ",333333333333");
						}
					}
				} else {
					if (!"delete".equals(req.getParameter("dp_pic3d"))) {
						dp_pic3 = dpVOForPic.getDp_pic3();
					}
				}

				byte[] dp_pic4 = null;
				System.out.println("update-------------9");
				if (req.getPart("dp_pic4").getInputStream().available() != 0) {
					System.out.println("dp4 lv1");
					Part pic4part = req.getPart("dp_pic4");
					if (getFileNameFromPart(pic4part) != null && pic4part.getContentType() != null) {
						System.out.println("dp4 lv2");
						if (pic4part.getSize() != 0) {
							System.out.println(pic4part.getSize());
							InputStream pic4in = pic4part.getInputStream();
							dp_pic4 = new byte[pic4in.available()];
							pic4in.read(dp_pic4);
							pic4in.close();
						}
					}
				} else {
					System.out.println("dp4 lv3");
					if (!"delete".equals(req.getParameter("dp_pic4d"))) {
						System.out.println("dp4 lv4");
						dp_pic4 = dpVOForPic.getDp_pic4();
					}
				}
//				dp_pic1 = new Bytes(req.getParameter("dp_pic1").trim());

				DpVO dpVO = new DpVO();
				dpVO.setDp_no(dp_no);
				dpVO.setLoc_no(loc_no);
				dpVO.setDp_name(dp_name);
				dpVO.setDp_lat(dp_lat);
				dpVO.setDp_lng(dp_lng);
				dpVO.setDp_info(dp_info);
				dpVO.setDp_pic1(dp_pic1);
				dpVO.setDp_pic2(dp_pic2);
				dpVO.setDp_pic3(dp_pic3);
				dpVO.setDp_pic4(dp_pic4);
//				這種寫法是一起檢查，一次設定，也可以改成在每個if判斷是寫入檢查過的參數，
//				但這樣dpVO就必須先寫出來，不管出錯不出錯，這裡給dpVO的資料，
//				都會在#181被更新。
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpVO", dpVO); // 含有輸入格式錯誤的dpVO物件,也存入req,不存的話，每次出錯回送的頁面之前輸入過的就白打
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/dive_point/update_dp_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				DpService dpSvc = new DpService();
				dpVO = dpSvc.updateDp(dp_no, loc_no, dp_name, dp_lat, dp_lng, dp_info, dp_pic1, dp_pic2, dp_pic3,
						dp_pic4);
				System.out.println("update-------------10-2");
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("dpVO", dpVO); // 資料庫update成功後,正確的的dpVO物件,存入req
				String url = "/back-end/dive_point/listOneDp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dive_point/update_dp_input.jsp");
				failureView.forward(req, res);
			}
		}
//insert
		if ("insert".equals(action)) { // 來自addDp.jsp的請求
			System.out.println("insert in");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String loc_no = new String(req.getParameter("loc_no").trim());
				System.out.println("insert-------------0");

				String dp_name = req.getParameter("dp_name");
				if (dp_name == null || dp_name.trim().length() == 0) {
					errorMsgs.add("請輸入潛點名稱");

				}
				Double dp_lat = null;
				try {
					dp_lat = new Double(req.getParameter("dp_lat").trim());
				} catch (NumberFormatException e) {
					dp_lat = 0.0;
					errorMsgs.add("緯度請輸入數字");
				}
				Double dp_lng = null;
				try {
					dp_lng = new Double(req.getParameter("dp_lng").trim());
				} catch (NumberFormatException e) {
					dp_lng = 0.0;
					errorMsgs.add("經度請輸入數字");
				}

				String dp_info = req.getParameter("dp_info");
//				String dp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (dp_info == null || dp_info.trim().length() == 0) {
					errorMsgs.add("請輸入潛點資訊");
				}
				System.out.println("insert-------------3");
//				以下圖片處理跳過
				byte[] dp_pic1 = null;
				Part pic1part = req.getPart("dp_pic1");
				if (getFileNameFromPart(pic1part) != null && pic1part.getContentType() != null) {
					if (pic1part.getSize() != 0) {
						InputStream pic1in = pic1part.getInputStream();
						dp_pic1 = new byte[pic1in.available()];
						pic1in.read(dp_pic1);
						pic1in.close();
					}
				} else {
					errorMsgs.add("新增潛點必須有第一張圖片");
				}

//				out.println("buffer length: " + buf.length);//可以指定上述pic的陣列，測試長度
				byte[] dp_pic2 = null;
				Part pic2part = req.getPart("dp_pic2");
				System.out.println("insert-------------4");
				if (getFileNameFromPart(pic2part) != null && pic2part.getContentType() != null) {
					if (pic2part.getSize() != 0) {
						InputStream pic2in = pic2part.getInputStream();
						dp_pic2 = new byte[pic2in.available()];
						pic2in.read(dp_pic2);
						pic2in.close();
					}
				}

				byte[] dp_pic3 = null;
				Part pic3part = req.getPart("dp_pic3");
				if (getFileNameFromPart(pic3part) != null && pic3part.getContentType() != null) {
					if (pic3part.getSize() != 0) {
						InputStream pic3in = pic3part.getInputStream();
						dp_pic3 = new byte[pic3in.available()];
						pic3in.read(dp_pic3);
						pic3in.close();
					}
				}

				byte[] dp_pic4 = null;
				Part pic4part = req.getPart("dp_pic4");
				if (getFileNameFromPart(pic4part) != null && pic4part.getContentType() != null) {
					if (pic4part.getSize() != 0) {
						InputStream pic4in = pic4part.getInputStream();
						dp_pic4 = new byte[pic4in.available()];
						pic4in.read(dp_pic4);
						pic4in.close();
					}
				}

				DpVO dpVO = new DpVO();
				dpVO.setLoc_no(loc_no);
				dpVO.setDp_name(dp_name);
				dpVO.setDp_lat(dp_lat);
				dpVO.setDp_lng(dp_lng);
				dpVO.setDp_info(dp_info);
				dpVO.setDp_pic1(dp_pic1);
				dpVO.setDp_pic2(dp_pic2);
				dpVO.setDp_pic3(dp_pic3);
				dpVO.setDp_pic4(dp_pic4);

				System.out.println("insert-------------6");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpVO", dpVO);// 含有輸入格式錯誤的dpVO物件,也存入req
					System.out.println("insert-------------7");
					System.out.println(req.getContextPath());

					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dive_point/addDp.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				DpService dpSvc = new DpService();
				dpVO = dpSvc.addDp(loc_no, dp_name, dp_lat, dp_lng, dp_info, dp_pic1, dp_pic2, dp_pic3, dp_pic4);
				System.out.println("insert-------------8");
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/dive_point/listAllDp.jsp";
				System.out.println(url);
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println("insert-------------9");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dive_point/addDp.jsp");
				failureView.forward(req, res);
			}
		}
		// delete：OK 20200208
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String dp_no = new String(req.getParameter("dp_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				DpService dpSvc = new DpService();
				dpSvc.deleteDp(dp_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/dive_point/listAllDp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/dive_point/listAllDp.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getAllDp".equals(action)) { // for AJAX
			System.out.println(action);
			JSONArray arr = new JSONArray();
			try {
				/*************************** 2.開始查詢資料 *****************************************/
				DpService dpSvc = new DpService();
				List<DpVO> list = dpSvc.getAll();
				for (DpVO vo : list) {
					JSONObject obj = new JSONObject();
					obj.put("dp_no", vo.getDp_no());
					obj.put("loc_no", vo.getLoc_no());
					obj.put("dp_name", vo.getDp_name());
					obj.put("dp_info", vo.getDp_info());
					obj.put("dp_lat", vo.getDp_lat());
					obj.put("dp_lng", vo.getDp_lng());
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
					obj.put("loc_no", vo.getLoc_no());
					obj.put("dp_lat", vo.getDp_lat());
					obj.put("dp_lng", vo.getDp_lng());
					obj.put("dp_info", vo.getDp_info());
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
	}
	

	public String getFileNameFromPart(Part part) {
		System.out.println("update-------------gfnf1");
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName().trim();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			System.out.println("gf-------------0");
			return null;
		}
		System.out.println("gf-------------1");
		return filename;

	}
}
