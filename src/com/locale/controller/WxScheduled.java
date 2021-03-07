package com.locale.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//用來設定定時任務的工具
public class WxScheduled extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Long period = 86400000l;
	Date FirstTime = new Date(); // when it was found
	String lastexecutetime = "";
	int yy, mm, dd, hr;
	Calendar wkC;
	int counts;
	Timer timer = new Timer();

	public void destroy() {
		timer.cancel();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		wkC = Calendar.getInstance();//取得當前時間(
		yy = wkC.get(Calendar.YEAR);//取得wkc當前時間，年
		mm = wkC.get(Calendar.MONTH);//取得wkc當前時間，月
		dd = wkC.get(Calendar.DATE);//取得wkc當前時間，日
		hr = wkC.get(Calendar.HOUR_OF_DAY);//取得wkc當前時間小時，24時制
		wkC.set(yy, mm, dd, 11, 0, 0);//歸零到當天00：00
		System.out.println(wkC.getTime().toString());
		counts = 0;
				TimerTask tsk = new TimerTask() {
						public void run() {
				hr = wkC.get(Calendar.HOUR_OF_DAY) + 6;//每6小時執行一次
				if (hr >= 24) {//
					hr %= 24;
					dd += 1;
					wkC.set(Calendar.HOUR_OF_DAY, hr);
					wkC.set(Calendar.DATE, dd);
				} else {//
					wkC.set(Calendar.HOUR_OF_DAY, hr);
				}
				wxloader.loader();
				counts++;
				lastexecutetime = wkC.getTime().toString();
//				System.out.println(lastexecutetime + ":目前共執行了 " + counts + " 次");// print of executing time and how many
																				// times
				// it rans.
			};
		};
//		timer.schedule(tsk, wkC.getTime(), 21600000);
		timer.scheduleAtFixedRate(tsk, wkC.getTime(), 21600000);//執行間隔24小時
	}
}