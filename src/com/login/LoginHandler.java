package com.login;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import javax.servlet.annotation.WebServlet;

@WebServlet("/loginhandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;


  protected boolean allowUser(String mem_id, String mem_psw, HttpServletRequest req) {
   
	//資料庫搜尋比對
	MemService memSvc = new MemService();
	MemVO memVO = memSvc.getOneMemById(mem_id);

	if(memVO == null || !(memVO.getMem_psw().equals(mem_psw)) || !("通過".equals(memVO.getMem_state()))) {
		return false;
	}
	
	//登入成功，將memVO存入session
	HttpSession session = req.getSession();
	session.setAttribute("memVO", memVO); 
	
	return true;
    
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");
    PrintWriter out = res.getWriter();

   
    
    
    
    // 【取得使用者 帳號(mem_id) 密碼(mem_psw)】
    String mem_id = req.getParameter("mem_id");
    String mem_psw = req.getParameter("mem_psw");
    
    //錯誤處理用
	List<String> errorMsgs = new LinkedList<String>();
	req.setAttribute("errorMsgs", errorMsgs);
	
	//資料庫搜尋比對用
	MemService memSvc = new MemService();
	MemVO memVO = memSvc.getOneMemById(mem_id);
	
	if (memVO == null) {
		errorMsgs.add("找不到帳號，請重新輸入");
	} else if(!(memVO.getMem_psw().equals(mem_psw))) {
		errorMsgs.add("密碼有誤，請重新輸入");
    }else if(!("通過".equals(memVO.getMem_state()))) {
		errorMsgs.add("信箱未驗證，請收信開通");
	}
	
	
	
	
    // 【檢查該帳號 , 密碼是否有效】
//    if (!allowUser(mem_id, mem_psw, req)) {          //【帳號 , 密碼無效時】
    if (!errorMsgs.isEmpty()) {       

    	RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/mem/memLogin.jsp");
		failureView.forward(req, res);
		return;//程式中斷
    	
    }else {                                       //【帳號 , 密碼有效時, 才做以下工作】
      HttpSession session = req.getSession();
      
      session.setAttribute("mem_id", mem_id);   //*工作1: 才在session內做已經登入過的標識
      session.setAttribute("memVO", memVO);
      
       try {                                                        
         String location = (String) session.getAttribute("location");
         System.out.println(location);
         
         if (location != null) {
           session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
           res.sendRedirect(location);            
           return;
         }
       }catch (Exception ignored) { }

      res.sendRedirect(req.getContextPath()+"/front-end/index.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
    }
  }
}