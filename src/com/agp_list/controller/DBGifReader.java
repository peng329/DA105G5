package com.agp_list.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class DBGifReader extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();

		try {
			Statement stmt = con.createStatement();
			String act_list_no = req.getParameter("act_list_no");
			String mem_no = req.getParameter("mem_no");

			ResultSet rs = stmt.executeQuery("SELECT mem_lic_pic FROM agp_list WHERE act_list_no ='" + act_list_no + "'"+" and mem_no='"+mem_no+"'");
			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("mem_lic_pic"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
//			InputStream in = new FileInputStream(getServletContext().getRealPath("/NoData/none.jpg"));
//			byte[] buf = new byte[in.available()];
//			in.read(buf);
//			out.write(buf);
//			in.close();
		}
	}

	public void init() throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "DA105G5", "DA105G5");
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
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
