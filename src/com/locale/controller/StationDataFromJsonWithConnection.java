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
import javax.swing.text.StyledEditorKit.ItalicAction;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.locale.model.LocService;
import com.locale.model.LocVO;

public class StationDataFromJsonWithConnection {
//0228, get all necesary ObStations and hanged,next step to finish all procedure 
//1.sql table
//	2.vo/model
//	3.pass it to the View and marked on map.
	public static void main(String[] args) {

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
		String apiKey = "CWB-092F6DFC-B940-44A7-A9E9-35F1A475B5E0";
		String dataID = "O-B0076-001";
		String format = "JSON";
		String dataUrl = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/";
//		String stationData ="https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/O-B0076-001?Authorization=CWB-092F6DFC-B940-44A7-A9E9-35F1A475B5E0&downloadType=WEB&format=JSON";
		StringBuilder builder = new StringBuilder();
		String url = builder.append(dataUrl).append(dataID).append("?Authorization=").append(apiKey)
				.append("&downloadType=WEB&format=").append(format).toString();
		String type = "GET"; // Method
		URL uri;
		HttpsURLConnection conn = null;
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
			int countlength = 0;
			for (String line = null; (line = br.readLine()) != null;) {
				jsonStrBuilder.append(line);
				countlength += line.length();
			}
			System.out.println(countlength + "," + jsonStrBuilder.length());// 印出(累計字元數，串接字元數)做比對

			JSONTokener jtkner = new JSONTokener(jsonStrBuilder.toString());
			JSONObject jObj = new JSONObject(jtkner);
			jObj = jObj.getJSONObject("cwbdata").getJSONObject("resources").getJSONObject("resource")
					.getJSONObject("data").getJSONObject("seaSurfaceObs");// 剝掉5層
			System.out.println(jObj.toString());
			JSONArray jsonAr = jObj.getJSONArray("location"); // 取得全部地區資料
			System.out.println(jsonAr.toString());// ok
//			System.out.println("jsonAr長度=：" + jsonAr.length());	//測試長度(數量=jsonObj物件數量)
//			JSONObject test = jsonAr.getJSONObject(1);				//測試：方法練習，指定index取得物件
//			System.out.println(test.getString("locationName"));		//測試：方法練習，指定key取得物件

			// 按照資料庫PK進行switch case原始亂序資料排序，或者將資料庫的地區編號按資料順序編號
//			Map<String, JSONArray> locationWx = new TreeMap<String, JSONArray>(); // 排序用的map
			Map<Integer, JSONObject> locationSta = new TreeMap<Integer, JSONObject>(); // 排序用的map
//			Iterator itJsonAr = jsonAr.iterator(); // Iterator / for loop 二選一隨便
			int countDataSets = 0;
			int i = 0;
//			while(itJsonAr.hasNext()){		//這行及下一行解開的話，請把接下來的兩行for loop 註解。
//				JSONObject tearDownWxElmnt = (JSONObject) itJsonAr.next();
			for (i = 0; i < jsonAr.length(); i++) {
				JSONObject tearDownStaElmnt = (JSONObject) jsonAr.get(i);// 取到個別區域的物件了
				JSONObject station = (JSONObject) tearDownStaElmnt.getJSONObject("station");// 取到個別區域的物件了
				String area = station.getJSONObject("area").getString("areaName");
				JSONObject staloc = new JSONObject();
				countDataSets += countDataSets;
				switch (area) {
				case "彭佳嶼基隆海面":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000001");
					locationSta.put(i, staloc);
					break;
				case "釣魚台海面":// 這區域有錯誤，一定會先從這裡開始，因為資料的順序

					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000002");
					locationSta.put(i, staloc);
					System.out.println(locationSta.toString());
					break;
				case "新竹鹿港沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000003");
					locationSta.put(i, staloc);
					break;
				case "鹿港東石沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000004");
					locationSta.put(i, staloc);
					break;
				case "東石安平沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000005");
					locationSta.put(i, staloc);
					break;
				case "安平高雄沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000006");
					locationSta.put(i, staloc);
					break;
				case "高雄枋寮沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000007");
					locationSta.put(i, staloc);
					break;
				case "枋寮恆春沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000008");
					locationSta.put(i, staloc);
					break;
				case "鵝鑾鼻沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000009");
					locationSta.put(i, staloc);
					break;
				case "成功臺東沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000010");
					locationSta.put(i, staloc);
					break;
				case "臺東大武沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000011");
					locationSta.put(i, staloc);
					break;
				case "綠島蘭嶼海面":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000012");
					locationSta.put(i, staloc);
					break;
				case "花蓮沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000013");
					locationSta.put(i, staloc);
					break;
				case "宜蘭蘇澳沿海":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000014");
					locationSta.put(i, staloc);
					break;
				case "澎湖海面":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000015");
					locationSta.put(i, staloc);
					break;
				case "馬祖海面":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000016");
					locationSta.put(i, staloc);
					break;
				case "金門海面":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000017");
					locationSta.put(i, staloc);
					break;
				default:
					break;
				}
			}
			if (locationSta.size() != 0) {
				System.out.println(locationSta.size());
				Set<Integer> keys = locationSta.keySet();
				Iterator<Integer> keyItr = keys.iterator();
				while (keyItr.hasNext()) {
					System.out.println(((JSONObject) locationSta.get(keyItr.next())).toString());
				}
			} else {
				System.out.println(locationSta.size());
			}


			/*----------------------以下對資料庫天氣做新增------------------------------ */

//			LocService locSvc = new LocService();
//			List<LocVO> list = locSvc.getAll();
//			Set<String> set = locationWx.keySet();
//			for (LocVO lv : list) {
//				String loc_no = lv.getLoc_no();
//				String ctry = lv.getCtry();
//				String sub_region = lv.getSub_region();
//			String weather = ((JSONArray) locationWx.get(lv.getLoc_no())).toString();
////				lv.setWeather(((JSONArray)locationWx.get(lv.getLoc_no())).toString());
//				System.out.println(lv.getLoc_no());
//				locSvc.updateLoc(loc_no, ctry, sub_region, weather);
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
			jsArray = jarry.getJSONObject(i).getJSONArray("time");
			jsObj = jsObj.put(key, jsArray);
			refined.put(jsObj);
		}
		return refined;
	}

}
