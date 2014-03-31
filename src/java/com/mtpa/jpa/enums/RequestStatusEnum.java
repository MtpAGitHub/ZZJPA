//310314    MtpA    Created

package com.mtpa.jpa.enums;

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
