package android.lessonorder.controller;

import android.lessonorder.model.LessonorderDAO_interface;
import android.lessonorder.model.LessonorderJNDIDAO;
import android.lessonorder.model.LessonorderVO;
import android.mem.model.MemVO;

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

public class LessonorderServletApp extends HttpServlet{
    private final static String CONTENT_TYPE = "text/html;charset=UTF-8";
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
    	req.setCharacterEncoding("UTF-8");
    	Gson gson = new Gson();
    	BufferedReader br = req.getReader();
    	StringBuilder jsonIn = new StringBuilder();
    	String line = null;
    	while((line=br.readLine())!=null) {
    		jsonIn.append(line);
    	}
    	
    	System.out.println("input:"+jsonIn);
    	LessonorderDAO_interface dao_interface = new LessonorderJNDIDAO();
    	JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
    	String action = jsonObject.get("action").getAsString();
    	
    	if("findByPrimaryKey".equals(action)) {
    		String les_o_no = jsonObject.get("les_o_no").getAsString();
    		LessonorderVO lessonorderVO = dao_interface.findByPrimaryKey(les_o_no);
    		writeText(res,lessonorderVO==null? "":gson.toJson(lessonorderVO));
    	}
    	else if("findByMem_no".equals(action)) {
    		String mem_no = jsonObject.get("mem_no").getAsString();
    		List<LessonorderVO> list = dao_interface.findByMem_no(mem_no);
    		writeText(res,list==null?"":gson.toJson(list));
    	}
    	else if("findByLes_no".equals(action)) {
    		String les_no = jsonObject.get("les_no").getAsString();
    		List<LessonorderVO> list = dao_interface.findByLes_no(les_no);
    		writeText(res, list==null?"":gson.toJson(list));
    	}
    	else if("getAll".equals(action)) {
    		List<LessonorderVO> list = dao_interface.getAll();
    		writeText(res,list==null?"":gson.toJson(list));
    	}
    	else if ("insert".equals(action)) {
    		LessonorderVO lessonOrderVO = gson.fromJson(jsonObject.get("lessonorderVO").getAsString(), LessonorderVO.class);
			writeText(res, String.valueOf(dao_interface.insert(lessonOrderVO)));
		}
    	else if ("isMemOrdered".equals(action)) {
			String les_no = jsonObject.get("les_no").getAsString();
			String mem_no = jsonObject.get("mem_no").getAsString();
			writeText(res, String.valueOf(dao_interface.isMemOrdered(les_no,mem_no)));
		} 
    	
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
    	doPost(req,res);
    }
    
    
    
    private void writeText(HttpServletResponse res,String outText) throws IOException{
    	res.setContentType(CONTENT_TYPE);
    	PrintWriter out = res.getWriter();
    	out.print(outText);
    	out.close();
    	System.out.println("outText:"+outText);
    }
    
    
}
