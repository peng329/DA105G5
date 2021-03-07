package android.equip.controller;

import android.equip.model.EquipDAO_interface;
import android.equip.model.EquipJNDIDAO;
import android.equip.model.EquipVO;

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

public class EquipServletApp extends HttpServlet {
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
		EquipDAO_interface dao_interface= new EquipJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("findByDsEpNo".equals(action)) {
			String ds_no = jsonObject.get("ds_no").getAsString(); 
			String ep_no = jsonObject.get("ep_no").getAsString(); 
			EquipVO equipVO = dao_interface.findByDsEpNo(ds_no, ep_no);
			writeText(res,equipVO==null?"":gson.toJson(equipVO));
		} 
		
		else if ("findByPrimaryKey".equals(action)) {
			String ep_seq = jsonObject.get("ep_seq").getAsString();
			EquipVO equipVO =  dao_interface.findByPrimaryKey(ep_seq);
			writeText(res, equipVO==null?"":gson.toJson(equipVO));
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

