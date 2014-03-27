//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("payment")
@RequestScoped
public class JSFPaymentBean {

    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    
    private String paymentUser;
    private long paymentAct;
    private double paymentAmt;
    
    public JSFPaymentBean() {
        
    }

    public String getPaymentUser() {
        return paymentUser;
    }

    public void setPaymentUser(String paymentUser) {
        this.paymentUser = paymentUser;
    }

    public long getPaymentAct() {
        return paymentAct;
    }

    public void setPaymentAct(long paymentAct) {
        this.paymentAct = paymentAct;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }
    
    public String submitPayment() {
        debugTxt.setDebugText("Amount paid " + paymentAmt + " to " + paymentUser);
        return "home";
    }
}
