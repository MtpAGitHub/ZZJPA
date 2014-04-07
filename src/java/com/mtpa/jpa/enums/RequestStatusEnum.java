/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 310314   The types of status that apply to requests for payment
 */
public enum RequestStatusEnum {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    REJECTED("Rejected");
    
    private String requestStatus;
    
    private RequestStatusEnum(String vReqStatus) {
        this.requestStatus = vReqStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }
}
