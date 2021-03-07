package com.dspic.controller;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import com.dspic.model.DspicService;
import com.dspic.model.DspicVO;
import com.lespic.model.LespicService;
import com.lespic.model.LespicVO;

public class DBGifReader4 extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		HttpSession session = req.getSession();

		try {
			// 初始化
			String primaryKey = "";
			byte[] imgBuf = null;
			String oneImg = primaryKey+"oneImg";
			List<DspicVO> multiPics = null;
			List<LespicVO> lesPicList = null;
			DspicVO dspicVO = null;
			LespicVO lespicVO = null;
			Integer index = null;
			String imgsList = primaryKey + "Imgs"; // session
			
			// 取出第一個primaryKey
			Enumeration<String> primaryKeyE = req.getParameterNames();
			if (primaryKeyE.hasMoreElements()) {
				primaryKey = primaryKeyE.nextElement();
			}
			
			String value = req.getParameter(primaryKey);
			
			switch (primaryKey) {
				case "wrongImg" :
					imgBuf = (byte[])session.getAttribute("wrongImg");
					//session.removeAttribute("wrongImg");
					break;
					
				case "ds_no": 
					String temp_index = req.getParameter("index"); // index=1
					try {
						index = Integer.parseInt(temp_index);
					} catch (NumberFormatException nfe) {
					}
					if ((multiPics = (List<DspicVO>) session.getAttribute(imgsList))!= null) {
						
					} else {
						DspicService dspicSvc = new DspicService();
						multiPics = dspicSvc.getDspicByDsno(value);
						session.setAttribute("imgsList", imgsList);
					}
					imgBuf = multiPics.get(index).getDpic();
					break;
					
				case "les_no": 
					String les_index = req.getParameter("index"); // index=1
					try {
						index = Integer.parseInt(les_index);
					} catch (NumberFormatException nfe) {
					}
					if ((lesPicList = (List<LespicVO>) session.getAttribute(imgsList))!= null) {
						
					} else {
						LespicService lespicSvc = new LespicService();
						lesPicList = lespicSvc.getLespicByLes_no(value);
						session.setAttribute("imgsList", imgsList);
					}
					imgBuf = lesPicList.get(index).getLpic();
					break;
					
				case "dpic_seq":
					DspicService dispicSvc = new DspicService();
					dspicVO = dispicSvc.getOneDspic(value);
					imgBuf = dspicVO.getDpic();
					
					break;
					
				case "lpic_seq":
					LespicService lespicSvc = new LespicService();
					lespicVO = lespicSvc.getOneLespic(value);
					imgBuf = lespicVO.getLpic();
					
					break;
			}
			
			out.write(imgBuf);
			
			
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/NoData/nodata.jpg");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}

	}

//	try {
	/****** 1.顯示潛店圖片 ********/
//		String dpic_seq= req.getParameter("dpic_seq");
//		DspicService dspicService = new DspicService();
//		DspicVO dspicVO = dspicService.getOneDspic(dpic_seq);
//		out.write(dspicVO.getDpic());
//	/****** 2.取出多張圖片 ************/
//		Statement stmt = con.createStatement();
//		String dpic_seq = req.getParameter("dpic_seq");
//		
//		ResultSet rs = stmt
//				.executeQuery("SELECT DPIC FROM DSPIC WHERE DPIC_SEQ='"+dpic_seq+"'");
//		System.out.println(rs);S
//		
//		
//		if (rs.next()) {
//			
//			BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("DPIC"));
//			
//			byte[] buf = new byte[4 * 1024]; // 4K buffer
//			int len =0 ;
//			while ((len = in.read(buf)) != -1) {
//				out.write(buf, 0, len);
//			}
//			in.close();
//		} else {
//			res.sendError(HttpServletResponse.SC_NOT_FOUND);
//		}
//		rs.close();
//		stmt.close();
//		
//			
//	} catch (Exception e) {
//		System.out.println(e);
//		InputStream in = getServletContext().getResourceAsStream("/NoData/nodata.jpg");
//		byte[] buf = new byte[in.available()];
//		in.read(buf);
//		out.write(buf);
//		in.close();
//	}

}