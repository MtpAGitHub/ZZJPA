//230214    -   MtpA    Restful request type

package com.mtpa.jpa.enums;

public enum ReqTypeEnum {
	GET("GET"), 
        POST("POST"), 
        PUT("PUT"), 
        DELETE("DELETE");
 
	private String reqType;
 
	private ReqTypeEnum(String type) {
		reqType = type;
	}
 
	public String getReqType() {
		return reqType;
	} 
}
