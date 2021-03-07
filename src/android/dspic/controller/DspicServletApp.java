package android.dspic.controller;

import android.dspic.model.DspicDAO_interface;
import android.dspic.model.DspicJNDIDAO;
import android.dspic.model.DspicVO;
import android.util.main.ImageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class DspicServletApp extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		System.out.println("input: " + jsonIn);
		DspicDAO_interface dao_interface= new DspicJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
	    if (action.equals("findByPrimaryKey")) {
			String dpic_seq = jsonObject.get("dpic_seq").getAsString();
			writeText(res, String.valueOf(dao_interface.findByPrimaryKey(dpic_seq)));
		} 
	    
	    else if (action.equals("findByDs_no")) {
			String ds_no = jsonObject.get("ds_no").getAsString();
			writeText(res, String.valueOf(dao_interface.findByDs_no(ds_no)));
		} 
	    
	    else if (action.equals("getAll")) {
	    	List<DspicVO> list = dao_interface.getAll();
			writeText(res, gson.toJson(list));
		}
	    
	    else if (action.equals("getDpic")) {
	    	OutputStream os = res.getOutputStream();
			String ds_no = jsonObject.get("ds_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			
			byte[] dpic = dao_interface.getDpic(ds_no,-1);
			if (dpic != null) {
				// 縮圖 in server side
				dpic = ImageUtil.shrink(dpic, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(dpic.length);
			}
			os.write(dpic);
	    } else if (action.equals("getDSPics")) {
	    	OutputStream os = res.getOutputStream();
			String ds_no = jsonObject.get("ds_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			int position = jsonObject.get("position").getAsInt();
			
			byte[] dpic = dao_interface.getDpic(ds_no, position);
			if (dpic != null) {
				// 縮圖 in server side
				dpic = ImageUtil.shrink(dpic, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(dpic.length);
			}
			os.write(dpic);
	    }
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);

	}

}
