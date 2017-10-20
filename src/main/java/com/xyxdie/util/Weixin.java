package com.xyxdie.util;

public class Weixin {
	private String openid;
	private String accesstoken;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public Weixin(String openid, String accesstoken) {
		super();
		this.openid = openid;
		this.accesstoken = accesstoken;
	}
	public Weixin() {
		super();
	}
	
}
