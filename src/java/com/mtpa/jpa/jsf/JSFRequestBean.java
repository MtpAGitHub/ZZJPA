//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.entity.ENTTransaction_;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
    @Inject
    JSFUserBean curUser;
    
    @EJB
    RequestJPALocal userRequest;
    @EJB
    GetTPUserLocal userList;
    @EJB
    UserJPALocal tpUser;
    @EJB
    DateStampLocal dateStamp;
    
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

    public List<String> getAllTpUsers() {
        return userList.getTPUserList(curUser.getUserId());
    }
    
    public String submitRequest() {
        ENTUser requesteeUser = tpUser.getUser(requestUser);
        if (requesteeUser != null) {
            userRequest.createRequest(curUser.getUserId(), requesteeUser.getPersonId(), requestAmt, dateStamp.getWsDateStamp());
        } else {
        }
        debugTxt.setDebugText("Requested " + requestAmt + " from " + requestUser);
        return "home";
    }
}
