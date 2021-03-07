package com.locale.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.locale.model.LocService;
import com.locale.model.LocVO;

public class wxloader {

	public static void main(String[] args) {
		wxloader.loader();
	}

	public static void loader() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			System.out.println(e);
		}

		// Custom parameters
		String url = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-A0012-001?Authorization=CWB-5D9B79DD-F14E-4F0F-A832-15712FB19941&downloadType=WEB&format=JSON"; // URL-Path
		String type = "GET"; // Method
		URL uri;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			uri = new URL(url);
			conn = (HttpsURLConnection) uri.openConnection();
			conn.setRequestMethod(type);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setRequestProperty("Content-Type", "application/json"); // Content-Type: application/json
			conn.setDoOutput(true);
			conn.connect();

			br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			StringBuilder jsonStrBuilder = new StringBuilder();
			int count = 0;
			for (String line = null; (line = br.readLine()) != null;) {
				jsonStrBuilder.append(line);
				count += line.length();
			}
			System.out.println(count + "," + jsonStrBuilder.length());

			JSONTokener jtkner = new JSONTokener(jsonStrBuilder.toString());
			JSONObject jObj = new JSONObject(jtkner);
			jObj = jObj.getJSONObject("cwbopendata").getJSONObject("dataset");// 剝掉2層
			JSONArray jsonAr = jObj.getJSONArray("location"); // 取得全部地區資料
//			System.out.println("jsonAr長度=：" + jsonAr.length());	//測試長度(數量=jsonObj物件數量)
//			JSONObject test = jsonAr.getJSONObject(1);				//測試：方法練習，指定index取得物件
//			System.out.println(test.getString("locationName"));		//測試：方法練習，指定key取得物件

			// 按照資料庫PK進行switch case原始亂序資料排序，或者將資料庫的地區編號按資料順序編號
			Map<String, JSONArray> locationWx = new TreeMap<String, JSONArray>(); // 排序用的map
			Iterator itJsonAr = jsonAr.iterator(); // Iterator / for loop 二選一隨便
			count = 0;
			while (itJsonAr.hasNext()) { // 迴圈開始
				JSONObject tearDownWxElmnt = (JSONObject) itJsonAr.next();
				switch (tearDownWxElmnt.getString("locationName").trim()) {
				case "彭佳嶼基隆海面":
					JSONArray LOC000001 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000001", LOC000001);
//					System.out.println("LOC000001");
//					System.out.println(LOC000001.toString());
//					System.out.println(((JSONArray)locationWx.get("LOC000001")).toString());
					break;
				case "釣魚台海面":// 這區域有錯誤，一定會先從這裡開始，因為資料的順序
					JSONArray LOC000002 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000002", LOC000002);
//					System.out.println("LOC000002");
//					System.out.println(LOC000002.toString());
//					System.out.println(((JSONArray) locationWx.get("LOC000002")).toString());
					break;
				case "新竹鹿港沿海":
					JSONArray LOC000003 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000003", LOC000003);
					break;
				case "鹿港東石沿海":
					JSONArray LOC000004 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000004", LOC000004);
					break;
				case "東石安平沿海":
					JSONArray LOC000005 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000005", LOC000005);
					break;
				case "安平高雄沿海":
					JSONArray LOC000006 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000006", LOC000006);
					break;
				case "高雄枋寮沿海":
					JSONArray LOC000007 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000007", LOC000007);
					break;
				case "枋寮恆春沿海":
					JSONArray LOC000008 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000008", LOC000008);
					break;
				case "鵝鑾鼻沿海":
					JSONArray LOC000009 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000009", LOC000009);
					break;
				case "成功臺東沿海":
					JSONArray LOC000010 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000010", LOC000010);
					break;
				case "臺東大武沿海":
					JSONArray LOC000011 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000011", LOC000011);
					break;
				case "綠島蘭嶼海面":
					JSONArray LOC000012 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000012", LOC000012);
					break;
				case "花蓮沿海":
					JSONArray LOC000013 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000013", LOC000013);
					break;
				case "宜蘭蘇澳沿海":
					JSONArray LOC000014 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000014", LOC000014);
					break;
				case "澎湖海面":
					JSONArray LOC000015 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000015", LOC000015);
					break;
				case "馬祖海面":
					JSONArray LOC000016 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000016", LOC000016);
					break;
				case "金門海面":
					JSONArray LOC000017 = jArrDowngrader(tearDownWxElmnt.getJSONArray("weatherElement"));
					locationWx.put("LOC000017", LOC000017);
					break;
				default:
					break;
				}

			}
			System.out.println("weather data ready");
//			System.out.println("目前共有" + locationWx.size() + "筆資料"); // 測試收到的資料筆數
//			jsonAr = (JSONArray) locationWx.get("LOC000004");
//			System.out.println(jsonAr.toString());						//	測試String作為Key的效果

			//
//			for (JSONArray ja : locationWx.values()) { // 測試內部物件
//				System.out.println(ja);
//
//			}

//			JSONObject jo = 
			LocService locSvc = new LocService();
			List<LocVO> list = locSvc.getAll();
			for (LocVO lv : list) {
				String loc_no = lv.getLoc_no();
				String ctry = lv.getCtry();
				String sub_region = lv.getSub_region();
				String weather = ((JSONArray) locationWx.get(lv.getLoc_no())).toString();
//				lv.setWeather(((JSONArray)locationWx.get(lv.getLoc_no())).toString());
				System.out.println(lv.getLoc_no());
				locSvc.updateLoc(loc_no, ctry, sub_region, weather);
			}

////			Set<String> set = locationWx.keySet();
//			String localeID = "";
//			for(int i=1;i<=set.size();i++) {
//				localeID= "LOC0000"+String.format("%02d", i);
//				jsonAr.(localeID,(JSONArray)locationWx.get("LOC000001"));

//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (conn != null)
				conn.disconnect();
		}
	}

	public static JSONArray jArrDowngrader(JSONArray jarry) {
		System.out.println("this method is to down grade the structure of the JSONArray");
		JSONArray refined = new JSONArray(); // 最後輸出的
		JSONArray jsArray = new JSONArray(); // 暫存V:JSONArray用物件
		JSONObject jsObj = null; // 暫存JSONObject用物件
		String key = ""; // 暫存Key用

		int i = 0;
		for (i = 0; i < jarry.length(); i++) {
			jsObj = new JSONObject();// 記得要new 新物件，不然會一直用舊的put上去
			key = jarry.getJSONObject(i).getString("elementName");
//			System.out.println(key);
			jsArray = jarry.getJSONObject(i).getJSONArray("time");
//			System.out.println(jsArray.toString());
			jsObj = jsObj.put(key, jsArray);
//			System.out.println(jsObj.toString());
			refined.put(jsObj);
//			System.out.println(i + refined.toString());
		}
		return refined;
	}

}
