package android.diveshop.controller;

import android.diveshop.model.DiveshopDAO_interface;
import android.diveshop.model.DiveshopJNDIDAO;
import android.diveshop.model.DiveshopVO;
import android.dspic.model.DspicDAO_interface;
import android.dspic.model.DspicJNDIDAO;

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
import com.google.gson.JsonObject;

public class DiveshopServletApp extends HttpServlet {
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
		DiveshopDAO_interface dao_interface= new DiveshopJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if (action.equals("findByPrimaryKey")) {
			String ds_no = jsonObject.get("ds_no").getAsString();
			DiveshopVO diveshop = dao_interface.findByPrimaryKey(ds_no);
			writeText(res,gson.toJson(diveshop));
		} 
		else if("findById".equals(action)) {
			String dsaccount = jsonObject.get("dsaccount").getAsString();
			DiveshopVO diveshop = dao_interface.findById(dsaccount);
			writeText(res,diveshop==null?"":gson.toJson(diveshop));
		}
		else if ("getAll".equals(action)) {
			List<DiveshopVO> list = dao_interface.getaAll();
			DspicDAO_interface picDao = new DspicJNDIDAO();
			for (DiveshopVO vo : list) {
				int count = picDao.getDpicCount(vo.getDs_no());
				vo.setCount(count);
			}
			writeText(res, list==null?"":gson.toJson(list));
		}
	    else if (action.equals("getShopByAddress")) {
			String address = jsonObject.get("address").getAsString();
			writeText(res, String.valueOf(dao_interface.getShopByAddress(address)));
		} 
	    else if ("isDiveshopMem".equals(action)) {
	    	String dsaccount = jsonObject.get("dsaccount").getAsString();
	    	String dspaw = jsonObject.get("dspaw").getAsString();
	    	writeText(res, String.valueOf(dao_interface.isDiveshopMem(dsaccount, dspaw)));
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

