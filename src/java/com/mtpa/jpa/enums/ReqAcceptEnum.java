//230214    -   MtpA    Restful request type

package com.mtpa.jpa.enums;

public enum ReqAcceptEnum {
	XML("application/xml"), 
        JSON("application/json");
 
	private String reqAccept;
 
	private ReqAcceptEnum(String accept) {
		reqAccept = accept;
	}
 
	public String getReqAccept() {
		return reqAccept;
	} 
}
