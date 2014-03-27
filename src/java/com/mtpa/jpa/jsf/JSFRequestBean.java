//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("newrequest")
@RequestScoped
public class JSFRequestBean {

    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    
    private String requestUser;
    private double requestAmt;

    public JSFRequestBean() {
        
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public double getRequestAmt() {
        return requestAmt;
    }

    public void setRequestAmt(double requestAmt) {
        this.requestAmt = requestAmt;
    }

    public String submitRequest() {
        debugTxt.setDebugText("Requested " + requestAmt + " from " + requestUser);
        return "home";
    }
}
