package android.mem.controller;

import android.mem.model.*;
import android.util.main.ImageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class MemServletApp extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private ServletContext context;
	
	@Override
	public void init() throws ServletException {
		context = getServletContext();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		System.out.println("input: " + jsonIn);
		MemDAO_interface dao_interface= new MemJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if (action.equals("isMember")) {
			String mem_id = jsonObject.get("mem_id").getAsString();
			String mem_psw = jsonObject.get("mem_psw").getAsString();
			writeText(res,	String.valueOf(dao_interface.isMember(mem_id, mem_psw)));
		} 
		
		else if (action.equals("isMemIdExist")) {
			String mem_id = jsonObject.get("mem_id").getAsString();
			writeText(res, String.valueOf(dao_interface.isMemIdExist(mem_id)));
		} 
		
		else if (action.equals("add")) {
			MemVO memVO = gson.fromJson(jsonObject.get("memVO").getAsString(), MemVO.class);
			writeText(res, String.valueOf(dao_interface.add(memVO)));
		}
		else if ("getAll".equals(action)) {
			List<MemVO> list = dao_interface.getAll();
			Gson gson2=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, list == null ? "" : gson2.toJson(list));
		}
		
		else if ("findPkById".equals(action)) {
			String mem_id = jsonObject.get("mem_id").getAsString();
			String mem_no = dao_interface.findPkById(mem_id);
			Gson gson2=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, mem_no == null ? "" : gson2.toJson(mem_no));
		} 
		else if ("findNameByPk".equals(action)) {
			String mem_no = jsonObject.get("mem_no").getAsString();
			String mem_name = dao_interface.findNameByPk(mem_no);
			Gson gson2=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res,  mem_name == null ? "" : gson2.toJson(mem_name));
		} 
		else if (action.equals("update")) {
			MemVO memVO = gson.fromJson(jsonObject.get("memVO").getAsString(), MemVO.class);
			writeText(res, String.valueOf(dao_interface.update(memVO)));
		}
		else if("findOneByIdPsw".equals(action)) {
			String mem_id = jsonObject.get("mem_id").getAsString();
			String mem_psw = jsonObject.get("mem_psw").getAsString();
			MemVO memVO = dao_interface.findOneByIdPsw(mem_id, mem_psw);
			Gson gson2=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res,memVO==null?"":gson2.toJson(memVO));
		}
		else if ("findOnePicByPk".equals(action)) {
	    	OutputStream os = res.getOutputStream();
			String mem_no = jsonObject.get("mem_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] mempic = dao_interface.findOnePicByPk(mem_no);
			if (mempic != null) {
				// 縮圖 in server side
				mempic = ImageUtil.shrink(mempic, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(mempic.length);
			}
			os.write(mempic);
	    }
		else if("updatePic".equals(action)) {
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			String mem_no = jsonObject.get("mem_no").getAsString();
			// java.util.Base64 (Java 8 supports)
			byte[] image = Base64.getMimeDecoder().decode(imageBase64);
			boolean isUpdated = dao_interface.updatePic(mem_no, image);
			context.setAttribute("image", image);
            
			
			res.setContentType(CONTENT_TYPE);
			PrintWriter out = res.getWriter();
			out.println(jsonIn);
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
