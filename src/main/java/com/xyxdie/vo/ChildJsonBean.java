package com.xyxdie.vo;


public class ChildJsonBean {

	private int id;
	private int pid;
    private String name;
    private String ip;
    private String date;
    private String message;
    private String imgUrl;
	private int type;

    public ChildJsonBean(int id, int pid, String name, String ip, String date, String message, String imgUrl, int type) {
    	this.id = id;
    	this.pid = pid;
        this.name = name;
        this.ip = ip;
        this.date = date;
        this.message = message;
        this.imgUrl = imgUrl;
        this.type = type;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}




}
