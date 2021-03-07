package android.lespic.controller;



import android.lespic.model.LespicDAO_interface;
import android.lespic.model.LespicJNDIDAO;
import android.lespic.model.LespicVO;
import android.util.main.ImageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LespicServletApp extends HttpServlet {
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
		LespicDAO_interface dao_interface= new LespicJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("findByPrimaryKey".equals(action)) {
			String lpic_seq = jsonObject.get("lpic_seq").getAsString();
			LespicVO lespicVO = dao_interface.findByPrimaryKey(lpic_seq);
			writeText(res,lespicVO == null? "": gson.toJson(lespicVO));
		} 
		
		else if ("findByLes_no".equals(action)) {
			String les_no = jsonObject.get("les_no").getAsString();
			List<LespicVO> lespicList = dao_interface.findByLes_no(les_no);
			writeText(res, lespicList == null ? "" : gson.toJson(lespicList));
		} 
		
		else if ("getAll".equals(action)) {
		    List<LespicVO> lespicList = dao_interface.getAll();
			writeText(res, lespicList == null ? "" : gson.toJson(lespicList));
		} 
		
		else if ("getLespic".equals(action)) {
			OutputStream os = res.getOutputStream();
			String les_no = jsonObject.get("les_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			
			byte[] lespic = dao_interface.getLespic(les_no);
			if (lespic != null) {
				// 縮圖 in server side
				lespic = ImageUtil.shrink(lespic, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(lespic.length);
			}
			os.write(lespic);
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

