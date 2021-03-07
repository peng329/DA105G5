package com.locale.model;

import java.io.Serializable;

public class LocVO implements Serializable {
//4?�屬??
	private String loc_no;
	private String ctry;
	private String sub_region;
	private String weather;

	public String getLoc_no() {
		return loc_no;
	}

	public void setLoc_no(String loc_no) {
		this.loc_no = loc_no;
	}

	public String getCtry() {
		return ctry;
	}

	public void setCtry(String ctry) {
		this.ctry = ctry;
	}

	public String getSub_region() {
		return sub_region;
	}

	public void setSub_region(String sub_region) {
		this.sub_region = sub_region;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

}
