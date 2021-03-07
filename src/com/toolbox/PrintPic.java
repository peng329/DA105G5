package com.toolbox;

import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;


public class PrintPic extends HttpServlet {
	
	Connection con;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("image/gif");
		ServletOutputStream out = response.getOutputStream();
		
		try {
			Statement stmt = con.createStatement();
			String epic_seq = request.getParameter("epic_seq").trim();
			ResultSet rs = stmt.executeQuery("SELECT EPIC FROM EQPIC WHERE EPIC_SEQ ='"+epic_seq+"'" );
			
			if(rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("EPIC"));
				byte[] epic = new byte[4*1024];
				int length;
				while((length = in.read(epic))!=-1) {
					out.write(epic,0,length);
				}
				in.close();
			}else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
				rs.close();
				stmt.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public void init() throws ServletException{
		try {

			Context ctx=new javax.naming.InitialContext();
			DataSource ds =(DataSource)ctx.lookup("java:comp/env/jdbc/DA105G5");
			con=ds.getConnection();
			
		}catch (NamingException e) {
			e.printStackTrace();
		}  catch(SQLException se){
			se.printStackTrace();
		}
	}

	public void destory() {
		try {
			if(con!=null)con.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}

}
