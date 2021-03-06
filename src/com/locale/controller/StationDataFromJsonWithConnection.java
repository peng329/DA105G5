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
			System.out.println(countlength + "," + jsonStrBuilder.length());// ??????(?????????????????????????????????)?????????

			JSONTokener jtkner = new JSONTokener(jsonStrBuilder.toString());
			JSONObject jObj = new JSONObject(jtkner);
			jObj = jObj.getJSONObject("cwbdata").getJSONObject("resources").getJSONObject("resource")
					.getJSONObject("data").getJSONObject("seaSurfaceObs");// ??????5???
			System.out.println(jObj.toString());
			JSONArray jsonAr = jObj.getJSONArray("location"); // ????????????????????????
			System.out.println(jsonAr.toString());// ok
//			System.out.println("jsonAr??????=???" + jsonAr.length());	//????????????(??????=jsonObj????????????)
//			JSONObject test = jsonAr.getJSONObject(1);				//??????????????????????????????index????????????
//			System.out.println(test.getString("locationName"));		//??????????????????????????????key????????????

			// ???????????????PK??????switch case?????????????????????????????????????????????????????????????????????????????????
//			Map<String, JSONArray> locationWx = new TreeMap<String, JSONArray>(); // ????????????map
			Map<Integer, JSONObject> locationSta = new TreeMap<Integer, JSONObject>(); // ????????????map
//			Iterator itJsonAr = jsonAr.iterator(); // Iterator / for loop ???????????????
			int countDataSets = 0;
			int i = 0;
//			while(itJsonAr.hasNext()){		//?????????????????????????????????????????????????????????for loop ?????????
//				JSONObject tearDownWxElmnt = (JSONObject) itJsonAr.next();
			for (i = 0; i < jsonAr.length(); i++) {
				JSONObject tearDownStaElmnt = (JSONObject) jsonAr.get(i);// ??????????????????????????????
				JSONObject station = (JSONObject) tearDownStaElmnt.getJSONObject("station");// ??????????????????????????????
				String area = station.getJSONObject("area").getString("areaName");
				JSONObject staloc = new JSONObject();
				countDataSets += countDataSets;
				switch (area) {
				case "?????????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000001");
					locationSta.put(i, staloc);
					break;
				case "???????????????":// ????????????????????????????????????????????????????????????????????????

					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000002");
					locationSta.put(i, staloc);
					System.out.println(locationSta.toString());
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000003");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000004");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000005");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000006");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000007");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000008");
					locationSta.put(i, staloc);
					break;
				case "???????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000009");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000010");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000011");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000012");
					locationSta.put(i, staloc);
					break;
				case "????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000013");
					locationSta.put(i, staloc);
					break;
				case "??????????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000014");
					locationSta.put(i, staloc);
					break;
				case "????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000015");
					locationSta.put(i, staloc);
					break;
				case "????????????":
					staloc.put("stationName", station.getString("stationName"));
					staloc.put("lat", station.getDouble("stationLatitude"));
					staloc.put("lng", station.getDouble("stationLongitude"));
					staloc.put("loc_no", "LOC000016");
					locationSta.put(i, staloc);
					break;
				case "????????????":
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


			/*----------------------?????????????????????????????????------------------------------ */

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
		JSONArray refined = new JSONArray(); // ???????????????
		JSONArray jsArray = new JSONArray(); // ??????V:JSONArray?????????
		JSONObject jsObj = null; // ??????JSONObject?????????
		String key = ""; // ??????Key???

		int i = 0;
		for (i = 0; i < jarry.length(); i++) {
			jsObj = new JSONObject();// ?????????new ????????????????????????????????????put??????
			key = jarry.getJSONObject(i).getString("elementName");
			jsArray = jarry.getJSONObject(i).getJSONArray("time");
			jsObj = jsObj.put(key, jsArray);
			refined.put(jsObj);
		}
		return refined;
	}

}
