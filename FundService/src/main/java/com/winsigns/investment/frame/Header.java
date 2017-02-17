package com.winsigns.investment.frame;

public class Header {	
	Long errorId = 0L;
	String message = new String("操作成功!");
	
	public Header setErrorId(Long errorId){
		this.errorId = errorId;
		return this;
	}
	
	public Long getErrorId(){
		return this.errorId;
	}
	
	public Header setMessage(String message){
		this.message = message;
		return this;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Header setHeader(Long errorId, String message){
		this.errorId = errorId;
		this.message = message;
		return this;
	}
}
