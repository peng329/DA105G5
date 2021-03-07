package com.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class adminLoginFilter implements Filter {

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
		
		String localUri = req.getContextPath() + "/back-end/webmaster/adminLogin.jsp";
		Object wm_id = session.getAttribute("wm_id");
		if (wm_id == null) {
			session.setAttribute("adminLocation", req.getRequestURI());
			
			
			//當來源網址不等於登入網址時，才進if，跳轉至登入頁
			if(!(req.getRequestURI().equals(localUri))) {
				
				res.sendRedirect(localUri);
				return;
			}
			chain.doFilter(request, response);
			
		} else {
			if(req.getRequestURI().equals(localUri)) {
				res.sendRedirect(req.getContextPath() + "/back-end/webmaster/adminIndex.jsp");
			}
			chain.doFilter(request, response);
		}
	}
}