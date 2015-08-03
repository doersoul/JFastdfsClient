package com.eiviv.fdfs.model;

import java.io.Serializable;

public class Result<T extends Serializable> {
	
	private int code;
	private String message;
	private T data;
	
	public Result(int code) {
		this.code = code;
	}
	
	public Result(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Result(int code, T data) {
		this.code = code;
		this.data = data;
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
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
}
