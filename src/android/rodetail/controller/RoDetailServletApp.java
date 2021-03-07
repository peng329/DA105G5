package android.rodetail.controller;


import android.rodetail.model.RoDetailDAO_interface;
import android.rodetail.model.RoDetailJNDIDAO;
import android.rodetail.model.RoDetailVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class RoDetailServletApp extends HttpServlet {
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
		RoDetailDAO_interface dao_interface= new RoDetailJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("findByPrimaryKey".equals(action)) {
			String ro_no = jsonObject.get("ro_no").getAsString(); 
			String ep_seq = jsonObject.get("ep_seq").getAsString(); 
			RoDetailVO rodetailVO = dao_interface.findByPrimaryKey(ro_no,ep_seq);
			writeText(res,rodetailVO==null?"":gson.toJson(rodetailVO));
		} 
		
		else if ("getSameRoRdAll".equals(action)) {
			String ro_no = jsonObject.get("ro_no").getAsString();
			List<RoDetailVO> rodetailList =  dao_interface.getSameRoRdAll(ro_no);
			writeText(res, rodetailList==null?"":gson.toJson(rodetailList));
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


