package com.lesson.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LessonSchedule")
public class LessonTimer extends HttpServlet {
	
	Long period = 3600000l;
	Date FirstTime = new Date();
	String lastexecutetime = "";
	int yy, mm, dd, hr, counts;
	Calendar cal;
	Timer myTimer = new Timer();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		myTimer.cancel();
	}
	
	public void init() {
		cal = Calendar.getInstance(); //取得時間的實體
		yy = cal.get(Calendar.YEAR);  //取得目前的年份
		mm = cal.get(Calendar.MONTH); //取得月份
		dd = cal.get(Calendar.DATE);  //取得日期
		hr = cal.get(Calendar.HOUR_OF_DAY); //取得時間24小時制
		cal.set(yy, mm, dd, 0, 0, 0); //設定回當天的00:00
		counts = 0;
		TimerTask tsk = new TimerTask() {
			public void run() {  //匿名類別實作run方法，要呼叫的類別跟方法放在這個區域中
				hr = cal.get(Calendar.HOUR_OF_DAY)+1;
				if(hr > 24) {    //要是尖閣加太多，超過24小時的話
					hr %= 24;
					dd += 1;     //如果有餘數天數+1
					cal.set(Calendar.HOUR_OF_DAY, hr);
					cal.set(Calendar.DATE, dd);
				}else { //如果沒超過24小時，直接按照間隔繼續設定
					cal.set(Calendar.HOUR_OF_DAY, hr);
				}
				LessonSchedule.load();
			};
		};
		myTimer.scheduleAtFixedRate(tsk, cal.getTime(), 24*60*60*1000);
	}
	
}
