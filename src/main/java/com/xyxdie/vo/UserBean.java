package com.xyxdie.vo;

public class UserBean {

    protected int id;

    protected int type;

    private String name;

    private String passwd;

    private String imgUrl;

    public UserBean(){}

    public UserBean(int id, String name, String passwd){
        this.name = name;
        this.id = id;
        this.passwd = passwd;
    }

    public UserBean(int id, String name, String passwd, int type){
        this.name = name;
        this.id = id;
        this.passwd = passwd;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
