package com.util;


import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class LocPic4 extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");//
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			Statement stmt = con.createStatement();
			String dp_no = req.getParameter("dp_no");
			String dp_pic = req.getParameter("dp_pic");
			ResultSet rs = stmt.executeQuery("SELECT "+dp_pic+" FROM dive_point WHERE dp_no ='" + dp_no + "'");
			System.out.println(dp_pic);
			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(dp_pic));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {				System.out.println("4------------2");
//				res.sendError(HttpServletResponse.SC_NOT_FOUND);
//			�U�C�аѷ����qP77
				InputStream in = getServletContext().getResourceAsStream("/back-end/dive_point/images/nopic2.jfif");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("4------------3");
//			System.out.println(e);
			InputStream in = getServletContext().getResourceAsStream("/back-end/dive_point/images/nopic.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DA105G5");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}