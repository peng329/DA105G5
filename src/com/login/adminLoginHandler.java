package com.login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.webmaster.model.*;


import javax.servlet.annotation.WebServlet;

@WebServlet("/adminLoginhandler")
public class adminLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;


  protected boolean allowUser(String wm_id, String wm_psw, HttpServletRequest req) {
   
	//資料庫搜尋比對
	WebmasterService webmasterSvc = new WebmasterService();
	WebmasterVO webmasterVO = webmasterSvc.getOneWmById(wm_id);

	if(webmasterVO == null || !(webmasterVO.getWm_psw().equals(wm_psw)) ) {
		return false;
	}
	
	//登入成功，將webmasterVO存入session
	HttpSession session = req.getSession();
	session.setAttribute("webmasterVO", webmasterVO); 
	
	return true;
    
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");
    PrintWriter out = res.getWriter();

    // 【取得使用者 帳號(wm_id) 密碼(wm_psw)】
    String wm_id = req.getParameter("wm_id");
    String wm_psw = req.getParameter("wm_psw");

    // 【檢查該帳號 , 密碼是否有效】
    if (!allowUser(wm_id, wm_psw, req)) {          //【帳號 , 密碼無效時】
//      out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
//      out.println("<BODY>你的帳號 , 密碼無效!<BR>");
//      out.println("請按此重新登入 <A HREF="+req.getContextPath()+"/back-end/webmaster/adminLogin.jsp>重新登入</A>");
//      out.println("</BODY></HTML>");
    	
    	RequestDispatcher failureView = req
				.getRequestDispatcher("/back-end/webmaster/adminLogin.jsp");
		failureView.forward(req, res);
		return;//程式中斷
    }else {                                       //【帳號 , 密碼有效時, 才做以下工作】
      HttpSession session = req.getSession();
      
      session.setAttribute("wm_id", wm_id);   //*工作1: 才在session內做已經登入過的標識
      
       try {                                                        
         String adminLocation = (String) session.getAttribute("adminLocation");
         if (adminLocation != null) {
           session.removeAttribute("adminLocation");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
           res.sendRedirect(adminLocation);            
           return;
         }
       }catch (Exception ignored) { }

      res.sendRedirect(req.getContextPath()+"/back-end/webmaster/adminIndex.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
    }
  }
}