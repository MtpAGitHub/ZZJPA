/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 230214   Specifies the permitted body detail from RESTFul call
 */
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
