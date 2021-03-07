package com.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class dsLoginFilter implements Filter {

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
		
		
		String localUri = req.getContextPath() + "/front-end/mem/dsLogin.jsp";
		Object dsaccount = session.getAttribute("dsaccount");
		if (dsaccount == null) {
			
			
			//當來源網址不等於登入網址時，才進if
			if(!(req.getRequestURI().equals(localUri))) {
				session.setAttribute("dsLocation", req.getRequestURI());
				res.sendRedirect(req.getContextPath() + "/front-end/mem/dsLogin.jsp");
				return;
			}
			chain.doFilter(request, response);
			

		} else {
			if(req.getRequestURI().equals(localUri)) {
				res.sendRedirect(req.getContextPath() + "/back-end/diveshop-master/diveshop-master.jsp");
			}
			chain.doFilter(request, response);
		}
	}
}