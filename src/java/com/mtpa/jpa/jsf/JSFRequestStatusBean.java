//310314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.enums.RequestStatusEnum;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("reqstatus")
@RequestScoped
public class JSFRequestStatusBean {

    public JSFRequestStatusBean() {
        
    }
    
    public RequestStatusEnum[] getRequestStatus() {
        return RequestStatusEnum.values();
    }
}