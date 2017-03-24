package com.winsigns.investment.webGateway.websocket;

public class WiselyMessage {
    private String name;
    public String getName(){
        return name;
    }
    
    private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}