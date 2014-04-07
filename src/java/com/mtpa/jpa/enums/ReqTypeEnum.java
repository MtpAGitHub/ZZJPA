/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 230214   Specifies the permitted RESTFul call types
 */
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
