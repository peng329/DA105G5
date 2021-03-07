package com.login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.diveshop.model.*;


import javax.servlet.annotation.WebServlet;

@WebServlet("/dsLoginhandler")
public class dsLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

   //【檢查使用者輸入的帳號(mem_id) 密碼(mem_psw)是否有效】
   //【實際上應至資料庫搜尋比對】
  protected boolean allowUser(String dsaccount, String dspaw, HttpServletRequest req) {
//    if ("divehouse".equals(mem_id) && "123456".equals(mem_psw))
//      return true;
//    else
//      return false;
    
	//資料庫搜尋比對
	DiveshopService diveshopSvc = new DiveshopService();
	DiveshopVO diveshopVO = diveshopSvc.findByDsaccount(dsaccount);

	if(diveshopVO == null || !(diveshopVO.getDspaw().equals(dspaw)) || !("審核通過".equals(diveshopVO.getDs_state()))) {
		return false;
	}
	
	//登入成功，將memVO存入session
	HttpSession session = req.getSession();
	session.setAttribute("diveshopVO", diveshopVO); 
	
	
	return true;
    
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");
    PrintWriter out = res.getWriter();
    
    // 【取得使用者 帳號， 密碼】
    String dsaccount = req.getParameter("mem_id");
    String dspaw = req.getParameter("mem_psw");
    
    // 【檢查該帳號 , 密碼是否有效】
    if (!allowUser(dsaccount, dspaw, req)) {          //【帳號 , 密碼無效時】
//      out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
//      out.println("<BODY>你的帳號 , 密碼無效!<BR>");
//      out.println("請按此重新登入 <A HREF="+req.getContextPath()+"/front-end/mem/dsLogin.jsp>重新登入</A>");
//      out.println("</BODY></HTML>");
    	RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/mem/dsLogin.jsp");
		failureView.forward(req, res);
    }else {                                       //【帳號 , 密碼有效時, 才做以下工作】
      HttpSession session = req.getSession();
      
      session.setAttribute("dsaccount", dsaccount);   //*工作1: 才在session內做已經登入過的標識
      
       try {                                                        
         String dsLocation = (String) session.getAttribute("dsLocation");
         if (dsLocation != null) {
           session.removeAttribute("dsLocation");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
           res.sendRedirect(dsLocation);            
           return;
         }
       }catch (Exception ignored) { }

      res.sendRedirect(req.getContextPath()+"/back-end/diveshop-master/diveshop-master.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
    }
  }
}