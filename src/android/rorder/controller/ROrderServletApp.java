package android.rorder.controller;


import android.rorder.model.ROrderDAO_interface;
import android.rorder.model.ROrderJNDIDAO;
import android.rorder.model.ROrderVO;

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

public class ROrderServletApp extends HttpServlet {
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
		ROrderDAO_interface dao_interface= new ROrderJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("delete".equals(action)) {
			String ro_no = jsonObject.get("ro_no").getAsString(); 
			writeText(res,String.valueOf(dao_interface.delete(ro_no)));
		} 
		
		else if ("findAMemRo".equals(action)) {
			String mem_no = jsonObject.get("mem_no").getAsString();
			String ro_no = jsonObject.get("ro_no").getAsString();
			ROrderVO rorderVO =  dao_interface.findAMemRo(mem_no,ro_no);
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, gson2.toJson(rorderVO));
		}
	    else if ("getAllRoByAMem".equals(action)) {
			String mem_no = jsonObject.get("mem_no").getAsString();
			List<ROrderVO> rorderList = dao_interface.getAllRoByAMem(mem_no);
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, gson2.toJson(rorderList));
		} 
	    else if ("findOneRo".equals(action)) {
			String ro_no = jsonObject.get("ro_no").getAsString();
			ROrderVO rorder = dao_interface.findOneRo(ro_no);
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, rorder==null?"":gson2.toJson(rorder));
		} 
	    else if ("getAllDsRo".equals(action)) {
	    	String ds_no = jsonObject.get("ds_no").getAsString();
	    	List<ROrderVO> rorderList = dao_interface.getAllRoByAMem(ds_no);
	    	writeText(res, gson.toJson(rorderList));
	   }
	    else if("update".equals(action)){
//	    	Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    	ROrderVO rorderVO = gson.fromJson(jsonObject.get("ROrderVO").getAsString(), ROrderVO.class);
	    	writeText(res,String.valueOf(dao_interface.update(rorderVO)));
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

