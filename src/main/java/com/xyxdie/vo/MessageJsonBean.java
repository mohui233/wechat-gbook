package com.xyxdie.vo;


public class MessageJsonBean {

	private int id;
    private String name;
    private String ip;
    private String date;
    private String message;
    private String imgUrl;

    public MessageJsonBean(int id, String name, String ip, String date, String message, String imgUrl) {
    	this.setId(id);
        this.name = name;
        this.ip = ip;
        this.date = date;
        this.message = message;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
