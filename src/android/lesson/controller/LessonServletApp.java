package android.lesson.controller;



import android.lesson.model.LessonVO;
import android.lesson.model.LessonJNDIDAO;
import android.lesson.model.LessonDAO_interface;


import java.io.BufferedReader;
import java.io.IOException;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class LessonServletApp extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		System.out.println("input: " + jsonIn);
		LessonDAO_interface dao_interface= new LessonJNDIDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		
		if ("findByPrimaryKey".equals(action)) {
			String les_no = jsonObject.get("les_no").getAsString();
			LessonVO lessonVO = dao_interface.findByPrimaryKey(les_no);
			
			writeText(res, lessonVO == null ? "" : gson.toJson(lessonVO));
		} 
		
		else if ("findByCoach".equals(action)) {
			String coach = jsonObject.get("coach").getAsString();
			List<LessonVO> lessonList = dao_interface.findByCoach(coach);
			
			writeText(res, lessonList == null ? "" : gson.toJson(lessonList));
		} 
		
		else if ("findByLessonName".equals(action)) {
			String les_name = jsonObject.get("les_name").getAsString();
			List<LessonVO> lessonList = dao_interface.findByLessonName(les_name);
			
			writeText(res, lessonList == null ? "" : gson.toJson(lessonList));
		} 
		
		else if ("findByShop".equals(action)) {
			String ds_no = jsonObject.get("ds_no").getAsString();
			List<LessonVO> lessonList  = dao_interface.findByShop(ds_no);
			Gson gson2=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(res, lessonList == null ? "" : gson2.toJson(lessonList));
		} 
		else if ("getAll".equals(action)) {
			List<LessonVO> lessonList  = dao_interface.getAll();
			
			writeText(res, lessonList == null ? "" : gson.toJson(lessonList));
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
