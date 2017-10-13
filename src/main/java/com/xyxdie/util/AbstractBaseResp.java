package com.xyxdie.util;

public class AbstractBaseResp {
	private int code;
	private String message;
	private Object object;
	private Long totalCount;
	private Integer totalPage;
	public AbstractBaseResp(int code, String message, Long totalCount, Integer totalPage, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.object = object;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}
	public AbstractBaseResp() {
		super();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	

}
