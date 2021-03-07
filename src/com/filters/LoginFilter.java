package com.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.lesson.model.LessonVO;

public class LoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		
		String localUri = req.getContextPath() + "/front-end/mem/memLogin.jsp";
		Object mem_id = session.getAttribute("mem_id");
		if (mem_id == null) {
			
			LessonVO lessonVO = (LessonVO) session.getAttribute("lessonVO");
			String url =  req.getParameter("requestURL");
			
			//當來源網址不等於登入網址時，才進if
			if(!(req.getRequestURI().equals(localUri))) {
				session.setAttribute("location", req.getRequestURI());
				if(url != null) {
					System.out.println(url);
					
					String actionurl = req.getHeader("Referer") +"?action=getOne_For_Display&les_no="+lessonVO.getLes_no()+"&ds_no="+lessonVO.getDs_no();
					System.out.println(actionurl);
					
					session.setAttribute("location", actionurl);
					
				}
				
				res.sendRedirect(localUri);
				return;
			}
			chain.doFilter(request, response);
			
		
		} else {
			if(req.getRequestURI().equals(localUri)) {
				res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
			}
			chain.doFilter(request, response);
		}
	}
}