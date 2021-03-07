package android.divepoint.controller;

import android.divepoint.model.DivepointDAO_interface;
import android.divepoint.model.DivepointJNDIDAO;
import android.divepoint.model.DivepointVO;
import android.diveshop.model.DiveshopVO;
import android.dspic.model.DspicDAO_interface;
import android.dspic.model.DspicJNDIDAO;
import android.util.main.ImageUtil;
import oracle.net.aso.e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class DivepointServletApp extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private ServletContext context;

	@Override
	public void init() throws ServletException {
		context = getServletContext();
	}

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
		DivepointDAO_interface dao_interface = new DivepointJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		if ("getAll".equals(action)) {
			List<DivepointVO> list = dao_interface.getAll();
			writeText(res, list == null ? "" : gson.toJson(list));
		} else if (action.equals("getOnePic")) {
			OutputStream os = res.getOutputStream();
			String dp_no = jsonObject.get("dp_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();

			byte[] image = dao_interface.getOnePic(dp_no);
			if (image != null) {
				// 縮圖 in server side
				image = ImageUtil.shrink(image, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(image.length);
			}
			os.write(image);
		} else if (action.equals("getAllPic")) {
			OutputStream os = res.getOutputStream();
			String dp_no = jsonObject.get("dp_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();

			List<byte[]> imageList = dao_interface.getAllPic(dp_no);

			Iterator<byte[]> it = imageList.iterator();

			while (it.hasNext()) {
				byte[] image = it.next();
				if (image != null) {
					// 縮圖 in server side
					image = ImageUtil.shrink(image, imageSize);
					res.setContentType("image/jpeg");
					res.setContentLength(image.length);
				}
				os.write(image);
			}
		}

		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.println(jsonIn);

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
