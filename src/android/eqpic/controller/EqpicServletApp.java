package android.eqpic.controller;


import android.eqpic.model.EqpicDAO_interface;
import android.eqpic.model.EqpicJNDIDAO;
import android.eqpic.model.EqpicVO;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class EqpicServletApp extends HttpServlet {
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
		EqpicDAO_interface dao_interface= new EqpicJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("findByPrimaryKey".equals(action)) {
			String epic_seq = jsonObject.get("epic_seq").getAsString(); 
			EqpicVO eqpicVO = dao_interface.findByPrimaryKey(epic_seq);
			writeText(res,eqpicVO==null?"":gson.toJson(eqpicVO));
		} 
		
		else if ("findByDsAll".equals(action)) {
			String ds_no = jsonObject.get("ds_no").getAsString();
			List<EqpicVO> eqpicList = dao_interface.findByDsAll(ds_no);
			writeText(res, eqpicList==null?"":gson.toJson(eqpicList));
		}
	    else if ("findByAEp_seq_All_Epic".equals(action)) {
			String ep_seq = jsonObject.get("ep_seq").getAsString();
			List<EqpicVO> eqpicList = dao_interface.findByAEp_seq_All_Epic(ep_seq);
			writeText(res,eqpicList==null?"":gson.toJson(eqpicList));
		} 
	    else if ("getEqpic".equals(action)) {
			OutputStream os = res.getOutputStream();
			String ds_no = jsonObject.get("ds_no").getAsString();
			String ep_seq = jsonObject.get("ep_seq").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] eqpic = dao_interface.getEqpic(ds_no,ep_seq);
			if (eqpic != null) {
				// 縮圖 in server side
				eqpic = ImageUtil.shrink(eqpic, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(eqpic.length);
			}
			os.write(eqpic);

		} else {
			writeText(res, "");
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


